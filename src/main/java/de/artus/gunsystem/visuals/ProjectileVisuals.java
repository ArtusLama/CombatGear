package de.artus.gunsystem.visuals;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ProjectileVisuals {


    public static void drawStraightBulletTrail(Location start, Location end, double particleSpacing) {

        Vector direction = start.toVector().subtract(end.toVector()).normalize().multiply(-1).multiply(particleSpacing);
        Location spawnLocation = start.clone();




    }

}
