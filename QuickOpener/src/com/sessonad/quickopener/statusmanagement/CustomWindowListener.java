package com.sessonad.quickopener.statusmanagement;

import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.services.ISourceProviderService;

public class CustomWindowListener implements IWindowListener{

	CustomSelectionListener selListener = null;
	
	@Override
	public void windowActivated(IWorkbenchWindow window) {
		selListener = new CustomSelectionListener((ISourceProviderService) window.getService(ISourceProviderService.class));
		window.getSelectionService().addSelectionListener(selListener);
		
//		IPartListener2 pl = new IPartListener2(){
//			@Override
//			public void partActivated(IWorkbenchPartReference partRef) {
//				System.out.println("\nActive: "+partRef.getTitle() +
//						"\nclass: " + partRef.getPart(false).getClass()+
//						"\nid: " + partRef.getId());
//			}
//			@Override
//			public void partBroughtToTop(IWorkbenchPartReference partRef) {}
//			@Override
//			public void partClosed(IWorkbenchPartReference partRef) {}
//			@Override
//			public void partDeactivated(IWorkbenchPartReference partRef) {}
//			@Override
//			public void partOpened(IWorkbenchPartReference partRef) {}
//			@Override
//			public void partHidden(IWorkbenchPartReference partRef) {}
//			@Override
//			public void partVisible(IWorkbenchPartReference partRef) {}
//			@Override
//			public void partInputChanged(IWorkbenchPartReference partRef) {}
//			
//		};
//		window.getPartService().addPartListener(pl);
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
