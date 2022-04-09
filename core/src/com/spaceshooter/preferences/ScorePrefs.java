package com.spaceshooter.preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import java.util.ArrayList;

public class ScorePrefs extends Prefs{
    private final Preferences scorePrefs;
    private static final ArrayList<Integer> scores = new ArrayList<>();

    public ScorePrefs(int noOfRecords) {
        super(noOfRecords);
        scorePrefs = Gdx.app.getPreferences("scorePrefs");
    }

    @Override
    public ArrayList<Integer> loadPrefs() {
        scores.clear(); //clears highscore lists and reloads scores saved in preference file
        for (int i = 0; i < noOfRecords; i++) {
            scores.add(scorePrefs.getInteger("high_score_" + i, 0));
        }

        return scores;
    }

    @Override
    public void savePrefs(ArrayList<?> scores) {
        for (int i = 0; i < noOfRecords; i++) {
            scorePrefs.putInteger("high_score_" + i, (int)scores.get(i)); //first place
        }
        scorePrefs.flush();
    }

    @Override
    public void clearPrefs() {
        scorePrefs.clear();
        scorePrefs.flush();
        scores.clear();
    }

}
