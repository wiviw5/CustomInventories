package me.wiviw.custominventories.events;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class MobInteractions implements Listener {
	
	@EventHandler
	public void MobSpawning(CreatureSpawnEvent e) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("MobAI")) {
			if (e.getSpawnReason()==SpawnReason.NATURAL || e.getSpawnReason()==SpawnReason.MOUNT || e.getSpawnReason()==SpawnReason.JOCKEY) {
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
    public void onSilverFishBurrow(EntityChangeBlockEvent e){
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("MobAI")) {
			if(e.getEntity().getType().equals(EntityType.SILVERFISH)){
	            e.setCancelled(true);
	        }
		}
    }
	@EventHandler
	public void entityDamage(EntityDamageEvent e) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("MobAI")) {
			if (e.getEntityType()==EntityType.ARMOR_STAND || e.getEntityType()==EntityType.RABBIT || e.getEntityType()==EntityType.VILLAGER) {
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void entityDamage(EntityDamageByEntityEvent e) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("MobAI")) {
			if (e.getEntityType()==EntityType.ARMOR_STAND || e.getEntityType()==EntityType.RABBIT || e.getEntityType()==EntityType.VILLAGER) {
				e.setCancelled(true);
			}
		}
	}

}
