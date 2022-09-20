package fr.dwightstudio.dsmapi.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import club.playbox.UtilsPlugin;

public class TestExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("dsmapi.test")) {

                new TestMenu().open(player, 0);
            } else {
                player.sendMessage(UtilsPlugin.getInstance().getCommand("dsm-test").getPermissionMessage());
            }

            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "The sender must be a player.");
            return true;
        }
    }
}
