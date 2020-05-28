package me.wiviw.custominventories.commands;

import com.comphenix.packetwrapper.WrapperPlayServerPlayerInfo;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.nametagedit.plugin.NametagEdit;
import me.wiviw.custominventories.CustomInventories;
import me.wiviw.custominventories.events.InventoryHelper;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class extras implements CommandExecutor {
    public static ItemMeta hex2Rgb(ItemMeta hex2rgbmeta, String colorStr) {
        int red = Integer.valueOf(colorStr.substring( 1, 3 ), 16 );
        int green = Integer.valueOf(colorStr.substring( 3, 5 ), 16 );
        int blue = Integer.valueOf(colorStr.substring( 5, 7 ), 16 );
        LeatherArmorMeta meta1 = (LeatherArmorMeta) hex2rgbmeta;
        Color color2 = (Color.fromRGB(red,green,blue));
        meta1.setColor(color2);
        return meta1;
    }
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
        ItemMeta meta = p.getInventory().getItemInHand().getItemMeta();
        switch(command.getName()) {
        case "exunbreaking":
        	if (p.getInventory().getItemInHand().getItemMeta().spigot().isUnbreakable()){
                meta.spigot().setUnbreakable(false);
            }else{
                meta.spigot().setUnbreakable(true);
            }
            p.getInventory().getItemInHand().setItemMeta(meta);
            break;
        case "exhideenchants":
        	if (meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)){
                meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
            }else{
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            p.getInventory().getItemInHand().setItemMeta(meta);
            break;
        case "exhideattributes":
        	if (meta.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES)){
                meta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            }else{
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            }
            p.getInventory().getItemInHand().setItemMeta(meta);
            break;
        case "exhideunbreakable":
        	if (meta.hasItemFlag(ItemFlag.HIDE_UNBREAKABLE)){
                meta.removeItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            }else{
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            }
            p.getInventory().getItemInHand().setItemMeta(meta);
            break;
        case "exhideplacedon":
        	if (meta.hasItemFlag(ItemFlag.HIDE_PLACED_ON)){
                meta.removeItemFlags(ItemFlag.HIDE_PLACED_ON);
            }else{
                meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            }
            p.getInventory().getItemInHand().setItemMeta(meta);
            break;
        case "exhidedestroys":
        	if (meta.hasItemFlag(ItemFlag.HIDE_DESTROYS)){
                meta.removeItemFlags(ItemFlag.HIDE_DESTROYS);
            }else{
                meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            }
            p.getInventory().getItemInHand().setItemMeta(meta);
            break;
        case "exhidepotions":
        	if (meta.hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS)){
                meta.removeItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            }else{
                meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            }
            p.getInventory().getItemInHand().setItemMeta(meta);
            break;
        case "colorleather":
            if (args.length<1){
                p.sendMessage(ChatColor.RED + "[CI] Specify a hex color.");
                return false;
            }
            ItemStack LeatherStack = p.getInventory().getItemInHand();
            switch (LeatherStack.getType()) {
                case LEATHER_BOOTS:
                case LEATHER_CHESTPLATE:
                case LEATHER_HELMET:
                case LEATHER_LEGGINGS:
                    ItemStack item = new ItemStack(LeatherStack);
                    LeatherArmorMeta leathermeta = (LeatherArmorMeta) item.getItemMeta();
                    item.setItemMeta(meta);
                    if (args[0].startsWith("#")){
                        p.getInventory().getItemInHand().setItemMeta(hex2Rgb(leathermeta,args[0]));
                        return true;
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
                                return true;
                            }
                            Color color2 = (Color.fromRGB(RED,GREEN,BLUE));
                            leathermeta.setColor(color2);
                            p.getInventory().getItemInHand().setItemMeta(leathermeta);
                        }else{
                            p.sendMessage(ChatColor.RED + "[CI] You specify either a hex or a set of RGB values.");
                            return true;
                        }
                    }
                    break;
                default:
                    p.sendMessage(ChatColor.RED + "[CI] You must be holding leather.");
                    break;
            }
            break;
        case "sethealth":
            if (args.length<2){
                p.sendMessage(ChatColor.RED + "[CI] Specify [Health, MaxHealth, Hunger, Absorp] and a value.");
                return false;
            }
            double Value;
            try {
                Value = Double.parseDouble(args[1]);
            } catch (Exception e){
                p.sendMessage(ChatColor.RED + "[CI] Specify your Value of hunger");
                return false;
            }
            EntityLiving cp = ((CraftPlayer) p.getPlayer()).getHandle();
            switch (args[0]){
                case "Health":
                    if (p.getMaxHealth()<Value){
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
        case "nick":
            if (!(args.length>0)) {
                p.sendMessage(ChatColor.RED + "[CI] Specify your nick.");
                return false;
            }
            StringBuilder sb2 = new StringBuilder();
            for (String s : args){
                sb2.append(s);
                sb2.append(" ");
            }
            String NickName = ChatColor.translateAlternateColorCodes('&',sb2.toString().trim());
            p.sendMessage("Set name to: " + NickName);
            p.setDisplayName(NickName);
            break;
        case "setname":
            StringBuilder sb = new StringBuilder();
            for (String s : args){
                sb.append(s);
                sb.append(" ");
            }
            String Name = ChatColor.translateAlternateColorCodes('&', String.valueOf(sb));
            if (Name.length()>16) {
                p.sendMessage(ChatColor.RED + "[CI] Your Nick cannot be over 16 Characters");
                return false;
            }
            NametagEdit.getApi().setPrefix(p,Name);
            p.sendMessage("Set name to: " + Name + p.getName());
            break;
            /*
            if (!(args.length>0)) {
                p.sendMessage(ChatColor.RED + "[CI] Specify your name.");
                return false;
            }
            StringBuilder sb = new StringBuilder();
            for (String s : args){
                sb.append(s);
                sb.append(" ");
            }
            String SetName = ChatColor.translateAlternateColorCodes('&',sb.toString().trim());
            p.sendMessage("Set name to: " + SetName);


            PlayerInfoData pid = new PlayerInfoData(WrappedGameProfile.fromPlayer(p), 1,
                    EnumWrappers.NativeGameMode.SURVIVAL,
                    WrappedChatComponent.fromText(SetName));
            WrapperPlayServerPlayerInfo wpspi = new WrapperPlayServerPlayerInfo();

            wpspi.setAction(EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
            wpspi.setData(Collections.singletonList(pid));
            for(Player player : Bukkit.getOnlinePlayers()) {
                if(player.equals(p)) {
                    continue;
                }
                player.hidePlayer(p);
                wpspi.sendPacket(player);
            }

            ProtocolLibrary.getProtocolManager().addPacketListener(
                    new PacketAdapter(CustomInventories.getPlugin(CustomInventories.class), PacketType.Play.Server.PLAYER_INFO) {

                        @Override
                        public void onPacketSending(PacketEvent event) {

                            if(event.getPacket().getPlayerInfoAction().read(0) != EnumWrappers.PlayerInfoAction.ADD_PLAYER) {
                                return;
                            }

                            PlayerInfoData pid = event.getPacket().getPlayerInfoDataLists().read(0).get(0);

                            if(!pid.getProfile().getName().toLowerCase().equals(p.getName().toLowerCase())) { // Here you can do something to ensure you're changing the name of the correct guy
                                return;
                            }

                            PlayerInfoData newPid = new PlayerInfoData(pid.getProfile().withName(SetName), pid.getPing(), pid.getGameMode(),
                                    WrappedChatComponent.fromText(p.getName()));
                            event.getPacket().getPlayerInfoDataLists().write(0, Collections.singletonList(newPid));
                        }
                    }
            );

            for(Player player : Bukkit.getOnlinePlayers()) {
                if(player.equals(p)) {
                    continue;
                }
                player.showPlayer(p);
            }
            break;

             */
        case "inventory":
            if (!(args.length>1)) {
                p.sendMessage(ChatColor.RED + "[CI] Specify Size, and Name");
                return false;
            }
            List<String> PostArgs = Arrays.asList(args);
            int size;
            try {
                size = Integer.parseInt(args[0]);
            } catch (NumberFormatException e){
                p.sendMessage(ChatColor.RED + "[CI] You must specify Size first, you said: " + args[0]);
                return false;
            }
            if (size!=0)
            if (size % 9 != 0 ){
                p.sendMessage(ChatColor.RED + "[CI] It must be a multiple of 9");
                return false;
            }
            PostArgs.set(0, "");
            StringBuilder SBinv = new StringBuilder();
            for (String s : PostArgs){
                SBinv.append(s);
                SBinv.append(" ");
            }
            String InvName = ChatColor.translateAlternateColorCodes('&', String.valueOf(SBinv)).trim();
            InventoryHelper Pit = new InventoryHelper(p, size, ChatColor.translateAlternateColorCodes('&',InvName));
            Pit.openInventory(p);
            break;
        case "title":
            if (!(args.length>1)) {
                p.sendMessage(ChatColor.RED + "[CI] Specify Title then type nl\\ and subtitle.");
                return false;
            }
            StringBuilder SBTitle = new StringBuilder();
            for (String s : args){
                SBTitle.append(s);
                SBTitle.append(" ");
            }
            String FullList = SBTitle.toString();
            if(!(FullList.contains("nl\\"))){
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
