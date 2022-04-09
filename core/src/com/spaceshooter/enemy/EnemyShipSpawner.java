package com.spaceshooter.enemy;

import com.badlogic.gdx.math.Vector2;
import com.spaceshooter.ship.EnemyShip;
import com.spaceshooter.configuration.GameConfiguration;

import java.util.ArrayList;

public class EnemyShipSpawner {
    private int level = 1;
    private final boolean singlePlayer;
    private float enemySpawnTimer;
    private float timeBetweenSpawns;
    private final ArrayList<EnemyShip> enemyShipList;
    private final EnemyShipFactory enemyShipFactory = new EnemyShipFactory();

    public EnemyShipSpawner(ArrayList<EnemyShip> enemyShipList, boolean singlePlayer) {
        this.enemyShipList = enemyShipList;
        this.timeBetweenSpawns = GameConfiguration.getInstance().CURRENT_TIME_BETWEEN_SPAWN;
        this.singlePlayer = singlePlayer;
    }

    public void spawnEnemyShips(float deltaTime) {
        enemySpawnTimer += deltaTime;
        // Check if it should spawn enemy ships based on time and max enemy count
        if (canSpawnEnemyShips()) {
            EnemyShip enemyShip;
            int spawnCount = calculateSpawnCount();
            for (int i = 0; i < spawnCount; i ++) {
                if (Math.random() < 0.8)
                    enemyShip = enemyShipFactory.create(EnemyShipFactory.LASER_ENEMY, level);
                else
                    enemyShip = enemyShipFactory.create(EnemyShipFactory.ROCKET_ENEMY, level);
                randomizeSpawnLocation(enemyShip);
                enemyShipList.add(enemyShip);
            }
            enemySpawnTimer -= GameConfiguration.getInstance().CURRENT_TIME_BETWEEN_SPAWN;
        }
    }

    public void updateLevel(int level) {
        this.level = level;
    }

    public void setTimeBetweenSpawns(float time) {
        this.timeBetweenSpawns = time;
    }

    public float getTimeBetweenSpawns() {
        return this.timeBetweenSpawns;
    }

    public void decreaseTimeBetweenSpawns() {
        this.timeBetweenSpawns -= 1f;
    }

    private int calculateSpawnCount() {
        if (singlePlayer) return 1;
        else return 2;
    }

    private boolean canSpawnEnemyShips() {
        // Check if it should spawn enemy ships based on time and max enemy count
        return enemySpawnTimer > timeBetweenSpawns
                && GameConfiguration.getInstance().CURRENT_MAX_SPAWN > enemyShipList.size();
    }

    private void randomizeSpawnLocation(EnemyShip enemyShip) {
        enemyShip.getHitBox().setPosition(
                GameConfiguration.random.nextFloat() * (GameConfiguration.getInstance().WORLD_WIDTH-5)+5,
                GameConfiguration.getInstance().WORLD_HEIGHT - 5f);
    }

}
