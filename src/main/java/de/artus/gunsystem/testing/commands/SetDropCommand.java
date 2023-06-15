package de.artus.gunsystem.testing.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetDropCommand implements CommandExecutor {

    public static float drop = 0.0005f;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        drop = Float.parseFloat(strings[0]);
        return false;
    }
}
