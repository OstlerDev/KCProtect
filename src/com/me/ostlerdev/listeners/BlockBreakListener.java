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
 * @author DarkGhoul45
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
		for(File file : files) {
			String ownerName = file.getName().split("Area.txt")[0];
			try {
				FileInputStream fin = new FileInputStream(file);
				DataInputStream din = new DataInputStream(fin);
				BufferedReader reader = new BufferedReader(new InputStreamReader(din));
				String line;
				data.clear();
				map.clear();
				while((line = reader.readLine()) != null) {
					data.add(line);
				}
				x = Integer.parseInt(data.toString().split(",")[0]);
				y = Integer.parseInt(data.toString().split(",")[1]);
				z = Integer.parseInt(data.toString().split(",")[2]);
				map.put(ownerName, new Location(currentWorld, x, y, z));
				reader.close();
				din.close();
				fin.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			for(int i = 0; i < map.size(); i++) {
				if(map.get(i).equals(player.getName()) && blockLocation.equals(map.get(i).getBlock().getLocation())) {
					return true;
				} else if(goodBoundaries(player.getLocation().getX(), player.getLocation().getZ(), map.get(i).getX(), map.get(i).getZ())) {
					return true;
				}
			}
		}
		return false;
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
					valueX = Integer.parseInt(line.split("valueX=")[1]);
					valueZ = Integer.parseInt(line.split("valueZ=")[1]);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Polygon zone = new Polygon();
		zone.addPoint((int) x + valueX, (int) z - valueZ);
		zone.addPoint((int) x - valueX, (int) z + valueZ);
		if(!zone.contains(playerX, playerZ)) {
			return true;
		}
		return false;
	}
}
