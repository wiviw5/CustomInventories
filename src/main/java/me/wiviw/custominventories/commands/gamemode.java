package me.wiviw.custominventories.commands;

import com.connorlinfoot.bountifulapi.BountifulAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gamemode implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "[CI] Console Cannot run this command.");
            return false;
        }
        if (!(sender.isOp())) {
            sender.sendMessage(ChatColor.RED + "[CI] You are lacking permissions to run this command.");
            return false;
        }
        Player p = (Player) sender;
        if (args.length > 0) {
            gamemodeChange(command.getName(),Bukkit.getPlayer(args[0]));
        } else {
            gamemodeChange(command.getName(),p);
        }
        return true;
    }

    public void gamemodeChange(String command, Player p) {
        switch (command.toLowerCase()) {
            case "gm":
                if (p.getGameMode() == GameMode.CREATIVE) {
                    p.setGameMode(GameMode.SURVIVAL);
                } else {
                    p.setGameMode(GameMode.CREATIVE);
                }
                BountifulAPI.sendActionBar(p, ChatColor.RED + "Set Gamemode to " + p.getGameMode(), 60);
                return;
            case "gms":
                p.setGameMode(GameMode.SURVIVAL);
                BountifulAPI.sendActionBar(p, ChatColor.RED + "Set Gamemode to " + p.getGameMode(), 60);
                return;
            case "gmc":
                p.setGameMode(GameMode.CREATIVE);
                BountifulAPI.sendActionBar(p, ChatColor.RED + "Set Gamemode to " + p.getGameMode(), 60);
                return;
            case "gmss":
                p.setGameMode(GameMode.SPECTATOR);
                BountifulAPI.sendActionBar(p, ChatColor.RED + "Set Gamemode to " + p.getGameMode(), 60);
                return;
            case "gma":
                p.setGameMode(GameMode.ADVENTURE);
                BountifulAPI.sendActionBar(p, ChatColor.RED + "Set Gamemode to " + p.getGameMode(), 60);
        }
    }
}
