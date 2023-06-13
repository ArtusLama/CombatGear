package de.artus.gunsystem.raycast;

import org.bukkit.Bukkit;
import org.bukkit.util.RayTraceResult;

public class RayCast {



    public static void shoot(RayConfiguration rayConfiguration) {

        RayTraceResult result = rayConfiguration.start.getWorld().rayTrace(
                rayConfiguration.start,
                rayConfiguration.direction,
                rayConfiguration.maxDistance,
                rayConfiguration.fluidCollision,
                rayConfiguration.ignorePassableBlocks,
                0.1,
                rayConfiguration.filter);

        if (result == null) {

        } else if (result.getHitEntity() != null) {

        } else if (result.getHitBlock() != null) {
            Bukkit.broadcastMessage(result.getHitBlockFace().name());
        }


    }


}
