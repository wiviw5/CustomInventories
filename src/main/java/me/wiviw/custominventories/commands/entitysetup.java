package me.wiviw.custominventories.commands;

import java.io.*;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class entitysetup implements CommandExecutor {

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
        if (command.getName().equals("entitysaving")){
            if(args.length <= 6){
                p.sendMessage(ChatColor.RED + "1: Name of file (No spaces) | 2: Choice of Entity | 3: Name of Entity (With color codes) | 4: Double x | 5: Double y | 6: Double z | 7: Facing Direction (0 is south)");
                return true;
            }
            String type = args[1];
            switch (type.toLowerCase()){
                case "armorstand":
                    p.sendMessage("Armorstand");
                    break;
                case "villager":
                    p.sendMessage("Villager");
                    break;
                case "rabbit":
                    p.sendMessage("Rabbit");
                    break;
                case "silverfish":
                    p.sendMessage("Silverfish");
                    break;
                default:
                    p.sendMessage(ChatColor.RED + "Invalid Entity please use one from this List: ARMORSTAND,VILLAGER,RABBIT,SILVERFISH");
                    return true;
            }
            String name = args[2];
            double XLOC = Double.parseDouble(args[3]);
            double YLOC = Double.parseDouble(args[4]);
            double ZLOC = Double.parseDouble(args[5]);
            double direction = Double.parseDouble(args[6]);

            File filecheck = new File("plugins//CustomInventories//setups//"+args[0]+".json");
            if (filecheck.exists()){
                JSONParser jsonParser = new JSONParser();
                try (FileReader reader = new FileReader("plugins//CustomInventories//setups//"+args[0]+".json"))
                {
                    Object obj = jsonParser.parse(reader);
                    JSONArray storagelistread = (JSONArray) obj;
                    JSONObject EntityInfo = new JSONObject();
                    EntityInfo.put("Type", type);
                    EntityInfo.put("Name", name);
                    EntityInfo.put("Direction", direction);
                    JSONArray XYZ = new JSONArray();
                    XYZ.add(XLOC);
                    XYZ.add(YLOC);
                    XYZ.add(ZLOC);
                    EntityInfo.put("Location", XYZ);
                    storagelistread.add(EntityInfo);
                    try (FileWriter file = new FileWriter("plugins//CustomInventories//setups//"+args[0]+".json")) {
                        file.write(storagelistread.toJSONString());
                        file.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (ParseException | IOException e) {
                    e.printStackTrace();
                }
            }else{
                try (FileWriter file = new FileWriter("plugins//CustomInventories//setups//"+args[0]+".json")) {
                    JSONObject EntityInfo = new JSONObject();
                    EntityInfo.put("Type", type);
                    EntityInfo.put("Name", name);
                    EntityInfo.put("Direction", direction);

                    JSONArray XYZ = new JSONArray();
                    XYZ.add(XLOC);
                    XYZ.add(YLOC);
                    XYZ.add(ZLOC);
                    EntityInfo.put("Location", XYZ);

                    JSONArray storagelist = new JSONArray();
                    storagelist.add(EntityInfo);
                    file.write(storagelist.toJSONString());
                    file.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (command.getName().equalsIgnoreCase("entitysetup")) {
            File filecheck = new File("plugins//CustomInventories//setups//"+args[0]+".json");
            if (filecheck.exists()){
                JSONParser jsonParser = new JSONParser();
                try (FileReader reader = new FileReader("plugins//CustomInventories//setups//"+args[0]+".json")) {
                    Object obj = jsonParser.parse(reader);
                    JSONArray storagelistread = (JSONArray) obj;
                    int itteration = 0;
                    while (storagelistread.size()>itteration){
                        JSONObject EntityInfo = (JSONObject) storagelistread.get(itteration);
                        itteration++;
                        String Type = (String) EntityInfo.get("Type");
                        String Name = (String) EntityInfo.get("Name");
                        double direction = (double) EntityInfo.get("Direction");

                        JSONArray Location = (JSONArray) EntityInfo.get("Location");
                        double x = (double) Location.get(0);
                        double y = (double) Location.get(1);
                        double z = (double) Location.get(2);
                        World world = p.getWorld();
                        Location spawnlocation = new Location(world, x, y, z, (float) direction, 0f);
                        ArmorStand AS = (ArmorStand) p.getWorld().spawnEntity(spawnlocation, EntityType.ARMOR_STAND);
                        AS.setCustomName(ChatColor.translateAlternateColorCodes('&',Name));
                        AS.setVisible(false);
                        AS.setCustomNameVisible(true);
                        AS.setGravity(false);
                        AS.setBasePlate(false);
                    }


                } catch (ParseException | IOException e) {
                    e.printStackTrace();
                }
            }else{
                p.sendMessage(ChatColor.RED + "[CI] This File Does not Exist");
            }
            return true;
        }
        if (command.getName().equalsIgnoreCase("entitytesting")){
            p.sendMessage("ent testing");
            return true;
        }
		return true;
	}
}
