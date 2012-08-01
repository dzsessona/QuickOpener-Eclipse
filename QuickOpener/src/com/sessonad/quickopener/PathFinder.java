package com.sessonad.quickopener;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.ide.ResourceSelectionUtil;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.views.navigator.ResourceNavigator;

public class PathFinder {

	public static boolean hasFile(ISelection selection){
    	if(selection instanceof ITextSelection){
    		return true;
    	}else if(selection instanceof IStructuredSelection){
    		IStructuredSelection sel=ResourceSelectionUtil.allResources((IStructuredSelection) selection,  IResource.FOLDER| IResource.FILE);
    		return (sel !=null && sel.size()>0);
    	}
    	return false;
    }
    
	
	
    private static IPath getIFileFromSelection(IWorkbenchWindow window){
    	IPath iPath = null;
    	ISelection selection=window.getSelectionService().getSelection();
    	if(selection instanceof IStructuredSelection){
    		IStructuredSelection sel=ResourceSelectionUtil.allResources((IStructuredSelection) selection,  IResource.FOLDER| IResource.FILE);
    		if(sel.size()>0){
    			Object element=sel.getFirstElement();
    			if(ResourceSelectionUtil.resourceIsType((IResource) element, IResource.FOLDER )){	
    				IFolder folder = (IFolder) element;
    				iPath = folder.getLocation();
    			}else if(ResourceSelectionUtil.resourceIsType((IResource) element, IResource.FILE)){
    				IFile file = (IFile) element;
    				iPath = file.getLocation();
    			}
			}
    	}else if(selection instanceof ITextSelection){
    		IEditorPart  editorPart = window.getActivePage().getActiveEditor();
    		IEditorInput input = editorPart.getEditorInput() ;
    		iPath = ResourceUtil.getFile(input).getLocation();
    	}
    	return iPath;
    }
    
    public static File getFileFromSelection(boolean file,IWorkbenchWindow window) throws Exception{
    	File current = getIFileFromSelection(window).toFile();
    	if(!file && !current.isDirectory()){
        	return current.getParentFile();
        }else{
        	return current;
        }
    }
    
    public static String getPathFromSelection(boolean file,IWorkbenchWindow window) throws Exception{
        File select = getIFileFromSelection(window).toFile();
    	if(!file && !select.isDirectory()){
        	return select.getParentFile().getAbsolutePath();
        }else{
        	return select.getAbsolutePath();
        }
    	
    }
    
    
    
    
    
    
    private static IPath getIFileFromWorkBench(IWorkbenchWindow window){
    	IWorkspace workspace = ResourcesPlugin.getWorkspace(); 
    	IWorkspaceRoot root = workspace.getRoot();     	
    	return root.getLocation();
    }
    
    public static File getFileFromWorkBench(boolean file,IWorkbenchWindow window)throws Exception{
    	File current = getIFileFromWorkBench(window).toFile();
    	if(!file && !current.isDirectory()){
        	return current.getParentFile();
        }else{
        	return current;
        }
    }
    
    public static String getPathFromWorkBench(boolean file,IWorkbenchWindow window) throws Exception{
    	File select = getIFileFromWorkBench(window).toFile();
    	if(!file && !select.isDirectory()){
        	return select.getParentFile().getAbsolutePath();
        }else{
        	return select.getAbsolutePath();
        }
    }
    
    
    
    
	private static IPath getIFileFromProject(IWorkbenchWindow window) {
		IProject activeProject = null;
		IEditorPart editorPart = window.getActivePage().getActiveEditor();
		if (editorPart != null) {
			IFileEditorInput input = (IFileEditorInput) editorPart.getEditorInput();
			IFile file = input.getFile();
			activeProject = file.getProject();				
		}else{
			IViewReference [] parts = window.getActivePage().getViewReferences();
			for(int i=0;i<parts.length;i++)
		    {
				IViewPart part =  parts[i].getView(false);
		        if(part instanceof ResourceNavigator)
		        {
		            ResourceNavigator navigator = (ResourceNavigator)parts[i];
		            StructuredSelection sel   = (StructuredSelection)navigator.getTreeViewer().getSelection();
		            IResource resource = (IResource)sel.getFirstElement();
		            activeProject = resource.getProject();
		            break;
		        }
		    }
		}		
		return activeProject.getLocation();
	}
    
    public static File getFileFromProject(boolean file,IWorkbenchWindow window){
    	File current = getIFileFromProject(window).toFile();
    	if(!file && !current.isDirectory()){
        	return current.getParentFile();
        }else{
        	return current;
        }
    }
    
    public static String getPathFromProject(boolean file,IWorkbenchWindow window) throws Exception{
    	File select = getIFileFromProject(window).toFile();
    	if(!file && !select.isDirectory()){
        	return select.getParentFile().getAbsolutePath();
        }else{
        	return select.getAbsolutePath();
        }
    }
    

}
