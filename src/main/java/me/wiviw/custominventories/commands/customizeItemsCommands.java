package me.wiviw.custominventories.commands;

import me.wiviw.custominventories.utilities.Glow;
import net.md_5.bungee.api.chat.*;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class customizeItemsCommands implements CommandExecutor { //Todo Add TabExectutor and tab autocomplete for these commands.
    public static String colorize(String str) {
        return str.replace('&', '§');
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "[CI] Console Cannot run this command.");
            return false;
        }
        if (!(sender.isOp())) {
            sender.sendMessage(ChatColor.RED + "[CI] You are lacking permissions to run this command.");
            return false;
        } //Todo Update Op Check format
        Player p = (Player) sender;
        ItemStack item = p.getInventory().getItemInHand();
        if (item.getType() == Material.AIR) {
            p.sendMessage(ChatColor.RED + "[CI] You cannot be holding nothing.");
            return false;
        } //Null check
        ItemMeta meta = item.getItemMeta();
        switch (command.getName().toLowerCase()) {
            case "rename":
                String name = "";
                for (String arg : args) {
                    name += arg + (" ");
                }
                name = name.trim();
                if (args.length <= 0) {
                    meta.setDisplayName(" ");
                    item.setItemMeta(meta);
                    break;
                }
                if (args[0].equals("list")) {
                    if (meta.hasDisplayName()) {
                        String nameitem = meta.getDisplayName();
                        nameitem = nameitem.replace('§', '&'); //Todo Replace sections of code relying on "§" with char definition
                        TextComponent msg = new TextComponent(ChatColor.AQUA + "[CI] " + ChatColor.RESET + nameitem.replace('&', '§'));
                        msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/rename " + nameitem));
                        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/rename " + nameitem).create()));
                        p.spigot().sendMessage(msg);
                        break;
                    } else {
                        p.sendMessage(ChatColor.RED + "[CI] Item must has a name other then default");
                        return false;
                    }
                }
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
                item.setItemMeta(meta);
                break;
            case "relore":
                if (args.length <= 0) {
                    p.sendMessage(ChatColor.RED + "[CI] You must specify lore to be added, For Blank lines: \"blank\"");
                    return false;
                } // Check for if they did not include any args
                List<String> lore;
                if (meta.hasLore()) {
                    lore = meta.getLore();
                } else {
                    lore = new ArrayList<>(1);
                }// After checks for lore ^
                switch (args[0]) {
                    case "remove": {
                        if (args.length <= 1) {
                            p.sendMessage(ChatColor.RED + "[CI] You must specify the line or all lines to be removed");
                            return false;
                        }
                        if (args[1].equals("all")) {
                            meta.setLore(null);
                            item.setItemMeta(meta);
                            break;
                        }// Removing All lines of lore on an item
                        if (lore.isEmpty()) {
                            p.sendMessage(ChatColor.RED + "[CI] You cannot use remove if the item doesn't have lore!");
                            return false;
                        } //Catches for stuff without lore
                        int specified;
                        try {
                            specified = Integer.parseInt(args[1]);
                        } catch (NumberFormatException NFE) {
                            p.sendMessage(ChatColor.RED + "[CI] You can't put a letter there...");
                            return false;
                        }
                        specified--;
                        int loresize = lore.size();
                        loresize--;
                        if (specified > loresize) {
                            p.sendMessage(ChatColor.RED + "[CI] You can't remove an items lore line if it doesn't exist!");
                            return false;
                        } //Catches for stuff out of bounds
                        if (specified < 0) {
                            p.sendMessage(ChatColor.RED + "[CI] You can't remove a negative lore line?");
                            return false;
                        }
                        lore.remove(specified);
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        break;
                    }
                    case "add":
                        String loretoadd = "";
                        for (int i = 1; i < args.length; i++) {
                            loretoadd = loretoadd + (colorize(args[i]) + (" "));
                        }
                        loretoadd = loretoadd.trim();
                        lore.add(loretoadd);
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        break;
                    case "set": {
                        if (lore.isEmpty()) {
                            p.sendMessage(ChatColor.RED + "[CI] You cannot use set if the item doesn't have lore!");
                            return false;
                        } //Catches for stuff without lore
                        if (args.length <= 1) {
                            p.sendMessage(ChatColor.RED + "[CI] You must specify a line to set!");
                            return false;
                        }
                        int specified;
                        try {
                            specified = Integer.parseInt(args[1]);
                        } catch (NumberFormatException NFE) {
                            p.sendMessage(ChatColor.RED + "[CI] You can't put a letter there...");
                            return false;
                        }
                        specified--;
                        int loresize = lore.size();
                        loresize--;
                        if (specified > loresize) {
                            p.sendMessage(ChatColor.RED + "[CI] You can't set an items lore line if it doesn't exist!");
                            return false;
                        } //Catches for stuff out of bounds
                        if (specified < 0) {
                            p.sendMessage(ChatColor.RED + "[CI] You can't set a negative lore line?");
                            return false;
                        }
                        if (args.length <= 2) {
                            lore.set(specified, "");
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                            break;
                        }// If what you want to set it to is a blank line
                        String loretoset = "";
                        for (int i = 2; i < args.length; i++) {
                            loretoset = loretoset + (colorize(args[i]) + (" "));
                        }
                        loretoset = loretoset.trim();
                        lore.set(specified, loretoset);
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        break;
                    }
                    case "list":
                        if (lore.isEmpty()) {
                            p.sendMessage(ChatColor.RED + "[CI] The item must have lore for this this to work!");
                            return false;
                        }
                        int linenumber = 0;
                        for (String lore2 : lore) {
                            linenumber++;
                            TextComponent msg = new TextComponent(ChatColor.AQUA + "[CI] " + ChatColor.RESET + lore2.replace('&', '§'));
                            msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/relore set " + linenumber + " " + lore2.replace('§', '&')));
                            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/relore set " + linenumber + " " + lore2.replace('§', '&')).create()));
                            p.spigot().sendMessage(msg);
                        }
                        break;
                    case "insert":
                        if (lore.isEmpty()) {
                            p.sendMessage(ChatColor.RED + "[CI] The item must have lore for this this to work!");
                            return false;
                        }
                        if (args.length < 2) {
                            p.sendMessage(ChatColor.RED + "[CI] You must specify the line you want to insert after!");
                            return false;
                        }
                        int size;
                        try {
                            size = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            p.sendMessage(ChatColor.RED + "[CI] Specify what line you would like to insert after.");
                            return false;
                        }
                        if (lore.size() < size) {
                            p.sendMessage(ChatColor.RED + "[CI] The line must be within the lore.");
                            return false;
                        }
                        lore.add(size, "");
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        break;
                    default:
                        p.sendMessage(ChatColor.RED + "[CI] Not a valid type of lore customization, Options: add, set, remove, insert, list");
                        break;
                }
                break;
            case "toggleunbreakable":
                meta.spigot().setUnbreakable(!item.getItemMeta().spigot().isUnbreakable());
                item.setItemMeta(meta);
                break;
            case "togglehideenchants":
                if (meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
                    meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
                } else {
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
                item.setItemMeta(meta);
                break;
            case "togglehideattributes":
                if (meta.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES)) {
                    meta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                } else {
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                }
                item.setItemMeta(meta);
                break;
            case "togglehideunbreakable":
                if (meta.hasItemFlag(ItemFlag.HIDE_UNBREAKABLE)) {
                    meta.removeItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                } else {
                    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                }
                item.setItemMeta(meta);
                break;
            case "togglehideplacedon":
                if (meta.hasItemFlag(ItemFlag.HIDE_PLACED_ON)) {
                    meta.removeItemFlags(ItemFlag.HIDE_PLACED_ON);
                } else {
                    meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                }
                item.setItemMeta(meta);
                break;
            case "togglehidedestroys":
                if (meta.hasItemFlag(ItemFlag.HIDE_DESTROYS)) {
                    meta.removeItemFlags(ItemFlag.HIDE_DESTROYS);
                } else {
                    meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
                }
                item.setItemMeta(meta);
                break;
            case "togglehidepotions":
                if (meta.hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS)) {
                    meta.removeItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                } else {
                    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                }
                item.setItemMeta(meta);
                break;
            case "colorleather":
                if (args.length<1){
                    p.sendMessage(ChatColor.RED + "[CI] Specify a hex color.");
                    return false;
                }
                switch (item.getType()) {
                    case LEATHER_BOOTS:
                    case LEATHER_CHESTPLATE:
                    case LEATHER_HELMET:
                    case LEATHER_LEGGINGS:
                        ItemStack Leatheritem = new ItemStack(item);
                        LeatherArmorMeta leathermeta = (LeatherArmorMeta) Leatheritem.getItemMeta();
                        Leatheritem.setItemMeta(meta);
                        if (args[0].startsWith("#")){
                            Leatheritem.setItemMeta(hex2Rgb(leathermeta,args[0]));
                            break;
                        }else{
                            if (args.length<4){
                                int RED;
                                int GREEN;
                                int BLUE;
                                try {
                                    RED = Integer.parseInt(args[0]);
                                    GREEN = Integer.parseInt(args[1]);
                                    BLUE = Integer.parseInt(args[2]);
                                } catch (NumberFormatException e){
                                    p.sendMessage(ChatColor.RED + "[CI] You list numbers as a RGB value.");
                                    return false;
                                }
                                Color color2 = (Color.fromRGB(RED,GREEN,BLUE));
                                leathermeta.setColor(color2);
                                Leatheritem.setItemMeta(leathermeta);
                            }else{
                                p.sendMessage(ChatColor.RED + "[CI] You specify either a hex or a set of RGB values.");
                                return false;
                            }
                        }
                        break;
                    default:
                        p.sendMessage(ChatColor.RED + "[CI] You must be holding leather.");
                        break;
                }
                break;
            case "glowing":
                Glow glow = new Glow();
                if (item.containsEnchantment(glow)){
                    item.removeEnchantment(glow);
                }else{
                    item.addUnsafeEnchantment(glow, 1);
                }
                break;
        }
        p.getInventory().setItemInHand(item);
        p.updateInventory();
        return true;
    }
    public static ItemMeta hex2Rgb(ItemMeta hex2rgbmeta, String colorStr) {
        int red = Integer.valueOf(colorStr.substring( 1, 3 ), 16 );
        int green = Integer.valueOf(colorStr.substring( 3, 5 ), 16 );
        int blue = Integer.valueOf(colorStr.substring( 5, 7 ), 16 );
        LeatherArmorMeta meta1 = (LeatherArmorMeta) hex2rgbmeta;
        Color color2 = (Color.fromRGB(red,green,blue));
        meta1.setColor(color2);
        return meta1;
    }
}
