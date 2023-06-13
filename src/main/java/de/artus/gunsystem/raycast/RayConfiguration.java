package de.artus.gunsystem.raycast;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class RayConfiguration {


    public Location start;
    public Vector direction;
    public double maxDistance;
    public FluidCollisionMode fluidCollision;
    public boolean ignorePassableBlocks;
    public @Nullable Predicate<Entity> filter;

    public RayConfiguration(Location start, Vector direction, double maxDistance, FluidCollisionMode fluidCollision, boolean ignorePassableBlocks, @Nullable Predicate<Entity> filter) {
        this.start = start;
        this.direction = direction;
        this.maxDistance = maxDistance;
        this.fluidCollision = fluidCollision;
        this.ignorePassableBlocks = ignorePassableBlocks;
        this.filter = filter;
    }


    public static RayConfiguration fromPlayer(Player player) {
        return new RayConfiguration(
                player.getEyeLocation(),
                player.getEyeLocation().getDirection(),
                100,
                FluidCollisionMode.NEVER,
                false,
                p -> !p.equals(player));
    }


}
