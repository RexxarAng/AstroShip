package com.spaceshooter.enemy;

import com.spaceshooter.configuration.GameConfiguration;

public class Difficulty {
    private float difficultyTimer;

    public Difficulty() {

    }

    public void increaseDifficulty(float deltaTime) {
        difficultyTimer += deltaTime;
        if (difficultyTimer > GameConfiguration.getInstance().CURRENT_TIME_BETWEEN_SPAWN) {

            difficultyTimer -= GameConfiguration.getInstance().CURRENT_TIME_BETWEEN_SPAWN;
        }
    }
}
