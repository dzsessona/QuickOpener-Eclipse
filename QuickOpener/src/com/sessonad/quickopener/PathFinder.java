package com.sessonad.quickopener;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
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
    
    public static String getPathFromSelection(IWorkbenchWindow window) throws Exception{
        return getFileFromSelection(window).getAbsolutePath();
    }
    
    public static File getFileFromSelection(IWorkbenchWindow window) throws Exception{
    	File current = getIFileFromSelection(window).toFile();
        return (current.isDirectory())?current:current.getParentFile();
    }
    
//  public static IProject getMainProject(IWorkbenchWindow window) throws Exception{
//	IFile ifile = getIFileFromSelection(window).;
//	IProject activeProject = ifile.getProject();
//	return activeProject;
//}
//
//public static File getMainProjectRoot(IWorkbenchWindow window) throws Exception{    	
//	IProject activeProject = getMainProject( window);
//	IPath path = activeProject.getFullPath();
//	return path.toFile();
//}
}
