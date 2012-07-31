package com.sessonad.quickopener.preferences;

public class QuickOpenerProperty {
    
    private String description;
    private String value;

    public QuickOpenerProperty(String description, String value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
    
}
