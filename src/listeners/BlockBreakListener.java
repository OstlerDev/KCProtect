package listeners;

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

import utilities.RemoveUtilities;

import com.me.ostlerdev.KCProtect;

/**
 * @author DarkGhoul45
 */
public class BlockBreakListener implements Listener {

	private KCProtect plugin;
	private World currentWorld;

	public BlockBreakListener(KCProtect plugin) {
		this.plugin = plugin;
	}

	// TODO Test to see if this code works.
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Block block = e.getBlock();
		if(checkLocation(player, block.getLocation())) {
			if(block.getTypeId() == 18) {
				new RemoveUtilities(player.getName()).start();
			}
		} else {
			e.setCancelled(true);
			player.sendMessage(ChatColor.RED + "You cannot break this block!");
		}
	}

	 private boolean checkLocation(Player player, Location blockLocation) {
		 Map<String, Location> map = new HashMap<String, Location>();
		 List<String> data = new ArrayList<String>();
		 for(World w : plugin.getServer().getWorlds()) {
			 if(w.getPlayers().contains(player)) {
				currentWorld = w;
			 }
		 }
		 File[] files = new File("plugins/KWatcher/Areas/").listFiles();
		 for(File file : files) {
			 String ownerName = file.getName().split("Area")[0];
			 try {
				FileInputStream fin = new FileInputStream(file);
				DataInputStream din = new DataInputStream(fin);
				BufferedReader reader = new BufferedReader(new InputStreamReader(din));
				String line;
				int x, y, z;
				while((line = reader.readLine()) != null) {
					data.add(line);
				}
				x = Integer.parseInt(data.toString().split(",")[0]);
				y = Integer.parseInt(data.toString().split(",")[1]);
				z = Integer.parseInt(data.toString().split(",")[2]);
				map.put(ownerName, new Location(currentWorld, x, y, z));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			for(int i = 0; i < map.size(); i++) {
				if(map.get(i).equals(player.getName()) && blockLocation.equals(map.get(i).getBlock().getLocation())) {
					return true;
				}
			}
		 }
		return false;
	 }

	 /*
	  * Just in-case I decide to change my mind.
	 private boolean sameLocation(Location blockLocation, Block block) {
		 if(blockLocation.equals(block.getLocation())) {
			 return true;
		 }
		 return false;
	 }
	 */

}