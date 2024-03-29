package com.me.ostlerdev.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author DarkGhoul45
 */
public class CreateUtilities extends Thread {

	private Player player;
	private Location location;

	public CreateUtilities() {
		File[] directories = {
				new File("plugins/KCProtect/Areas/"),
				new File("plugins/KCProtect/Logs/")
		};
		for(File file : directories) {
			if(!file.exists()) {
				file.mkdirs();
			}
		}
		// TODO Make this a useful file.
		File config = new File("plugins/KCProtect/config.txt");
		if(!config.exists()) {
			try {
				config.createNewFile();
				try(FileWriter fstream = new FileWriter(config); BufferedWriter writer = new BufferedWriter(fstream))
				{
				writer.write("valueX=10\n" + "valueZ=10\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public CreateUtilities(Player player, Location location) {
		super();
		this.player = player;
		this.location = location;
	}

	@Override
	public void run() {
		super.run();
		File file = new File("plugins/KCProtect/Areas/" + player.getName() + "Area.txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try(FileWriter fstream = new FileWriter(file); BufferedWriter writer = new BufferedWriter(fstream))
		{
			writer.write(location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
