package me.wiviw.custominventories.events;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class BlockTicks implements Listener {
	@EventHandler
	void onFireLight(BlockIgniteEvent i) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("BlockUpdates")) {
			if (i.getPlayer() == null) {
				i.setCancelled(true);
			}
		}
	}

	@EventHandler
	void onLiquidFlow(BlockFromToEvent e) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("BlockUpdates")) {
			int id = e.getBlock().getTypeId();
			if (id == 8 || id == 9) {
				e.setCancelled(true);
			} // Water Cancellation
			if (id == 10 || id == 11) {
				e.setCancelled(true);
			} // Lava Cancellation
		}
	}

	@EventHandler
	void onFireBurnBlock(BlockBurnEvent e) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("BlockUpdates")) {
			e.setCancelled(true);
		}
	} // Burning BLocks

	@EventHandler
	void onGrassGrowth(BlockSpreadEvent e) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("BlockUpdates")) {
			if (e.getBlock().getType() == Material.DIRT || e.getBlock().getType() == Material.GRASS) {
				e.setCancelled(true);
			}
		}
	} // Grass Growth

	@EventHandler
	void onIceMelt(BlockFadeEvent e) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("BlockUpdates")) {
			if (e.getBlock().getType() == Material.ICE) {
				e.setCancelled(true);
			}
		}
	} // Ice Melting

	@EventHandler
	void onFarmland(BlockFadeEvent e) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("BlockUpdates")) {
			if (e.getBlock().getType() == Material.SOIL) {
				e.setCancelled(true);
			}
		}
	} // Soil Un-tilling

	@EventHandler
	void onLeafFade(LeavesDecayEvent e) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("BlockUpdates")) {
			if (e.getBlock().getType() == Material.LEAVES || e.getBlock().getType() == Material.LEAVES_2) {
				e.setCancelled(true);
			}
		}
	} // Stops Leaf decay

	@EventHandler
	void onGrass(BlockFadeEvent e) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("BlockUpdates")) {
			if (e.getBlock().getType() == Material.DIRT || e.getBlock().getType() == Material.GRASS) {
				e.setCancelled(true);
			}
		}
	} // Stops Grass production/Grass destruction

	@EventHandler
	void onStomp(PlayerInteractEvent e) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("BlockUpdates")) {
			if (e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.SOIL) {
				e.setCancelled(true);
			}
		}
	} // Disables stomping tilled soil

	@EventHandler
	void OnBlockBreak(BlockBreakEvent e) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("BlockUpdates")) {
			if (!e.getPlayer().isOp()) {
				if (e.getBlock().getType() == Material.COBBLESTONE || e.getBlock().getType() == Material.OBSIDIAN) {
					e.getBlock().setType(Material.AIR);
				} else {
					e.setCancelled(true);
				} // Actual blocks check
			} // Op check
		}
	} // Destroying Blocks Disabler

	@EventHandler
	void OnBlockPlace(BlockPlaceEvent e) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("BlockUpdates")) {
			if (!e.getPlayer().isOp()) {
				if (!(e.getBlock().getType() == Material.COBBLESTONE || e.getBlock().getType() == Material.OBSIDIAN)) {
					e.setCancelled(true);
				} // Cobble obsidian check
			} // Op Check
		}
	} // Placing Blocks disabler

	@EventHandler
	public void onVinesGrow(BlockSpreadEvent e) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("BlockUpdates")) {
			if (e.getBlock().getType() == Material.AIR) {
				e.setCancelled(true);
			}
		}
	}// Vine Disabler
	@EventHandler
	public void onRain(WeatherChangeEvent e) {
		File file = new File("plugins//CustomInventories//config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (config.getBoolean("BlockUpdates")) {
			e.setCancelled(e.toWeatherState());
		}
	}
}
