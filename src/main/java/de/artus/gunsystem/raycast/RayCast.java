package de.artus.gunsystem.raycast;

import de.artus.gunsystem.GunSystem;
import de.artus.gunsystem.testing.commands.SetDropCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Marker;
import org.bukkit.scheduler.BukkitRunnable;
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


    //FIXME Currently like a rocket launcher DONT USE METHOD
    public static void shootSlowWithDrag(RayConfiguration rayConfiguration, float speed, float drag) {
        ArmorStand projectile = (ArmorStand) rayConfiguration.start.getWorld().spawnEntity(rayConfiguration.start, EntityType.ARMOR_STAND);


        projectile.setGravity(false);
        projectile.setInvulnerable(true);
        projectile.setSmall(true);

        SetDropCommand.drop = 0.005f;
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                //FIXME clone direction and set and change
                projectile.teleport(projectile.getLocation().add(getDrop(rayConfiguration.direction, drag).normalize().multiply(speed)));
                rayConfiguration.maxDistance = 0.1;
                rayConfiguration.start = projectile.getEyeLocation();
                rayConfiguration.filter = e -> !e.equals(projectile);
                RayTraceResult result = shoot(rayConfiguration);
                if (result != null) {
                    projectile.remove();
                    rayConfiguration.start.getWorld().createExplosion(result.getHitPosition().toLocation(rayConfiguration.start.getWorld()), 10, true);
                    cancel();
                }
                i++;
                if (i > 200) {
                    projectile.remove();
                    cancel();
                }
            }
        }.runTaskTimer(GunSystem.getPlugin(GunSystem.class), 1, 1);

        //shoot(rayConfiguration);

    }



    public static Vector getDrop(Vector direction, float drop) {
        return direction.setY(direction.getY() - drop);
    }


}
