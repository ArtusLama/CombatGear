package de.artus.gunsystem;

import de.artus.gunsystem.testing.commands.SetDropCommand;
import de.artus.gunsystem.testing.events.TestShootingEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class GunSystem extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new TestShootingEvent(), this);

        getCommand("test-setdrop").setExecutor(new SetDropCommand());
    }

    @Override
    public void onDisable() {

    }
}
