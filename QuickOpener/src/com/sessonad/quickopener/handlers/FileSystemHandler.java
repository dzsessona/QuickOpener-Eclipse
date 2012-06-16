package com.sessonad.quickopener.handlers;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

import com.sessonad.quickopener.commands.Commands;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class FileSystemHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public FileSystemHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		try {
        	File toOpen = Commands.getFileFromSelection(window);
            Commands.getPlatform().browseInFileSystem(toOpen);
	    } catch (Exception ex) {
	        MessageDialog.openInformation(window.getShell(),"error", ex.getMessage());
	    }
		return null;
	}
}
