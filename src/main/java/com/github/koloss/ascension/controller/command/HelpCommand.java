package com.github.koloss.ascension.controller.command;

import com.github.koloss.ascension.controller.event.HelpEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (sender instanceof Player) {
            HelpEvent helpEvent = new HelpEvent((Player) sender, true);
            Bukkit.getPluginManager().callEvent(helpEvent);
        }

        return true;
    }
}
