package com.frapee;

public enum enumString {
    STR_VALUE_FIRST("First"),
    STR_VALUE_SECOND("Second");
    
    public final String label;

    private enumString(String label) {
        this.label = label;
    }  
}
