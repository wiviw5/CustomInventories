package me.wiviw.custominventories.commands;

import com.nametagedit.plugin.NametagCommand;
import com.nametagedit.plugin.NametagEdit;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class extras implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "[CI] Console Cannot run this command.");
            return true;
        }
        if (!(sender.isOp())) {
            sender.sendMessage(ChatColor.RED + "[CI] You are lacking permissions to run this command.");
            return true;
        }
        Player p = (Player) sender;
        ItemMeta meta = p.getInventory().getItemInHand().getItemMeta();
        switch (command.getName()) {
            case "sethealth":
                if (args.length < 2) {
                    p.sendMessage(ChatColor.RED + "[CI] Specify [Health, MaxHealth, Hunger, Absorp] and a value.");
                    return false;
                }
                double Value;
                try {
                    Value = Double.parseDouble(args[1]);
                } catch (Exception e) {
                    p.sendMessage(ChatColor.RED + "[CI] Specify your Value of hunger");
                    return false;
                }
                EntityLiving cp = ((CraftPlayer) p.getPlayer()).getHandle();
                switch (args[0]) {
                    case "Health":
                        if (p.getMaxHealth() < Value) {
                            p.setHealth(p.getMaxHealth());
                            break;
                        }
                        p.setHealth(Value);
                        break;
                    case "Hunger":
                        p.setFoodLevel((int) Value);
                        break;
                    case "Absorp":
                        cp.setAbsorptionHearts((float) Value);
                        break;
                    case "MaxHealth":
                        p.setMaxHealth(Value);
                        break;
                    default:
                        p.sendMessage(ChatColor.RED + "[CI] Specify [Health, MaxHealth, Hunger, Absorp] and a value.");
                        return true;
                }
                break;
            case "inventory":
                if (!(args.length > 1)) {
                    p.sendMessage(ChatColor.RED + "[CI] Specify Size, and Name");
                    return false;
                }
                List<String> PostArgs = Arrays.asList(args);
                int size;
                try {
                    size = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    p.sendMessage(ChatColor.RED + "[CI] You must specify Size first, you said: " + args[0]);
                    return false;
                }
                if (size != 0)
                    if (size % 9 != 0) {
                        p.sendMessage(ChatColor.RED + "[CI] It must be a multiple of 9");
                        return false;
                    }
                PostArgs.set(0, "");
                StringBuilder SBinv = new StringBuilder();
                for (String s : PostArgs) {
                    SBinv.append(s);
                    SBinv.append(" ");
                }
                String InvName = ChatColor.translateAlternateColorCodes('&', String.valueOf(SBinv)).trim();
                Inventory inv = Bukkit.createInventory(p, size, ChatColor.translateAlternateColorCodes('&', InvName));
                p.openInventory(inv);
                break;
            case "title":
                if (!(args.length > 1)) {
                    p.sendMessage(ChatColor.RED + "[CI] Specify Title then type nl\\ and subtitle.");
                    return false;
                }
                StringBuilder SBTitle = new StringBuilder();
                for (String s : args) {
                    SBTitle.append(s);
                    SBTitle.append(" ");
                }
                String FullList = SBTitle.toString();
                if (!(FullList.contains("nl\\"))) {
                    p.sendMessage(ChatColor.RED + "[CI] Include nl\\ in your message to separate the titles.");
                    return false;
                }


                break;
            default:
                p.sendMessage(ChatColor.RED + "[CI] " + command.getName() + " is not a command.");
        }
        return true;
    }
}
