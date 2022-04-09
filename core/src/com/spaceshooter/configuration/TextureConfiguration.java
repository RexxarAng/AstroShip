package com.spaceshooter.configuration;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public final class TextureConfiguration {

    private static TextureConfiguration INSTANCE;

    public static TextureConfiguration getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TextureConfiguration();
        }
        return INSTANCE;
    }

    // For title in screens
    public final Texture TEXTURE_SINGLE_PLAYER_TITLE;
    public final Texture TEXTURE_COOP_TITLE;

    // For player controls
    public final Texture TEXTURE_PLAYER1_CONTROL;
    public final Texture TEXTURE_PLAYER2_CONTROL;

    // For Background
    public final TextureRegion TEXTURE_BACKGROUND_STARSCAPE0;
    public final TextureRegion TEXTURE_BACKGROUND_STARSCAPE1;
    public final TextureRegion TEXTURE_BACKGROUND_STARSCAPE2;
    public final TextureRegion TEXTURE_BACKGROUND_STARSCAPE3;


    // For Player
    public final TextureRegion TEXTURE_PLAYER_SHIP_TYPE1_BLUE;
    public final TextureRegion TEXTURE_PLAYER_SHIP_TYPE1_GREEN;
    public final TextureRegion TEXTURE_PLAYER_SHIP_TYPE1_ORANGE;
    public final TextureRegion TEXTURE_PLAYER_SHIELD;
    public final TextureRegion TEXTURE_PLAYER_SHIELD_INDICATOR;
    public final TextureRegion TEXTURE_PLAYER_SHIELD_EMPTY_INDICATOR;
    public final TextureRegion TEXTURE_PLAYER_AMMO;
    public final TextureRegion TEXTURE_PLAYER_AMMO_HALF;

    // For Player 1 & 2 current texture
    public TextureRegion TEXTURE_PLAYER1;
    public TextureRegion TEXTURE_PLAYER2;
    public TextureRegion TEXTURE_PLAYER1_LASER;
    public TextureRegion TEXTURE_PLAYER2_LASER;

    // For Enemy
    public final TextureRegion TEXTURE_ENEMY_SHIP_STAR_RANKING;
    public final TextureRegion TEXTURE_ENEMY_SHIP_TYPE1_BLACK;
    public final TextureRegion TEXTURE_ENEMY_SHIP_TYPE1_BLUE;
    public final TextureRegion TEXTURE_ENEMY_SHIP_TYPE1_GREEN;
    public final TextureRegion TEXTURE_ENEMY_SHIP_TYPE1_RED;
    public final TextureRegion TEXTURE_ENEMY_SHIP_TYPE2_BLACK;
    public final TextureRegion TEXTURE_ENEMY_SHIP_TYPE2_BLUE;
    public final TextureRegion TEXTURE_ENEMY_SHIP_TYPE2_GREEN;
    public final TextureRegion TEXTURE_ENEMY_SHIP_TYPE2_RED;
    public final ArrayList<TextureRegion> TEXTURE_ENEMY_SHIP_TYPE1_LIST;
    public final ArrayList<TextureRegion> TEXTURE_ENEMY_SHIP_TYPE2_LIST;

    public final TextureRegion TEXTURE_ENEMY_SHIELD;

    // For Projectile
    public final TextureRegion TEXTURE_PROJECTILE_LASER_FRIENDLY_BLUE;
    public final TextureRegion TEXTURE_PROJECTILE_LASER_FRIENDLY_GREEN;
    public final TextureRegion TEXTURE_PROJECTILE_LASER_FRIENDLY_ORANGE;
    public final TextureRegion TEXTURE_PROJECTILE_LASER_NOT_FRIENDLY;
    public final TextureRegion TEXTURE_PROJECTILE_ROCKET_FRIENDLY_GREY;
    public final TextureRegion TEXTURE_PROJECTILE_ROCKET_FRIENDLY_BLUE;
    public final TextureRegion TEXTURE_PROJECTILE_ROCKET_FRIENDLY_GREEN;
    public final TextureRegion TEXTURE_PROJECTILE_ROCKET_NOT_FRIENDLY;
    public final TextureRegion TEXTURE_PROJECTILE_ROCKET_FIRE_NOT_FRIENDLY;
    public final TextureRegion TEXTURE_PROJECTILE_ROCKET_FIRE_FRIENDLY_BLUE;
    public final TextureRegion TEXTURE_PROJECTILE_ROCKET_FIRE_FRIENDLY_GREEN;
    public final TextureRegion TEXTURE_PROJECTILE_TUNGSTEN_SLUG;

    // For Explosion
    public final Texture TEXTURE_EXPLOSION;

    // For PowerUp
    public final TextureRegion TEXTURE_POWERUP_PLAYER1_BACKGROUND;
    public final TextureRegion TEXTURE_POWERUP_PLAYER2_BACKGROUND;
    public final TextureRegion TEXTURE_POWERUP_ROCKET;
    public final TextureRegion TEXTURE_POWERUP_RAILGUN_AMMO;
    public final TextureRegion TEXTURE_POWERUP_PLAYER1_SHIELD;
    public final TextureRegion TEXTURE_POWERUP_PLAYER2_SHIELD;


    private TextureConfiguration() {
        TEXTURE_SINGLE_PLAYER_TITLE = new Texture("Single_Player_Yellow.png");
        TEXTURE_COOP_TITLE = new Texture("Co-Op_Yellow.png");
        TEXTURE_PLAYER1_CONTROL = new Texture("Player1_Controls.png");
        TEXTURE_PLAYER2_CONTROL = new Texture("Player2_Controls.png");

        TextureAtlas textureAtlas = new TextureAtlas("images.atlas");

        TEXTURE_BACKGROUND_STARSCAPE0 = textureAtlas.findRegion("Starscape00");
        TEXTURE_BACKGROUND_STARSCAPE1 = textureAtlas.findRegion("Starscape01");
        TEXTURE_BACKGROUND_STARSCAPE2 = textureAtlas.findRegion("Starscape02");
        TEXTURE_BACKGROUND_STARSCAPE3 = textureAtlas.findRegion("Starscape03");

        TEXTURE_ENEMY_SHIP_STAR_RANKING = textureAtlas.findRegion("star_gold");
        TEXTURE_PLAYER_SHIP_TYPE1_BLUE = textureAtlas.findRegion("playerShip1_blue");
        TEXTURE_PLAYER_SHIP_TYPE1_GREEN = textureAtlas.findRegion("playerShip1_green");
        TEXTURE_PLAYER_SHIP_TYPE1_ORANGE = textureAtlas.findRegion("playerShip1_orange");
        TEXTURE_PLAYER_SHIELD = textureAtlas.findRegion("shield1");
        TEXTURE_PLAYER_SHIELD_INDICATOR = textureAtlas.findRegion("shield_silver");
        TEXTURE_PLAYER_SHIELD_EMPTY_INDICATOR = textureAtlas.findRegion("shield_bronze");
        TEXTURE_PLAYER_AMMO = textureAtlas.findRegion("bolt_gold");
        TEXTURE_PLAYER_AMMO_HALF = textureAtlas.findRegion("bold_silver");

        TEXTURE_ENEMY_SHIP_TYPE1_BLACK = textureAtlas.findRegion("enemyBlack1");
        TEXTURE_ENEMY_SHIP_TYPE1_BLUE = textureAtlas.findRegion("enemyBlue1");
        TEXTURE_ENEMY_SHIP_TYPE1_GREEN = textureAtlas.findRegion("enemyGreen1");
        TEXTURE_ENEMY_SHIP_TYPE1_RED = textureAtlas.findRegion("enemyRed1");
        TEXTURE_ENEMY_SHIP_TYPE2_BLACK = textureAtlas.findRegion("enemyBlack4");
        TEXTURE_ENEMY_SHIP_TYPE2_BLUE = textureAtlas.findRegion("enemyBlue4");
        TEXTURE_ENEMY_SHIP_TYPE2_GREEN = textureAtlas.findRegion("enemyGreen4");
        TEXTURE_ENEMY_SHIP_TYPE2_RED = textureAtlas.findRegion("enemyRed4");

        TEXTURE_ENEMY_SHIP_TYPE1_LIST = new ArrayList<>();
        TEXTURE_ENEMY_SHIP_TYPE1_LIST.add(TEXTURE_ENEMY_SHIP_TYPE1_BLACK);
        TEXTURE_ENEMY_SHIP_TYPE1_LIST.add(TEXTURE_ENEMY_SHIP_TYPE1_BLUE);
        TEXTURE_ENEMY_SHIP_TYPE1_LIST.add(TEXTURE_ENEMY_SHIP_TYPE1_GREEN);
        TEXTURE_ENEMY_SHIP_TYPE1_LIST.add(TEXTURE_ENEMY_SHIP_TYPE1_RED);
        TEXTURE_ENEMY_SHIP_TYPE2_LIST = new ArrayList<>();
        TEXTURE_ENEMY_SHIP_TYPE2_LIST.add(TEXTURE_ENEMY_SHIP_TYPE2_BLACK);
        TEXTURE_ENEMY_SHIP_TYPE2_LIST.add(TEXTURE_ENEMY_SHIP_TYPE2_BLUE);
        TEXTURE_ENEMY_SHIP_TYPE2_LIST.add(TEXTURE_ENEMY_SHIP_TYPE2_GREEN);
        TEXTURE_ENEMY_SHIP_TYPE2_LIST.add(TEXTURE_ENEMY_SHIP_TYPE2_RED);

        TEXTURE_ENEMY_SHIELD = new TextureRegion(textureAtlas.findRegion("shield1"));
        TEXTURE_ENEMY_SHIELD.flip(false, true);

        TEXTURE_PROJECTILE_LASER_FRIENDLY_BLUE = textureAtlas.findRegion("laserBlue15");
        TEXTURE_PROJECTILE_LASER_FRIENDLY_GREEN = textureAtlas.findRegion("laserGreen07");
        TEXTURE_PROJECTILE_LASER_FRIENDLY_ORANGE = textureAtlas.findRegion("laserOrange03");
        TEXTURE_PROJECTILE_LASER_NOT_FRIENDLY = textureAtlas.findRegion("laserRed15");
        TEXTURE_PROJECTILE_ROCKET_FRIENDLY_GREY = textureAtlas.findRegion("Missile02A");
        TEXTURE_PROJECTILE_ROCKET_FRIENDLY_BLUE = textureAtlas.findRegion("Missile02B");
        TEXTURE_PROJECTILE_ROCKET_FRIENDLY_GREEN = textureAtlas.findRegion("Missile02E");
        TEXTURE_PROJECTILE_ROCKET_NOT_FRIENDLY = textureAtlas.findRegion("Missile02C");
        TEXTURE_PROJECTILE_ROCKET_NOT_FRIENDLY.flip(false, true);
        TEXTURE_PROJECTILE_ROCKET_FIRE_NOT_FRIENDLY = textureAtlas.findRegion("laserRed04");
        TEXTURE_PROJECTILE_ROCKET_FIRE_FRIENDLY_BLUE = textureAtlas.findRegion("laserBlue04");
        TEXTURE_PROJECTILE_ROCKET_FIRE_FRIENDLY_GREEN = textureAtlas.findRegion("laserGreen04");
        TEXTURE_PROJECTILE_TUNGSTEN_SLUG = new TextureRegion(new Texture("AntiMissleCannon.png"));

        TEXTURE_PLAYER1 = TEXTURE_PLAYER_SHIP_TYPE1_BLUE;
        TEXTURE_PLAYER1_LASER = TEXTURE_PROJECTILE_LASER_FRIENDLY_BLUE;

        TEXTURE_PLAYER2 = TEXTURE_PLAYER_SHIP_TYPE1_GREEN;
        TEXTURE_PLAYER2_LASER = TEXTURE_PROJECTILE_LASER_FRIENDLY_GREEN;

        TEXTURE_EXPLOSION = new Texture("explosion.png");

        TEXTURE_POWERUP_PLAYER1_BACKGROUND = textureAtlas.findRegion("powerupBlue");
        TEXTURE_POWERUP_PLAYER2_BACKGROUND = textureAtlas.findRegion("powerupGreen");
        TEXTURE_POWERUP_ROCKET = textureAtlas.findRegion("Missile02A");
        TEXTURE_POWERUP_RAILGUN_AMMO = textureAtlas.findRegion("things_gold");
        TEXTURE_POWERUP_PLAYER1_SHIELD = textureAtlas.findRegion("powerupBlue_shield");
        TEXTURE_POWERUP_PLAYER2_SHIELD = textureAtlas.findRegion("powerupGreen_shield");

    }
}
