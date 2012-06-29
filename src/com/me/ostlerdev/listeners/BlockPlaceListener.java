package com.me.ostlerdev.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.me.ostlerdev.KCProtect;
import com.me.ostlerdev.utilities.AreaUtil;
import com.me.ostlerdev.utilities.CreateUtilities;


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
			AreaUtil util = new AreaUtil(plugin);
			if (!util.CheckOverlap(player, new Location(player.getWorld(), e.getBlock().getX()-10, 64,  e.getBlock().getX()-10), new Location(player.getWorld(), e.getBlock().getX()+10, 64,  e.getBlock().getX()+10))){
				new CreateUtilities(player, e.getBlock().getLocation()).start();
				plugin.getLogger().info("Creating new Area at " + e.getBlock().getLocation().toString());
				player.sendMessage("Area succesfully created at " + e.getBlock().getX() +","+ e.getBlock().getZ());
			}
		}
	}
}