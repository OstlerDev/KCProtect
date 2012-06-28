package com.me.ostlerdev.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.me.olsterdev.utilities.CreateUtilities;
import com.me.ostlerdev.KCProtect;


/**
 * @author OstlerDev
 */
public class BlockPlaceListener implements Listener {

	private KCProtect plugin;

	public BlockPlaceListener(KCProtect plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player player = e.getPlayer();

		if (e.getBlock().getTypeId() == 19){
			new CreateUtilities(player, e.getBlock().getLocation()).start();
			plugin.getLogger().info("[KCProtect] Creating new Area at " + e.getBlock().getLocation().toString());
		}
	}
}