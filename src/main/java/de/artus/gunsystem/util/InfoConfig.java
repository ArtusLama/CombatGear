package de.artus.gunsystem.util;

import de.artus.gunsystem.GunSystem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class InfoConfig {

    //TODO: better config name
    public static File ConfigFile = new File(GunSystem.getPlugin(GunSystem.class).getDataFolder(), ".yml");
    public static FileConfiguration config = YamlConfiguration.loadConfiguration(ConfigFile);

    public static void save() {
        try {
            config.save(ConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
