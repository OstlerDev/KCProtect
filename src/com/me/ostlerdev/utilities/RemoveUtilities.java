package com.me.ostlerdev.utilities;

import java.io.File;

/**
 * @author DarkGhoul45
 */
public class RemoveUtilities extends Thread {

	private String playerName;

	public RemoveUtilities(String playerName) {
		super();
		this.playerName = playerName;
	}

	@Override
	public void run() {
		super.run();
		File file = new File("plugins/KWatcher/Areas/" + playerName + "Area.txt");
		if(file.exists()) {
			file.delete();
		}
	}

}
