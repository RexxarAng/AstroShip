package com.spaceshooter.pickup;

import com.badlogic.gdx.math.Rectangle;
import com.spaceshooter.Player;
import com.spaceshooter.configuration.GameConfiguration;
import com.spaceshooter.configuration.TextureConfiguration;
import com.spaceshooter.ship.PlayerShip;

public class ShieldPickup extends Pickup {

    public ShieldPickup(Rectangle hitBox, Player player) {
        super(hitBox, TextureConfiguration.getInstance().TEXTURE_POWERUP_PLAYER1_SHIELD, player);
        if (player.getId() != GameConfiguration.getInstance().PLAYER_1)
            this.textureRegion = TextureConfiguration.getInstance().TEXTURE_POWERUP_PLAYER2_SHIELD;
    }

    @Override
    public boolean consumeByPlayer(PlayerShip playerShip) {
        if (playerShip.getPlayer() != this.player) {
            setTextForDisplayUponPickup("Wasted");
            setIsNegativeEffect(true);
            return true;
        }

        if (playerShip.getShield().getMaxCapacity() <= 6) {
            playerShip.getShield().increaseShieldCapacity(1);
            setTextForDisplayUponPickup("Shield Capacity +1");
            return true;
        }

        if (!playerShip.getShield().isAtMaxCapacity()) {
            playerShip.getShield().increaseShield(1);
            setTextForDisplayUponPickup("Shield +1");
            return true;
        }

        return false;
    }
}
