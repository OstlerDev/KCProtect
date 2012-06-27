package com.me.olisterdev;

import java.util.logging.Logger;

import listeners.BlockBreakListener;

import org.bukkit.plugin.java.JavaPlugin;

import utilities.CreateUtilities;

/**
 * @author DarkGhoul45
 */
public class KWatcher extends JavaPlugin {

	private Logger logger;

	public KWatcher() {
		super();
	}

	@Override
	public void onEnable() {
		super.onEnable();
		logger = getLogger();
		logger.info("KWatcher has been enabled!");
		new CreateUtilities();
		KWatcherCommandExecutor executor = new KWatcherCommandExecutor();
		// TODO insert all commands.
		getCommand("").setExecutor(executor);
		// TODO insert the rest of the listeners.
		getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
	}

	@Override
	public void onDisable() {
		super.onDisable();
		logger.info("KWatcher has been disabled!");
	}

}
