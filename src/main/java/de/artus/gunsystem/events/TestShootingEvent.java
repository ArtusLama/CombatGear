package de.artus.gunsystem.events;

import de.artus.gunsystem.raycast.RayCast;
import de.artus.gunsystem.raycast.RayConfiguration;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TestShootingEvent implements Listener {

    @EventHandler
    public void onShoot(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.hasItem()) {
                if (e.getItem().getType() == Material.STICK) {
                    RayCast.shoot(RayConfiguration.fromPlayer(e.getPlayer()));
                }
            }
        }
    }

}
