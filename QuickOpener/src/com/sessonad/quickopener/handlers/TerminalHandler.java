package com.sessonad.quickopener.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import com.sessonad.quickopener.commands.Commands;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class TerminalHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public TerminalHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		try {
            String path=Commands.getPathFromSelection(window);
            Commands.getPlatform().openInShell(path);
        } catch (Exception ex) {
        	//ex.printStackTrace();
        	//MessageDialog.openInformation(window.getShell(),"error", ex.getMessage());
        }
		return null;
	}
}
