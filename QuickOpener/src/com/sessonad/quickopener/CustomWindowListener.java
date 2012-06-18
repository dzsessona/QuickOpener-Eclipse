package com.sessonad.quickopener;

import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.services.ISourceProviderService;

public class CustomWindowListener implements IWindowListener{

	CustomSelectionListener selListener = null;
	
	@Override
	public void windowActivated(IWorkbenchWindow window) {
		selListener = new CustomSelectionListener((ISourceProviderService) window.getService(ISourceProviderService.class));
		window.getSelectionService().addSelectionListener(selListener);
	}

	@Override
	public void windowDeactivated(IWorkbenchWindow window) {
		if(selListener!=null){
			window.getSelectionService().removeSelectionListener(selListener);
		}
	}

	@Override
	public void windowClosed(IWorkbenchWindow window) {}

	@Override
	public void windowOpened(IWorkbenchWindow window) {}

}
