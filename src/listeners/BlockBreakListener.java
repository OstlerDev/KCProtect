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
			String ownerName = file.getName().split("Area")[0];
			try {
				FileInputStream fin = new FileInputStream(file);
				DataInputStream din = new DataInputStream(fin);
				BufferedReader reader = new BufferedReader(new InputStreamReader(din));
				String line;
				data.clear();
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
				if(map.get(i).equals(player.getName()) && blockLocation.equals(map.get(i).getBlock().getLocation()) &&
						goodBoundaries(player.getLocation().getX(), player.getLocation().getZ(), x, z)) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 *  TODO Connect old code with this and zones will be created! No need for Create Area class.
	 *  This method will check to see if player is in the zone
	 */
	public boolean goodBoundaries(double playerX, double playerY, int x, int z) {
		return false;
	}

	/**
	 * @author Killutch
	 */

	/*
	 *
	public double[] zoneLoc1 = new double[2];
	public double[] zoneLoc2 = new double[2];
	public boolean[] zoneLocBool1 = new boolean[2];
	public boolean[] zoneLocBool2 = new boolean[2];
	public double[] playersXY = new double[2];




	public boolean isInZone(Zone zone, Player player){
		ZoneToLocalInt(zone);
		ConvertPlayersXYCordsToDoubles(player);
		return IsPlayerInsideOfRectangle();
	}



		private boolean IsPlayerInsideOfRectangle(){
			boolean theReturn = true;
			for(int i=0;i<2;i++){
				//if player is not between 2 lines
				if(IsPlayerInsideOfLine(playersXY[i], zoneLoc1[i], zoneLocBool1[i]) == false){
					theReturn = false;
				}else if(IsPlayerInsideOfLine(playersXY[i], zoneLoc2[i], zoneLocBool2[i]) == false){
					theReturn = false;
				}
			}
			return theReturn;
		}



		private boolean IsPlayerInsideOfLine(double playerAxis, double line, boolean plusSideOfLine) {
			boolean over = true;
			if(plusSideOfLine == true && (int)playerAxis < line){
				over = false;
			}else if(plusSideOfLine == false && (int)playerAxis > line){
				over = false;
			}
			return over;
		}


	private void ConvertPlayersXYCordsToDoubles(Player player) {
		playersXY[0] = player.getLocation().getX();
		playersXY[1] = player.getLocation().getZ();
	}



	private void ZoneToLocalInt(Zone zone){
		zoneLoc1[0] = zone.getSquareSide1();
		zoneLocBool1[0] = zone.getSide1Pos(); //1 = Positive side of line is out of bounds

		zoneLoc1[1] = zone.getSquareSide2();
		zoneLocBool1[1] = zone.getSide2Pos();

		zoneLoc2[0] = zone.getSquareSide3();
		zoneLocBool2[0] = zone.getSide3Pos();

		zoneLoc2[1] = zone.getSquareSide4();
		zoneLocBool2[1] = zone.getSide4Pos();

	}

	//converts 2 Locations into a Zone with zones if "side#Pos" is true then then the adding to the cord means its out of bounds
	public Zone Convert2LocationsTo1Zone(Location L1, Location L2){
		Zone newZone = new Zone();
		double loc1x = L1.getX();
		double loc1y = L1.getZ();
		double loc2x = L2.getX();
		double loc2y = L2.getZ();

		if(loc1x > loc2x){
			newZone.setSide1Pos(false);
			newZone.setSide3Pos(true);
		}else{
			newZone.setSide1Pos(true);
			newZone.setSide3Pos(false);
		}

		if(loc1y > loc2y){
			newZone.setSide2Pos(false);
			newZone.setSide4Pos(true);
		}else{
			newZone.setSide2Pos(true);
			newZone.setSide4Pos(false);
		}
		newZone.setSquareSide1((int)loc1x);
		newZone.setSquareSide2((int)loc1y);
		newZone.setSquareSide3((int)loc2x);
		newZone.setSquareSide4((int)loc2y);
		return newZone;
	}
	 */
}