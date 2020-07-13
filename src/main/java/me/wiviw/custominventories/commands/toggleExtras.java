package me.wiviw.custominventories.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class toggleExtras implements TabExecutor {
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
        if (args.length<=0){
            p.sendMessage(ChatColor.RED + "[CI] Specify your action: unbreakable, hideenchants, hideattributes, hideunbreakable, hideplacedon, hidedestroys, hidepotions.");
            return false;
        }
        String choice = args[0];
        switch(choice) {
            case "unbreakable":
                meta.spigot().setUnbreakable(!item.getItemMeta().spigot().isUnbreakable());
                item.setItemMeta(meta);
                break;
            case "hideenchants":
                if (meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
                    meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
                } else {
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
                item.setItemMeta(meta);
                break;
            case "hideattributes":
                if (meta.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES)) {
                    meta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                } else {
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                }
                item.setItemMeta(meta);
                break;
            case "hideunbreakable":
                if (meta.hasItemFlag(ItemFlag.HIDE_UNBREAKABLE)) {
                    meta.removeItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                } else {
                    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                }
                item.setItemMeta(meta);
                break;
            case "hideplacedon":
                if (meta.hasItemFlag(ItemFlag.HIDE_PLACED_ON)) {
                    meta.removeItemFlags(ItemFlag.HIDE_PLACED_ON);
                } else {
                    meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                }
                item.setItemMeta(meta);
                break;
            case "hidedestroys":
                if (meta.hasItemFlag(ItemFlag.HIDE_DESTROYS)) {
                    meta.removeItemFlags(ItemFlag.HIDE_DESTROYS);
                } else {
                    meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
                }
                item.setItemMeta(meta);
                break;
            case "hidepotions":
                if (meta.hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS)) {
                    meta.removeItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                } else {
                    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                }
                item.setItemMeta(meta);
                break;
        }
        p.getInventory().setItemInHand(item);
        p.updateInventory();
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
