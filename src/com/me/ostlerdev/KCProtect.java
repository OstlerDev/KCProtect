package com.me.ostlerdev;

import java.util.logging.Logger;


import org.bukkit.plugin.java.JavaPlugin;

import com.me.olsterdev.utilities.CreateUtilities;
import com.me.ostlerdev.listeners.BlockBreakListener;
import com.me.ostlerdev.listeners.BlockPlaceListener;


/**
 * @author DarkGhoul45
 */
public class KCProtect extends JavaPlugin {

	private Logger logger;

	public KCProtect() {
		super();
	}

	@Override
	public void onEnable() {
		super.onEnable();
		logger = getLogger();
		new CreateUtilities();
		KCProtectCommandExecutor executor = new KCProtectCommandExecutor();
		getCommand("kc").setExecutor(executor);
		getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
		getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
		logger.info("KCProtect has been enabled!");
	}

	@Override
	public void onDisable() {
		super.onDisable();
		logger.info("KCProtect has been disabled!");
	}

}