package com.spaceshooter.preferences;

import java.util.ArrayList;

public abstract class Prefs {
    protected int noOfRecords;

    public Prefs(int noOfRecords){
        this.noOfRecords = noOfRecords;
    }

    public abstract ArrayList<?> loadPrefs();
    public abstract void savePrefs(ArrayList<?> list);
    public abstract void clearPrefs();
    public int getNoOfRecords() {
        return noOfRecords;
    }
}
