package com.sessonad.quickopener.preferences;

import java.util.ArrayList;
import java.util.List;

public class PreferenceUtils {
	
	public static List<QuickOpenerProperty> getAllMatching(String prefix){
		List<QuickOpenerProperty> list = new ArrayList<QuickOpenerProperty> ();
		list.add(new QuickOpenerProperty("Play", "C:\\Installed\\play-2.0"));
		list.add(new QuickOpenerProperty("Installed", "C:\\Installed"));
		return list;
	}
	
}
