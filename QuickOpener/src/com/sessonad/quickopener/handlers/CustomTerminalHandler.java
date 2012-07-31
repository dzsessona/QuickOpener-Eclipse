package com.sessonad.quickopener.handlers;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JOptionPane;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.sessonad.oscommands.commands.Commands;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class CustomTerminalHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public CustomTerminalHandler() {
	}


	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {			
		try {
			DialogueCommands dialogue = new DialogueCommands();
			final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            final int x = (screenSize.width - dialogue.getWidth()) / 2;
            final int y = (screenSize.height - dialogue.getHeight()) / 2;
            dialogue.setLocation(x, y);
            dialogue.setVisible(true);
        } catch (Exception ex) {
        	//ex.printStackTrace();
        	//MessageDialog.openInformation(window.getShell(),"error", ex.getMessage());
        }
		return null;
	}
}
