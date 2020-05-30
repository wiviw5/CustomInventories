package me.wiviw.custominventories.commands;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class textreturn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender.isOp()){
            if (command.getName().equalsIgnoreCase("returntext")){
                if (!(sender instanceof Player)){ sender.sendMessage("[CI] Console Cannot run this command."); return true; }
                Player p = (Player) sender; // assigning sender to p
                if(args.length <= 0){ p.sendMessage(ChatColor.RED + "[CI] Please add arguments to have your message sent."); return true; }
                String input = "";
                for (String arg : args) {
                    input += arg + (" ");
                }
                input = input.trim();
                input = ChatColor.translateAlternateColorCodes('&',input);
                p.sendMessage(input);
                return true;
            }
            if (command.getName().equalsIgnoreCase("returntextall")){
                if(args.length <= 0){ sender.sendMessage(ChatColor.RED + "[CI] Please add arguments to have your message sent."); return true; }
                String input = "";
                for (String arg : args) {
                    input += arg + (" ");
                }
                input = input.trim();
                input = ChatColor.translateAlternateColorCodes('&',input);
                Bukkit.broadcastMessage(input);
                return true;
            }
        } //Todo Update Op Check format
        return true;
    }
}
