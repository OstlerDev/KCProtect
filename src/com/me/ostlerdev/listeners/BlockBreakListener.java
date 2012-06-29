package com.me.ostlerdev.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


import com.me.ostlerdev.KCProtect;
import com.me.ostlerdev.utilities.AreaUtil;
import com.me.ostlerdev.utilities.RemoveUtilities;

/**
 * @author DarkGhoul45, OstlerDev
 */
public class BlockBreakListener implements Listener {

	private KCProtect plugin;

	public BlockBreakListener(KCProtect plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Block block = e.getBlock();
		AreaUtil areaUtil = new AreaUtil(plugin);
		if(areaUtil.checkLocation(player, block.getLocation())) {
			if(block.getTypeId() == 19) {
				new RemoveUtilities(player.getName()).start();
			}
		} else {
			e.setCancelled(true);
			plugin.getLogger().info(player.getName() + " tryed to break a block that they did not own at " + e.getBlock().getLocation().toString());
			player.sendMessage(ChatColor.RED + "You cannot break this block!");
		}
	}

	// I moved all of the Area checking stuff to AreaUtil, so we can use it here and in the BlockPlaceListener, 
	// if you really do not want it in there I can move it back here.
}
