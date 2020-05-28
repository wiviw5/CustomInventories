package me.wiviw.custominventories.commands;

import net.md_5.bungee.api.chat.*;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

public class itemdependants implements CommandExecutor {
	public static String colorize(String str) { return str.replace('&', '§'); }
	
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "[CI] Console Cannot run this command.");
            return true;
        }
        if (!(sender.isOp())){
            sender.sendMessage(ChatColor.RED + "[CI] You are lacking permissions to run this command.");
            return true;
        }
        Player p = (Player) sender;
        if(p.getInventory().getItemInHand().getType()== Material.AIR){
            p.sendMessage(ChatColor.RED + "[CI] You cannot be holding nothing.");
            return true;
        }
        if (command.getName().equalsIgnoreCase("rename")) {
        	String name= "";
            for (String arg : args) {
                name += arg + (" ");
            }
            name = name.trim();
            if(args.length <= 0){
                ItemMeta meta = p.getInventory().getItemInHand().getItemMeta();
                meta.setDisplayName(" ");
                p.getInventory().getItemInHand().setItemMeta(meta);
                return true;
            }
            if(args[0].equals("listname")){
                ItemMeta meta = p.getInventory().getItemInHand().getItemMeta();
                if (meta.hasDisplayName()){
                    String nameitem = meta.getDisplayName();
                    nameitem = nameitem.replace('§', '&');
                    TextComponent msg = new TextComponent(ChatColor.AQUA + "[CI] " + ChatColor.RESET + nameitem.replace('&', '§'));
                    msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/rename " + nameitem));
                    msg.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/rename " + nameitem).create()));
                    p.spigot().sendMessage(msg);
                    return true;
                }else{
                    p.sendMessage(ChatColor.RED + "[CI] Item must has a name other then default");
                    return true;
                }
            }
            ItemMeta meta = p.getInventory().getItemInHand().getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            p.getInventory().getItemInHand().setItemMeta(meta);
            return true;
        }
        if (command.getName().equalsIgnoreCase("relore")) {
        	if (args.length <= 0){
                p.sendMessage(ChatColor.RED + "[CI] You must specify lore to be added, For Blank lines: \"blank\"");
                return true;
            } // Check for if they did not include any args
            ItemMeta meta = p.getInventory().getItemInHand().getItemMeta(); //Meta Gathering
            List<String> lore;
            if (meta.hasLore()){
                lore = meta.getLore();
            }else{
                lore = new ArrayList<>(1);
            }// After checks for lore ^
            if (args[0].equals("blank")){
                lore.add("");
                meta.setLore(lore);
                p.getInventory().getItemInHand().setItemMeta(meta);
            }
            else if (args[0].equals("remove")){
                if (args.length <= 1){
                    p.sendMessage(ChatColor.RED + "[CI] You must specify the line or all lines to be removed");
                    return true;
                }
                if (args[1].equals("all")){
                    meta.setLore(null);
                    p.getInventory().getItemInHand().setItemMeta(meta);
                    return true;
                }// Removing All lines of lore on an item
                if (lore.isEmpty()){
                    p.sendMessage(ChatColor.RED + "[CI] You cannot use remove if the item doesn't have lore!");
                    return true;
                } //Catches for stuff without lore
                int specified;
                try {
                    specified = Integer.parseInt(args[1]);
                }
                catch(NumberFormatException NFE){
                    p.sendMessage(ChatColor.RED + "[CI] You can't put a letter there...");
                    return true;
                }
                specified--;
                int loresize = lore.size();
                loresize--;
                if (specified> loresize){
                    p.sendMessage(ChatColor.RED + "[CI] You can't remove an items lore line if it doesn't exist!");
                    return true;
                } //Catches for stuff out of bounds
                if (specified<0){
                    p.sendMessage(ChatColor.RED + "[CI] You can't remove a negative lore line?");
                    return true;
                }
                lore.remove(specified);
                meta.setLore(lore);
                p.getInventory().getItemInHand().setItemMeta(meta);
                return true;
            }
            else if (args[0].equals("add")){
                String loretoadd = "";
                for (int i=1;i<args.length; i++){
                    loretoadd = loretoadd + (colorize(args[i]) + (" "));
                }
                loretoadd = loretoadd.trim();
                lore.add(loretoadd);
                meta.setLore(lore);
                p.getInventory().getItemInHand().setItemMeta(meta);
            }
            else if (args[0].equals("set")){
                if (lore.isEmpty()){
                    p.sendMessage(ChatColor.RED + "[CI] You cannot use set if the item doesn't have lore!");
                    return true;
                } //Catches for stuff without lore
                if (args.length<=1){
                    p.sendMessage(ChatColor.RED + "[CI] You must specify a line to set!");
                    return true;
                }
                int specified;
                try {
                    specified = Integer.parseInt(args[1]);
                }
                catch(NumberFormatException NFE){
                    p.sendMessage(ChatColor.RED + "[CI] You can't put a letter there...");
                    return true;
                }
                specified--;
                int loresize = lore.size();
                loresize--;
                if (specified> loresize){
                    p.sendMessage(ChatColor.RED + "[CI] You can't set an items lore line if it doesn't exist!");
                    return true;
                } //Catches for stuff out of bounds
                if (specified<0){
                    p.sendMessage(ChatColor.RED + "[CI] You can't set a negative lore line?");
                    return true;
                }
                if (args.length<=2){
                    lore.set(specified, "");
                    meta.setLore(lore);
                    p.getInventory().getItemInHand().setItemMeta(meta);
                    return true;
                }// If what you want to set it to is a blank line
                String loretoset = "";
                for (int i=2;i<args.length; i++){
                    loretoset = loretoset + (colorize(args[i]) + (" "));
                }
                loretoset = loretoset.trim();
                lore.set(specified, loretoset);
                meta.setLore(lore);
                p.getInventory().getItemInHand().setItemMeta(meta);
            }
            else if (args[0].equals("listlore")){
                if (lore.isEmpty()){
                    p.sendMessage(ChatColor.RED + "[CI] The item must have lore for this this to work!");
                    return true;
                }
                int linenumber = 0;
                for (String lore2 : lore){
                    linenumber++;
                    TextComponent msg = new TextComponent(ChatColor.AQUA + "[CI] " + ChatColor.RESET + lore2.replace('&', '§'));
                    msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/relore set " + linenumber + " " + lore2.replace('§', '&')));
                    msg.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/relore set " + linenumber + " " +  lore2.replace('§', '&')).create()));
                    p.spigot().sendMessage(msg);
                }
            }
            else if (args[0].equals("movedown")){
                if (lore.isEmpty()){
                    p.sendMessage(ChatColor.RED + "[CI] The item must have lore for this this to work!");
                    return true;
                }
                if (args.length<2){
                    p.sendMessage(ChatColor.RED + "[CI] You must specify the line you want to move down!");
                    return true;
                }
                int size;
                try {
                    size = Integer.parseInt(args[1]);
                } catch (NumberFormatException e){
                    p.sendMessage(ChatColor.RED + "[CI] Specify what line you would like to edit.");
                    return true;
                }
                if (lore.size()<size){
                    p.sendMessage(ChatColor.RED + "[CI] It cannot be out of bounds of the lore");
                    return true;
                }
                lore.add(size,"");
                meta.setLore(lore);
                p.getInventory().getItemInHand().setItemMeta(meta);
                return true;
            }
            else {
                p.sendMessage(ChatColor.RED + "Not a valid type of lore customization");
            }// If they input something other then add,all,remove, or blank
        }
        return true;
    }
}
