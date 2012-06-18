package com.sessonad.quickopener.statusmanagement;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.services.ISourceProviderService;

import com.sessonad.quickopener.commands.Commands;

public class CustomSelectionListener implements ISelectionListener{

	private ISourceProviderService sourceProvider;
	
	public CustomSelectionListener(ISourceProviderService sourceProvider) {
		this.sourceProvider=sourceProvider;
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		PluginSourceProvider quickOpenerSourceProvider = (PluginSourceProvider) sourceProvider.getSourceProvider(PluginSourceProvider.ACTIVE_STATE);
		quickOpenerSourceProvider.setState(Commands.hasFile(selection));	
		
	}

}
