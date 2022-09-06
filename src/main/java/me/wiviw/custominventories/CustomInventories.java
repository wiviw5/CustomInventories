package me.wiviw.custominventories;

import me.wiviw.custominventories.commands.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

import static me.wiviw.custominventories.utilities.Glow.registerEnchant;

public final class CustomInventories extends JavaPlugin {

    @Override
    public void onEnable() {
        registerEvents();
        registerCommands();
        registerEnchant();
        PluginFolders(this);
    }

    public void registerEvents() {

    }

    public void registerCommands() {
        getCommand("gm").setExecutor(new gamemode());
        getCommand("gms").setExecutor(new gamemode());
        getCommand("gmc").setExecutor(new gamemode());
        getCommand("gmss").setExecutor(new gamemode());
        getCommand("gma").setExecutor(new gamemode());
        getCommand("returntext").setExecutor(new textreturn());
        getCommand("returntextall").setExecutor(new textreturn());
        getCommand("rename").setExecutor(new customizeItemsCommands());
        getCommand("relore").setExecutor(new customizeItemsCommands());
        getCommand("toggleextras").setExecutor(new toggleExtras());
        getCommand("colorleather").setExecutor(new customizeItemsCommands());
        getCommand("glowing").setExecutor(new customizeItemsCommands());
        getCommand("head").setExecutor(new extras());
        //Todo Update all commands below
        getCommand("sethealth").setExecutor(new extras());
        getCommand("inventory").setExecutor(new extras());
        getCommand("colorcodes").setExecutor(new extras());
        getCommand("setlevel").setExecutor(new extras());
    }

    public static void PluginFolders(CustomInventories p) {
        String pluginFolder = p.getDataFolder().getAbsolutePath(); //Getting the absolutepath of the folder
        (new File(pluginFolder)).mkdirs(); // making the plugin folder for config
        (new File(pluginFolder + "/inventories")).mkdirs(); //Plugin folder that stores all inventories
    }
}
