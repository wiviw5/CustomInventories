package me.wiviw.custominventories.commands;

import com.connorlinfoot.bountifulapi.BountifulAPI;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gamemode implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "[CI] Console Cannot run this command.");
            return true;
        }
        if (!(sender.isOp())){
            sender.sendMessage(ChatColor.RED + "[CI] You are lacking permissions to run this command.");
            return true;
        }
        Player p = (Player) sender;
        if (command.getName().equalsIgnoreCase("gm")){
            if (p.getGameMode()==GameMode.CREATIVE){
                p.setGameMode(GameMode.SURVIVAL);
            }else{
                p.setGameMode(GameMode.CREATIVE);
            }
            BountifulAPI.sendActionBar(p,ChatColor.RED + "Set Gamemode to " + p.getGameMode() , 60);
            return true;
        }
        if (command.getName().equalsIgnoreCase("gms")){
            p.setGameMode(GameMode.SURVIVAL);
            BountifulAPI.sendActionBar(p,ChatColor.RED + "Set Gamemode to " + p.getGameMode() , 60);
            return true;
        }
        if (command.getName().equalsIgnoreCase("gmc")){
            p.setGameMode(GameMode.CREATIVE);
            BountifulAPI.sendActionBar(p,ChatColor.RED + "Set Gamemode to " + p.getGameMode() , 60);
            return true;
        }
        if (command.getName().equalsIgnoreCase("gmss")){
            p.setGameMode(GameMode.SPECTATOR);
            BountifulAPI.sendActionBar(p,ChatColor.RED + "Set Gamemode to " + p.getGameMode() , 60);
            return true;
        }
        return true;
    }
}
