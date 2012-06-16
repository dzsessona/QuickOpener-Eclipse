package com.sessonad.quickopener.commands;


import com.sessonad.quickopener.OperatingSystem;
import  com.sessonad.quickopener.OSDetector;
import java.io.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.ide.ResourceSelectionUtil;
import org.eclipse.ui.ide.ResourceUtil;

/**
 *
 * @author SessonaD
 */
public abstract class Commands {
    
    static Commands platform;
    
    
    public abstract void browseInFileSystem(File current) throws Exception;
    
    public abstract void openInShell(String currentPath) throws Exception;
        
    @SuppressWarnings("unchecked")
    public static <T extends Commands> T getPlatform(){
        if (platform == null)initializePlatform();
        return (T)platform;
    }
    
    static void initializePlatform(){
        OperatingSystem os = OSDetector.detectOS();
        if      (os.equals(OperatingSystem.WINDOWS))    platform = new WindowsCommands();
        else if (os.equals(OperatingSystem.MAC_OS))     platform = new MacOSCommands();
        else if (os.equals(OperatingSystem.LINUX_GNOME))platform = new LinuxGnomeCommands();        
        else if (os.equals(OperatingSystem.LINUX_KDE))  platform = new LinuxKdeCommands();
        else if (os.equals(OperatingSystem.LINUX_LXDE)) platform = new LinuxLxdeCommands();
        else if (os.equals(OperatingSystem.LINUX_XFCE)) platform = new LinuxXfceCommands();
        else if (os.equals(OperatingSystem.LINUX_UNKNOWN)) platform = new LinuxUnknownCommands();
        else if (os.equals(OperatingSystem.UNKNOWN))    platform = new UnknownOSCommands();
    }
    
    public static String getPathFromSelection(IWorkbenchWindow window) throws Exception{
        return getFileFromSelection(window).getAbsolutePath();
    }
    
    public static File getFileFromSelection(IWorkbenchWindow window) throws Exception{
    	File current = getIFileFromSelection(window).getLocation().toFile();
        return (current.isDirectory())?current:current.getParentFile();
    }
    
    public static IProject getMainProject(IWorkbenchWindow window) throws Exception{
    	IFile ifile = getIFileFromSelection(window);
    	IProject activeProject = ifile.getProject();
    	return activeProject;
    }
    
    public static File getMainProjectRoot(IWorkbenchWindow window) throws Exception{    	
    	IProject activeProject = getMainProject( window);
    	IPath path = activeProject.getFullPath();
    	return path.toFile();
    }
    
    private static IFile getIFileFromSelection(IWorkbenchWindow window){
    	IFile ifile = null;
    	ISelection selection=window.getSelectionService().getSelection();
    	if(selection instanceof IStructuredSelection){
    		IStructuredSelection sel=ResourceSelectionUtil.allResources((IStructuredSelection) selection,  IResource.FOLDER| IResource.FILE);
    		if(sel.size()>0){
    			Object element=sel.getFirstElement();
    			if(ResourceSelectionUtil.resourceIsType((IResource) element, IResource.FOLDER )){
    				System.out.println("folder");
    				ifile = ResourceUtil.getFile(element);
    			}else if(ResourceSelectionUtil.resourceIsType((IResource) element, IResource.FILE)){
    				System.out.println("file");
    				ifile = ResourceUtil.getFile(element);
    			}
    			
    			ifile = ResourceUtil.getFile(element);
			}
    	}else if(selection instanceof ITextSelection){
    		IEditorPart  editorPart = window.getActivePage().getActiveEditor();
    		IEditorInput input = editorPart.getEditorInput() ;
    		ifile = ResourceUtil.getFile(input);
    	}
    	return ifile;
    }
    
}
