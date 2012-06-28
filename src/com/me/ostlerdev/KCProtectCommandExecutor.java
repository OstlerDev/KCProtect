package com.me.ostlerdev;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author OlisterDev
 * @author DarkGhoul45
 */
public class KCProtectCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equals("kc")) {
			// TODO Handle all of the commands.
			@SuppressWarnings("unused")
			Player player = (Player) sender;
		}
		return false;
	}

}
