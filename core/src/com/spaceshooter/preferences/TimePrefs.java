package com.spaceshooter.preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import java.util.ArrayList;

public class TimePrefs extends Prefs{
    private final Preferences timePrefs;
    private static final ArrayList<Float> times = new ArrayList<>();

    public TimePrefs(int noOfRecords) {
        super(noOfRecords);
        timePrefs = Gdx.app.getPreferences("timePrefs");
    }

    @Override
    public ArrayList<Float> loadPrefs() {
        times.clear(); //clears time list and reloads timing saved in preference file
        for (int i = 0; i < noOfRecords; i++) {
            times.add(timePrefs.getFloat("time_" + i,(float)00.00));
        }

        return times;
    }

    @Override
    public void savePrefs(ArrayList<?> times) {
        for (int i = 0; i < noOfRecords; i++) {
            timePrefs.putFloat("time_" + i, (Float)times.get(i));
        }

        timePrefs.flush();
    }

    @Override
    public void clearPrefs() {
        timePrefs.clear();
        timePrefs.flush();
        times.clear();
    }

}
