package com.me.ostlerdev;

import java.util.logging.Logger;

import listeners.BlockBreakListener;
import listeners.BlockPlaceListener;

import org.bukkit.plugin.java.JavaPlugin;

import utilities.Config;
import utilities.CreateUtilities;

/**
 * @author DarkGhoul45
 */
public class KWatcher extends JavaPlugin {

	@SuppressWarnings("unused")
	private Logger logger;

	public KWatcher() {
		super();
	}

	@Override
	public void onEnable() {
		super.onEnable();
		logger = getLogger();
		new CreateUtilities();
		new Config(this);
		KWatcherCommandExecutor executor = new KWatcherCommandExecutor();
		// TODO insert all commands.
		getCommand("kc").setExecutor(executor);
		// TODO insert the rest of the listeners.
		getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
		getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

}
