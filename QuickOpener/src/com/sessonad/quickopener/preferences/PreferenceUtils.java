package com.sessonad.quickopener.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

public class PreferenceUtils {
		
	public static void storeProperty(String prefix, QuickOpenerProperty qoprop){
		Preferences preferences = ConfigurationScope.INSTANCE.getNode("com.sessonad.quickopener");
		Preferences node = preferences.node(prefix);
		node.put(qoprop.getDescription().replaceAll(" ","_"), qoprop.getValue());
		try {			
			preferences.flush();
		} catch (BackingStoreException e) {e.printStackTrace();}
	}
	
	public static List<QuickOpenerProperty> getAll(String prefix){
		List<QuickOpenerProperty> list = new ArrayList<QuickOpenerProperty> ();
		Preferences preferences = ConfigurationScope.INSTANCE.getNode("com.sessonad.quickopener");
		Preferences node = preferences.node(prefix);
		try {
			String[] keys=node.keys();
			for(String key:keys){
				String value = node.get(key, null);
				list.add(new QuickOpenerProperty(key.replaceAll("_",""), value));
			}	
		} catch (BackingStoreException e) {e.printStackTrace();}
		return list;
	}
}
