package com.spaceshooter.preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import java.util.ArrayList;

public class NamePrefs extends Prefs{
    private final Preferences namePrefs;
    private static final ArrayList<String> names = new ArrayList<>();

    public NamePrefs(int noOfRecords) {
        super(noOfRecords);
        namePrefs = Gdx.app.getPreferences("namePrefs");
    }

    @Override
    public ArrayList<String> loadPrefs() {
        names.clear(); //clears high score lists and reloads scores saved in preference file
        for (int i = 0; i < noOfRecords; i++) {
            names.add(namePrefs.getString("name_" + i,"DEFAULT")); //first place
        }
        return names;
    }

    @Override
    public void savePrefs(ArrayList<?> names) {
        for (int i = 0; i < noOfRecords; i++) {
            namePrefs.putString("name_" + i, (String)names.get(i));
        }

        namePrefs.flush();
    }

    @Override
    public void clearPrefs() {
        namePrefs.clear();
        namePrefs.flush();
        names.clear();
    }

    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }
}
