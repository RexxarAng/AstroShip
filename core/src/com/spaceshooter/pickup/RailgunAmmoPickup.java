package com.spaceshooter.pickup;

import com.badlogic.gdx.math.Rectangle;
import com.spaceshooter.Player;
import com.spaceshooter.configuration.TextureConfiguration;
import com.spaceshooter.ship.PlayerShip;
import com.spaceshooter.weapon.Railgun;
import com.spaceshooter.weapon.Weapon;

public class RailgunAmmoPickup extends Pickup {

    public RailgunAmmoPickup(Rectangle hitBox, Player player) {
        super(hitBox, TextureConfiguration.getInstance().TEXTURE_POWERUP_RAILGUN_AMMO, player);
    }

    @Override
    public boolean consumeByPlayer(PlayerShip playerShip) {
        if (playerShip.getPlayer() != this.player) {
            setTextForDisplayUponPickup("Wasted");
            setIsNegativeEffect(true);
            return true;
        }
        Weapon weapon = playerShip.getRailGun();
        if (weapon == null) return false;
        if(((Railgun)weapon).getAmmo() > 60)
            return false;
        int ammo = 15;
        setTextForDisplayUponPickup("Anti Missile Ammo +" + ammo);
        ((Railgun)weapon).addAmmo(ammo);
        return true;
    }
}
