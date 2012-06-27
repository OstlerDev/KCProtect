package utilities;

import org.bukkit.plugin.Plugin;

import com.me.ostlerdev.KWatcher;

public class Config {

	/**
	 * @author OstlerDev
	 */
	Plugin p;
	public Config(Plugin plugin){
		p = plugin;
	}
	
	public Config(KWatcher plugin) {
		LoadConfig();
	}

	public boolean LoadConfig(){
		/**
		 * 
		 * TODO:
		 * Decide what plot sizes we are going to use and what blocks default
		 * then add an option to the config
		 * 
		 */
		p.getConfig().addDefault("Utilities.VerboseMode", true);
		p.getConfig().options().copyDefaults(true);
	    p.saveConfig();
		return false;
	}
}
