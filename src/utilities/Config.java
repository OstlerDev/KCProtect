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
		p.getConfig().options().copyDefaults(true);
	    p.saveConfig();
		return false;
	}
}
