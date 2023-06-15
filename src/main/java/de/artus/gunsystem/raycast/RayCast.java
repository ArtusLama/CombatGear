package de.artus.gunsystem.raycast;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class RayCast {



    public static RayTraceResult shoot(RayConfiguration rayConfiguration) {
        //FIXME: item is NOT passable! Add to filter
        RayTraceResult result = rayConfiguration.start.getWorld().rayTrace(
                    rayConfiguration.start,
                    rayConfiguration.direction,
                    rayConfiguration.maxDistance,
                    rayConfiguration.fluidCollision,
                    rayConfiguration.ignorePassableBlocks,
                    0.1,
                    rayConfiguration.filter);

        return result;
    }

    public static void shootWithDrop(RayConfiguration rayConfiguration) {
        //FIXME lagging when shooting straight up (maybe add y offset limit)
        RayTraceResult result = null;

        Location startLocation = rayConfiguration.start.clone();
        Vector direction = rayConfiguration.direction;
        rayConfiguration.maxDistance = 0.01; //TODO maybe change to better

        int iterations = 0;
        int maxIterations = 10000; //TODO get from config or other
        while (result == null && iterations <= maxIterations) {
            RayConfiguration configuration = new RayConfiguration(
                    startLocation,
                    direction,
                    rayConfiguration.maxDistance,
                    rayConfiguration.fluidCollision,
                    rayConfiguration.ignorePassableBlocks,
                    rayConfiguration.filter,
                    0
            );

            direction = getDrop(direction, rayConfiguration.drop);
            result = shoot(configuration);

            startLocation = startLocation.add(direction);

            //TESTING
            startLocation.getWorld().spawnParticle(Particle.DRIP_LAVA, startLocation, 0, 0, 0, 0, 0, null, true);
            Bukkit.broadcastMessage("[%d] ".formatted(iterations) + getDrop(direction, rayConfiguration.drop).getY());

            iterations++;
        }
        //TESTING
        //NOT ACCURATE
        startLocation.getWorld().spawnParticle(Particle.DRAGON_BREATH, result.getHitPosition().clone().subtract(direction.clone().multiply(0.5)).toLocation(startLocation.getWorld()), 0);
    }

    public static Vector getDrop(Vector direction, float drop) {
        return direction.setY(direction.getY() - drop);
    }


}
