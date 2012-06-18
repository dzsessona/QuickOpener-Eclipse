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
//		ISelectionListener listener = new ISelectionListener() {			
//			@Override
//			public void selectionChanged(IWorkbenchPart part,ISelection selection) {
//				System.out.println("st");
//				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//				ISourceProviderService service = (ISourceProviderService) window.getService(ISourceProviderService.class);
//				QuickOpenerSourceProvider quickOpenerSourceProvider = (QuickOpenerSourceProvider) service.getSourceProvider(QuickOpenerSourceProvider.ACTIVE_STATE);
//				quickOpenerSourceProvider.setState(selection instanceof org.eclipse.jface.text.ITextSelection);
//			}
//		};
//		
//		IWorkbenchWindow win = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//		win.getSelectionService().addSelectionListener(listener);
//		System.out.println("reg");
	}

	public static String active="true";
	
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return super.isEnabled();
	}



	@Override
	public void setEnabled(Object evaluationContext) {
		// TODO Auto-generated method stub
		super.setEnabled(evaluationContext);
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);		
		//plugin.getWorkbench().addWorkbenchListener(listener);
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
