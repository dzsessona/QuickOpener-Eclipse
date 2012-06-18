package com.sessonad.quickopener;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

public class QuickOpenerSourceProvider extends AbstractSourceProvider {

	public final static String ACTIVE_STATE = "quickopener.active";
	
	boolean state = true;
	
	
	@Override
	public void dispose() {
	}	

	@Override
	public Map<String, String> getCurrentState() {
		Map<String, String> currentState = new HashMap<String, String>(1);
		currentState.put(ACTIVE_STATE, (state ? "yes":"no"));
		return currentState;
	}

	@Override
	public String[] getProvidedSourceNames() {
		return new String[] { ACTIVE_STATE };
	}

	public void setState(boolean state) {
		if (this.state == state)
			return; // no change
		this.state = state;
		fireSourceChanged(ISources.WORKBENCH, ACTIVE_STATE, (state ? "yes":"no"));
	}
}
