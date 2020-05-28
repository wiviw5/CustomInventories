package me.wiviw.custominventories.events;

import com.connorlinfoot.bountifulapi.BountifulAPI;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class damagetracker implements Listener {

    @EventHandler
    public void Damage(EntityDamageByEntityEvent e){
    	File file = new File("plugins//CustomInventories//config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    	if (config.getBoolean("DamageIndicators")) {
    		if (!(e.getDamager() instanceof Player)){
    		    if(e.getDamager() instanceof Arrow){
    		        Arrow arrow = (Arrow) e.getDamager();
    		        if(arrow.getShooter() instanceof Player){
    		            Player p = (Player) arrow.getShooter();
                        double dmg = e.getDamage();
                        double finaldmg = e.getFinalDamage();
                        String wpn = String.valueOf(p.getInventory().getItemInHand().getType());
                        BountifulAPI.sendActionBar(p,ChatColor.YELLOW + " [Pre-Reductions: " + Math.round(dmg * 100d)/100d + "]" + ChatColor.GREEN + " [Post-Reductions: " + Math.round(finaldmg * 100d)/100d + "]" + ChatColor.AQUA + " [Weapon: " + wpn + "]", 120);
                    }
                }
    		    return;
    		}
            Player p = (Player) e.getDamager();
            double dmg = e.getDamage();
            double finaldmg = e.getFinalDamage();
            String wpn = String.valueOf(p.getInventory().getItemInHand().getType());
            if (wpn.equals("AIR")){
                wpn = "FIST";
            }
            BountifulAPI.sendActionBar(p,ChatColor.YELLOW + " [Pre-Reductions: " + Math.round(dmg * 100d)/100d + "]" + ChatColor.GREEN + " [Post-Reductions: " + Math.round(finaldmg * 100d)/100d + "]" + ChatColor.AQUA + " [Weapon: " + wpn + "]", 120);
    	}
    }
}
