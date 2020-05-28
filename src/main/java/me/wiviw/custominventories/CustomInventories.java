package me.wiviw.custominventories;

import me.wiviw.custominventories.commands.*;
import me.wiviw.custominventories.events.BlockTicks;
import me.wiviw.custominventories.events.EnviromentEffects;
import me.wiviw.custominventories.events.MobInteractions;
import me.wiviw.custominventories.events.damagetracker;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class CustomInventories extends JavaPlugin {

    @Override
    public void onEnable() {
        registerEvents();
        registerCommands();
        PluginFolders(this);
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new EnviromentEffects(), this);
        getServer().getPluginManager().registerEvents(new damagetracker(), this);
        getServer().getPluginManager().registerEvents(new BlockTicks(), this);
        getServer().getPluginManager().registerEvents(new MobInteractions(), this);

    }

    public void registerCommands() {
        getCommand("gm").setExecutor(new gamemode());
        getCommand("gms").setExecutor(new gamemode());
        getCommand("gmc").setExecutor(new gamemode());
        getCommand("gmss").setExecutor(new gamemode());
        getCommand("returntext").setExecutor(new textreturn());
        getCommand("returntextall").setExecutor(new textreturn());
        getCommand("rename").setExecutor(new itemdependants());
        getCommand("relore").setExecutor(new itemdependants());
        getCommand("exunbreaking").setExecutor(new extras());
        getCommand("exhideunbreakable").setExecutor(new extras());
        getCommand("exhideenchants").setExecutor(new extras());
        getCommand("exhideattributes").setExecutor(new extras());
        getCommand("exhideplacedon").setExecutor(new extras());
        getCommand("exhidedestroys").setExecutor(new extras());
        getCommand("exhidepotions").setExecutor(new extras());
        getCommand("particletrails").setExecutor(new trails());
        getCommand("entitysetup").setExecutor(new entitysetup());
        getCommand("entitysaving").setExecutor(new entitysetup());
        getCommand("entitytesting").setExecutor(new entitysetup());
        getCommand("colorleather").setExecutor(new extras());
        getCommand("sethealth").setExecutor(new extras());
        getCommand("setname").setExecutor(new extras());
        getCommand("nick").setExecutor(new extras());
        getCommand("inventory").setExecutor(new extras());
    }

    public static void PluginFolders(CustomInventories p) {
        File configfile = new File("plugins//CustomInventories//config.yml");
        String pluginFolder = p.getDataFolder().getAbsolutePath(); //Getting the absolutepath of the folder
        (new File(pluginFolder)).mkdirs(); // making the plugin folder for config
        (new File(pluginFolder+"/inventories")).mkdirs(); //Plugin folder that stores all inventories
        (new File(pluginFolder+"/setups")).mkdirs(); //Plugin folder that stores all inventories
        try { (new File(pluginFolder+"/config.yml")).createNewFile(); } catch (IOException e) { System.out.println("Could not create plugin file"); } // Creates config file
        p.saveDefaultConfig();
        try { p.getConfig().load(configfile); } catch (IOException | InvalidConfigurationException e) { System.out.println("Could not load plugin config file"); } // loads the config file
        FileConfiguration config = p.getConfig();
        config.addDefault("EnviromentEffects", false); //Default False, Turns on/off environment variables.
        config.addDefault("BlockUpdates", false); //Default False, Turns off certain block updates
        config.addDefault("DamageIndicators", false); //Default False, Turns on Damage indicators for useful debugging
        config.addDefault("MobAI", false); //Default False, Turns on mob AI/spawning
        config.options().copyDefaults(true);
        p.saveConfig();
    }
}
