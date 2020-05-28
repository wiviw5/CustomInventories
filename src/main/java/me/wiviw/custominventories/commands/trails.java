package me.wiviw.custominventories.commands;

import me.wiviw.custominventories.CustomInventories;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.wiviw.custominventories.CustomInventories;

public class trails implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "[CI] Console Cannot run this command.");
            return true;
        }
        if (!(sender.isOp())){
            sender.sendMessage(ChatColor.RED + "[CI] You are lacking permissions to run this command.");
            return true;
        }
        Player p = (Player) sender;
        Bukkit.getScheduler().runTaskTimer(JavaPlugin.getProvidingPlugin(CustomInventories.class), () -> {
        	Location pl = p.getLocation();
        	pl.add(0,2.5,0);
            Effect part = Effect.HEART;
    		pl.getWorld().playEffect(pl, part, 0);
        }, 0L, 0L);
		return true;
	}

}
