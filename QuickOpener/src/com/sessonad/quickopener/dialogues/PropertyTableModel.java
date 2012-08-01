package com.sessonad.quickopener.dialogues;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.sessonad.quickopener.preferences.PreferenceUtils;
import com.sessonad.quickopener.preferences.QuickOpenerProperty;


public class PropertyTableModel extends AbstractTableModel{
	

	private static final long serialVersionUID = 1L;
	String[] columnNames= {"Name","Path"};
	Object[][] data=null;

	public PropertyTableModel(String prefix) {
	    try {
	        List<QuickOpenerProperty> prefs = PreferenceUtils.getAll(prefix);
	        this.data = new Object[prefs.size()][2];
	        for (int i = 0; i < prefs.size(); i++) {
	            QuickOpenerProperty pref = prefs.get(i);
	            this.data[i]=new String[]{pref.getDescription(),pref.getValue()};
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}

	@Override
	public int getColumnCount() {
	    return columnNames.length;
	}

	@Override
	public int getRowCount() {
	    return data.length;
	}

	@Override
	public String getColumnName(int col) {
	    return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
	    return data[row][col];
	}

	public Class getColumnClass(int c) {
	    return String.class;
	}
	
}
