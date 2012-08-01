package com.sessonad.quickopener.handlers;

import java.awt.Dimension;
import java.awt.Toolkit;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import com.sessonad.quickopener.preferences.PreferenceDialogue;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class PreferencesHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public PreferencesHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {			
			PreferenceDialogue dialogue = new PreferenceDialogue(null,true);			
			final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            final int x = (screenSize.width - dialogue.getWidth()) / 2;
            final int y = (screenSize.height - dialogue.getHeight()) / 2;
            dialogue.setLocation(x, y);
            dialogue.setVisible(true); 
	    } catch (Exception ex) {
	       // MessageDialog.openInformation(null,"error", ex.getMessage());
	    }
		return null;
	}
}
