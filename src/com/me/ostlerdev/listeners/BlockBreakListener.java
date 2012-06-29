package com.me.ostlerdev.listeners;

import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


import com.me.ostlerdev.KCProtect;
import com.me.ostlerdev.utilities.RemoveUtilities;

/**
 * @author DarkGhoul45, OstlerDev
 */
public class BlockBreakListener implements Listener {

	private KCProtect plugin;
	private World currentWorld;

	public BlockBreakListener(KCProtect plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Block block = e.getBlock();
		if(checkLocation(player, block.getLocation())) {
			if(block.getTypeId() == 19) {
				new RemoveUtilities(player.getName()).start();
			}
		} else {
			e.setCancelled(true);
			plugin.getLogger().info(player.getName() + " tryed to break a block that they did not own at " + e.getBlock().getLocation().toString());
			player.sendMessage(ChatColor.RED + "You cannot break this block!");
		}
	}

	// TODO Test new code
	private boolean checkLocation(Player player, Location blockLocation) {
		File[] files = new File("plugins/KCProtect/Areas/").listFiles();
		if(files.length == 0) {
			return true;
		}
		Map<String, Location> map = new HashMap<String, Location>();
		List<String> data = new ArrayList<String>();
		for(World w : plugin.getServer().getWorlds()) {
			if(w.getPlayers().contains(player)) {
				currentWorld = w;
			}
		}
		int x = 0, y = 0, z = 0;
		List<String> PlayerList = new ArrayList<String>();
		for(int b = 0; b < files.length; b++) {
			File file = files[b];
			if (file.toString().contains("Area.txt")){
				String ownerName = file.toString().replace("Area.txt", "");
				ownerName = ownerName.replace("plugins/KCProtect/Areas/", "");
				plugin.getLogger().info(ownerName);
				try {
					//Check if player is owner
					if (ownerName.equals(player.getDisplayName())) return true;
					//I had to change the file reader as it was freaking out, I hope you do not mind
					FileInputStream fs;
					fs = new FileInputStream(file);
					BufferedReader br = new BufferedReader(new InputStreamReader(fs));
					String line = br.readLine();
					data.clear();
					map.clear();
					plugin.getLogger().info(line);
					String[] a = line.split(",");
					x = Integer.parseInt(a[0]);
					y = Integer.parseInt(a[1]);
					z = Integer.parseInt(a[2]);
					map.put(ownerName, new Location(currentWorld, x, y, z));
					PlayerList.add(ownerName);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				for(int i = 0; i < map.size(); i++) {
					//I had to switch map.get(i) to map.get(ownerName) because map.get(i) would not work because HashMaps do not work as an array
					if(map.get(PlayerList.get(i)).equals(player.getDisplayName()) && blockLocation.equals(map.get(PlayerList.get(i)).getBlock().getLocation())) {
						return false;
					} else if(goodBoundaries(blockLocation.getX(), blockLocation.getZ(), map.get(PlayerList.get(i)).getX(), map.get(PlayerList.get(i)).getZ())) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public boolean goodBoundaries(double playerX, double playerZ, double x, double z) {
		int valueX = 0, valueZ = 0;
		try {
			FileInputStream fin = new FileInputStream("plugins/KCProtect/config.txt");
			DataInputStream din = new DataInputStream(fin);
			BufferedReader reader = new BufferedReader(new InputStreamReader(din));
			String line;
			while((line = reader.readLine()) != null) {
				if(line.contains("valueX=")){
					//I had to change this method a bit I hope you do not mind.
					valueX = Integer.parseInt(line.replace("valueX=", ""));
				}
				else if (line.contains("valueZ=")){
					valueZ = Integer.parseInt(line.replace("valueZ=", ""));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/**
		 * I completely redid the Region Checking part as it was not
		 * working at all with the Polygon class, if you would like 
		 * to mess with the polygon class again, just tell me and I
		 * will remove this code.
		 */

		Location pointOne = new Location(currentWorld, x - valueX, 0, z - valueZ);
		Location pointTwo = new Location(currentWorld, x + valueX, 256, z + valueZ);
		Location pointCheck = new Location(currentWorld, playerX, 64, playerZ);
		
		
		double xOne = Math.max(pointOne.getX(),pointTwo.getX());
		double yOne = Math.max(pointOne.getY(),pointTwo.getY());
		double zOne = Math.max(pointOne.getZ(),pointTwo.getZ());
		//this lets you deal with xTwo,yTwo,zTwo as always being smallest
		double xTwo = Math.min(pointOne.getX(),pointTwo.getX());
		double yTwo = Math.min(pointOne.getY(),pointTwo.getY());
		double zTwo = Math.min(pointOne.getZ(),pointTwo.getZ());

		double xCheck = pointCheck.getX();
		double yCheck = pointCheck.getY();
		double zCheck = pointCheck.getZ();
		/*
		 * Here's a triple nested if clause - have fun!
		 * You can probably clean this up a LOT
		 */
		if(xCheck>xTwo && xCheck<xOne) {
			if(yCheck>yTwo && yCheck<yOne) {
				if(zCheck>zTwo && zCheck<zOne) {
					plugin.getLogger().info("Player was Inside the Area!");
					//return true if pointCheck is in the region
					return true;
				}
			}
		}
		//return false if pointCheck is not in the region.
		plugin.getLogger().info("Player was Outside the Area!");
		return false;
	}
}
