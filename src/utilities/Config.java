package utilities;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.me.ostlerdev.KCProtect;

public class Config {

	/**
	 * @author OstlerDev
	 */
	Plugin p;
	public Config(Plugin plugin){
		p = plugin;
	}
	
	public Config(KCProtect plugin) {
		LoadConfig();
	}

	public boolean LoadConfig(){
		/**
		 * 
		 * TODO:
		 * Decide what plot sizes we are going to use and what blocks default
		 * then add an option to the config for the different sizes.
		 * 
		 */
		
		File configFile = new File(Bukkit.getServer().getPluginManager().getPlugin("KCProtect").getDataFolder(), "config.yml");
        if (configFile.exists()) {
        	p.getConfig().addDefault("Utilities.VerboseMode", true);
    		p.getConfig().options().copyDefaults(true);
    	    p.saveConfig();
    	    return true;
        } else
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return false;
	}
}
