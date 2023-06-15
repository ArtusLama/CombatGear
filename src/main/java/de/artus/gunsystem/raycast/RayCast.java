package de.artus.gunsystem.raycast;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class RayCast {



    public static RayTraceResult shoot(RayConfiguration rayConfiguration) {
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
        RayTraceResult result = null;

        Location startLocation = rayConfiguration.start;
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
            startLocation.getWorld().spawnParticle(Particle.FLAME, startLocation, 0);
            Bukkit.broadcastMessage("[%d] %f".formatted(iterations, rayConfiguration.drop));

            iterations++;
        }
        //TESTING
        rayConfiguration.start.getWorld().spawnParticle(Particle.END_ROD, result.getHitPosition().toLocation(rayConfiguration.start.getWorld()), 0);
    }

    public static Vector getDrop(Vector direction, float drop) {
        return direction.setY(direction.getY() - drop);
    }


}
