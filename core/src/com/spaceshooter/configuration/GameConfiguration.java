package com.spaceshooter.configuration;

import com.badlogic.gdx.Input;

import java.util.Random;

public class GameConfiguration {

    private static GameConfiguration INSTANCE;

    public static GameConfiguration getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameConfiguration();
        }
        return INSTANCE;
    }

    public static Random random = new Random();

    // World perimeters
    public final int WORLD_WIDTH = (int)(128*1.5);
    public final int WORLD_HEIGHT = (int)(72*1.5);

    // Difficulty increase rate
    public final int DIFFICULTY_INCREASE_RATE_IN_SECONDS = 15;

    // Ship width, height
    public final int DEFAULT_SHIP_WIDTH = 5;
    public final int DEFAULT_SHIP_HEIGHT = 5;
    public int CURRENT_PLAYER_SHIP_WIDTH = DEFAULT_SHIP_WIDTH;
    public int CURRENT_PLAYER_SHIP_HEIGHT = DEFAULT_SHIP_HEIGHT;
    public int CURRENT_ENEMY_SHIP_WIDTH = DEFAULT_SHIP_WIDTH;
    public int CURRENT_ENEMY_SHIP_HEIGHT = DEFAULT_SHIP_HEIGHT;

    // Default Projectile speed and damage
    public final float DEFAULT_TIME_BETWEEN_SHOTS = 1f;
    public final float DEFAULT_PROJECTILE_MOVEMENT_SPEED = 45f;
    public final int DEFAULT_PROJECTILE_DAMAGE = 1;
    public final float DEFAULT_LASER_WIDTH = 0.1f * DEFAULT_SHIP_WIDTH;
    public final float DEFAULT_ROCKET_WIDTH = 0.5f * DEFAULT_SHIP_WIDTH;

    // Default Rocket tracking duration
    public final float DEFAULT_HOMING_TRACKING_DURATION = 3f;
    public final float DEFAULT_HOMING_MOVEMENT_SPEED = 20f;
    public final float DEFAULT_HOMING_ACCELERATION_SPEED = 15f;
    public final float DEFAULT_HOMING_ROTATION_SPEED = 1.5f;

    // Default Railgun
    public final float DEFAULT_RAILGUN_MOVEMENT_SPEED = 60f;

    // Spawn timer
    public final float DEFAULT_TIME_BETWEEN_SPAWN = 2f;
    public float CURRENT_TIME_BETWEEN_SPAWN = DEFAULT_TIME_BETWEEN_SPAWN;

    // Enemy default stats
    public final int DEFAULT_MAX_ENEMY_MOVEMENT_SPEED = 50;
    public final int DEFAULT_ENEMY_SHIELD = 1;
    public final int DEFAULT_ENEMY_DAMAGE = 1;
    public final float DEFAULT_MAX_ENEMY_TIME_BETWEEN_SHOTS = 1.5f;
    public final float DEFAULT_ENEMY_PROJECTILE_SPEED = 45f;
    public final float DEFAULT_ENEMY_DIRECTION_CHANGE_FREQUENCY = 1.5f;

    // Default Enemy's points for score
    public final int DEFAULT_SCORE_POINTS = 200;

    // Max Spawn
    public int DEFAULT_MAX_SPAWN = 20;
    public int CURRENT_MAX_SPAWN = DEFAULT_MAX_SPAWN;

    // Player Ships Default
    public final int PLAYER_1 = 1;
    public final int PLAYER_2 = 2;
    public final int DEFAULT_PLAYER_MOVEMENT_SPEED = 60;
    public final int DEFAULT_PLAYER_SHIELD = 3;
    public final float DEFAULT_PLAYER_SHIELD_REGEN_SPEED = 5f;

    // Player default movement keys;
    public final int PLAYER_1_UP_KEY = Input.Keys.UP;
    public final int PLAYER_1_LEFT_KEY = Input.Keys.LEFT;
    public final int PLAYER_1_DOWN_KEY = Input.Keys.DOWN;
    public final int PLAYER_1_RIGHT_KEY = Input.Keys.RIGHT;
    public final int PLAYER_1_SECONDARY_KEY = Input.Keys.SLASH;
    public final int PLAYER_2_UP_KEY = Input.Keys.W;
    public final int PLAYER_2_LEFT_KEY = Input.Keys.A;
    public final int PLAYER_2_DOWN_KEY = Input.Keys.S;
    public final int PLAYER_2_RIGHT_KEY = Input.Keys.D;
    public final int PLAYER_2_SECONDARY_KEY = Input.Keys.F;

    // Default lives for different game modes
    public final int SINGLE_PLAYER_DEFAULT_LIVES = 1;
    public final int COOP_DEFAULT_LIVES = 1;

    // Number of records
    public final int DEFAULT_NO_OF_RECORDS = 9;
    private GameConfiguration() {
    }
}
