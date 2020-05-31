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
            case "colorcodes":
                p.sendMessage((char) 167 + "f&0 = " + (char) 167 + "0Black                  " + (char) 167 + "f&8 = " + (char) 167 + "8Dark Gray");
                p.sendMessage((char) 167 + "f&1 = " + (char) 167 + "1Dark Blue           " + (char) 167 + "f&9 = " + (char) 167 + "9Blue");
                p.sendMessage((char) 167 + "f&2 = " + (char) 167 + "2Dark Green        " + (char) 167 + "f&a = " + (char) 167 + "aGreen");
                p.sendMessage((char) 167 + "f&3 = " + (char) 167 + "3Dark Aqua          " + (char) 167 + "f&b = " + (char) 167 + "bAqua");
                p.sendMessage((char) 167 + "f&4 = " + (char) 167 + "4Dark Red            " + (char) 167 + "f&c = " + (char) 167 + "cRed");
                p.sendMessage((char) 167 + "f&5 = " + (char) 167 + "5Dark Purple       " + (char) 167 + "f&d = " + (char) 167 + "dLight Purple");
                p.sendMessage((char) 167 + "f&6 = " + (char) 167 + "6Gold                    " + (char) 167 + "f&e = " + (char) 167 + "eYellow");
                p.sendMessage((char) 167 + "f&7 = " + (char) 167 + "7Gray                   " + (char) 167 + "f&f = " + (char) 167 + "fWhite");
                p.sendMessage((char) 167 + "f&k = " + (char) 167 + "kMagic" + (char) 167  + "r                  " + (char) 167 + "f&n = " + (char) 167 + "nUnderline");
                p.sendMessage((char) 167 + "f&l = " + (char) 167 + "lBold                 " + (char) 167 + "f&o = " + (char) 167 + "oItalic");
                p.sendMessage((char) 167 + "f&m = " + (char) 167 + "mStrikeThrough" + (char) 167  + "r   " + (char) 167 + "f&r = " + (char) 167 + "rReset");
                break;
            default:
                p.sendMessage(ChatColor.RED + "[CI] " + command.getName() + " is not a command.");
        }
        return true;
    }
}
