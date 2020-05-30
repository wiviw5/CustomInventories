package me.wiviw.custominventories;

import me.wiviw.custominventories.commands.*;
import me.wiviw.custominventories.events.BlockTicks;
import me.wiviw.custominventories.events.EnviromentEffects;
import me.wiviw.custominventories.events.MobInteractions;
import me.wiviw.custominventories.events.damagetracker;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import static me.wiviw.custominventories.Glow.registerEnchant;

public final class CustomInventories extends JavaPlugin {

    @Override
    public void onEnable() {
        registerEvents();
        registerCommands();
        registerEnchant();
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
        getCommand("gma").setExecutor(new gamemode());
        getCommand("returntext").setExecutor(new textreturn());
        getCommand("returntextall").setExecutor(new textreturn());
        getCommand("rename").setExecutor(new customizeItems());
        getCommand("relore").setExecutor(new customizeItems());
        getCommand("toggleunbreakable").setExecutor(new customizeItems());
        getCommand("togglehideunbreakable").setExecutor(new customizeItems());
        getCommand("togglehideenchants").setExecutor(new customizeItems());
        getCommand("togglehideattributes").setExecutor(new customizeItems());
        getCommand("togglehideplacedon").setExecutor(new customizeItems());
        getCommand("togglehidedestroys").setExecutor(new customizeItems());
        getCommand("togglehidepotions").setExecutor(new customizeItems());
        getCommand("colorleather").setExecutor(new customizeItems());
        getCommand("glowing").setExecutor(new customizeItems());
        //Todo Update all commands below
        getCommand("sethealth").setExecutor(new extras());
        getCommand("setname").setExecutor(new extras());
        getCommand("nick").setExecutor(new extras());
        getCommand("inventory").setExecutor(new extras());
    }

    public static void PluginFolders(CustomInventories p) {
        File configfile = new File("plugins//CustomInventories//config.yml");
        String pluginFolder = p.getDataFolder().getAbsolutePath(); //Getting the absolutepath of the folder
        (new File(pluginFolder)).mkdirs(); // making the plugin folder for config
        (new File(pluginFolder + "/inventories")).mkdirs(); //Plugin folder that stores all inventories
        (new File(pluginFolder + "/setups")).mkdirs(); //Plugin folder that stores all inventories
        try {
            (new File(pluginFolder + "/config.yml")).createNewFile();
        } catch (IOException e) {
            System.out.println("Could not create plugin file");
        } // Creates config file
        p.saveDefaultConfig();
        try {
            p.getConfig().load(configfile);
        } catch (IOException | InvalidConfigurationException e) {
            System.out.println("Could not load plugin config file");
        } // loads the config file
        FileConfiguration config = p.getConfig();
        config.addDefault("EnviromentEffects", false); //Default False, Turns on/off environment variables.
        config.addDefault("BlockUpdates", false); //Default False, Turns off certain block updates
        config.addDefault("DamageIndicators", false); //Default False, Turns on Damage indicators for useful debugging
        config.addDefault("MobAI", false); //Default False, Turns on mob AI/spawning
        config.options().copyDefaults(true);
        p.saveConfig();
    }
}
