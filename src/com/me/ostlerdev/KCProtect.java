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
public class KCProtect extends JavaPlugin {

	// Logger is needed to display information in the future. Don't remove it.
	private Logger logger;

	public KCProtect() {
		super();
	}

	@Override
	public void onEnable() {
		super.onEnable();
		logger = getLogger();
		new CreateUtilities();
		new Config(this);
		KCProtectCommandExecutor executor = new KCProtectCommandExecutor();
		// TODO add the rest of the commands.
		getCommand("kc").setExecutor(executor);
		// TODO add the rest of the listeners. (if any)
		getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
		getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
		logger.info("KCProtect has been enabled!");
	}

	@Override
	public void onDisable() {
		super.onDisable();
		logger.info("KCProtect has been disabled!");
	}

}