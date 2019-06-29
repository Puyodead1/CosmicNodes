package me.puyodead1.cosmicnodes.events;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.puyodead1.cosmicnodes.ActiveResourceNode;
import me.puyodead1.cosmicnodes.utils.Node;
import me.puyodead1.cosmicnodes.utils.PSPlayer;
import me.puyodead1.cosmicnodes.utils.Utils;

public class PlayerJoin implements Listener {

	@EventHandler
	public void playerJoin(PlayerJoinEvent e) {
		final long started = System.currentTimeMillis();
		int STARTED = 0, LOADED = 0;
		
		final Player p = e.getPlayer();
		new PSPlayer(p.getUniqueId());
		PSPlayer pdata = PSPlayer.getPlayer(p.getUniqueId());
		
		ConfigurationSection section = pdata.config.getConfigurationSection("nodes");
		if (section != null && section.getKeys(false).size() != 0) {
			for (String s : section.getKeys(false)) {
				String path = s + ".";
				Location location = new Location(Bukkit.getWorld(section.getString(path + "location.world")),
						section.getDouble(path + "location.x"), section.getDouble(path + "location.y"),
						section.getDouble(path + "location.z"));

				if (location.getWorld().getBlockAt(location) != null) {
					new ActiveResourceNode(p, Node.valueOf(section.getString(path + "type")), location);
					STARTED++;
				}
				LOADED++;
			}
			
			Utils.sendConsole("&dLoaded " + LOADED + " player nodes, started " + STARTED + " valid player nodes and loaded data for player", started, true);
		}
	}
}
