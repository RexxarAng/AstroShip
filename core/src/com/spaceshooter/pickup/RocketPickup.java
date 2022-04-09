package com.spaceshooter.pickup;

import com.badlogic.gdx.math.Rectangle;
import com.spaceshooter.Player;
import com.spaceshooter.configuration.TextureConfiguration;
import com.spaceshooter.ship.PlayerShip;
import com.spaceshooter.weapon.HomingRocketLauncher;
import com.spaceshooter.weapon.Weapon;

public class RocketPickup extends Pickup {

    public RocketPickup(Rectangle hitBox, Player player) {
        super(hitBox, TextureConfiguration.getInstance().TEXTURE_POWERUP_ROCKET, player);
    }

    @Override
    public boolean consumeByPlayer(PlayerShip playerShip) {
        if (playerShip.getPlayer() != this.player) {
            setTextForDisplayUponPickup("Wasted");
            setIsNegativeEffect(true);
            return true;
        }
        Weapon weapon = playerShip.getWeapon();
        if (weapon == null) return false;
        if (!(weapon instanceof HomingRocketLauncher)) {
            playerShip.setWeapon(new HomingRocketLauncher());
            setTextForDisplayUponPickup("Rocket Launcher!");
            return true;
        }
        // Increase projectile speed by 10%
        weapon.setProjectileMovementSpeed(weapon.getProjectileMovementSpeed() * 1.1f);

        // Increase shooting speed by 10%
        weapon.setTimeBetweenShots(weapon.getTimeBetweenShots() * 0.9f);

        setTextForDisplayUponPickup("Rocket speed 10%");
        return true;
    }
}
