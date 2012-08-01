package com.sessonad.quickopener.handlers;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import com.sessonad.quickopener.PathFinder;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class PathHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public PathHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		try {
//			PreferenceUtils.storeProperty(PreferenceUtils.PLACES, new QuickOpenerProperty("Downloads","C:\\Users\\sessonad\\Desktop\\Downloads\\src"));
//			PreferenceUtils.storeProperty(PreferenceUtils.PLACES, new QuickOpenerProperty("Desktop","C:\\Users\\sessonad\\Desktop"));
//			PreferenceUtils.storeProperty(PreferenceUtils.COMMANDS, new QuickOpenerProperty("Zip Backup","cmd /c start 7z a C:\\develop\\backup\\${param1}.zip ${param2} -wC:\\Users\\sessonad\\temp"));
//			
			String path=PathFinder.getPathFromSelection(true,window);
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();
            StringSelection strSel = new StringSelection(path);
            clipboard.setContents(strSel, null);
		}catch (Exception ex) {
        	MessageDialog.openInformation(window.getShell(),"error", ex.getMessage());
        }
		return null;
	}
}
