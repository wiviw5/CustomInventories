package me.wiviw.custominventories.events;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EnviromentEffects implements Listener {

	@EventHandler
	void OnDamage(EntityDamageEvent e) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("EnviromentEffects")) {
			Entity ee = e.getEntity();
			EntityDamageEvent.DamageCause type = e.getCause();
			if (ee instanceof Player) {
				if (type == EntityDamageEvent.DamageCause.FALL) {
					e.setCancelled(true);
				}
				if (type == EntityDamageEvent.DamageCause.FIRE_TICK) {
					e.getEntity().setFireTicks(0);
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	void OnHunger(FoodLevelChangeEvent e) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("EnviromentEffects")) {
			Entity ee = e.getEntity();
			if (ee instanceof Player) {
				e.setCancelled(true);
				e.setFoodLevel(20);
			}
		}
	}

	@EventHandler
	void OnVision(PlayerJoinEvent e) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("EnviromentEffects")) {
			Player p = e.getPlayer();
			p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
		}
	}
}
