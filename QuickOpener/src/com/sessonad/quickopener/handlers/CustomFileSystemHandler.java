package com.sessonad.quickopener.handlers;

import java.io.File;

import javax.swing.JOptionPane;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;

import com.sessonad.oscommands.commands.Commands;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class CustomFileSystemHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public CustomFileSystemHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			String userpath = JOptionPane.showInputDialog("Select location");
			if(userpath!=null && !userpath.isEmpty()){
				File toOpen = new File(userpath);
				Commands.getPlatform().browseInFileSystem(toOpen);
			}
	    } catch (Exception ex) {
	        MessageDialog.openInformation(null,"error", ex.getMessage());
	    }
		return null;
	}
}
