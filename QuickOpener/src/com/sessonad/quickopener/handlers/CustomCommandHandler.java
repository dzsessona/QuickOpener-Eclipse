package com.sessonad.quickopener.handlers;

import javax.swing.JOptionPane;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class CustomCommandHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public CustomCommandHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			String userCommand = JOptionPane.showInputDialog("Select location");
			if(userCommand!=null && !userCommand.isEmpty()){
				Runtime.getRuntime().exec(userCommand);
			}
	    } catch (Exception ex) {
	        MessageDialog.openInformation(null,"error", ex.getMessage());
	    }
		return null;
	}
}
