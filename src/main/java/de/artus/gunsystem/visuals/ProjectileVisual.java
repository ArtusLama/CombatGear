package de.artus.gunsystem.visuals;

import org.bukkit.Location;

public interface ProjectileVisual {

    void drawProjectileTrail(Location location);
    void drawProjectileImpact(Location location);
}
