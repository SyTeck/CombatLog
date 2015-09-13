package com.syteck.combatlog.commands;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager {

	public HashMap<String, ICommand> commandMap = new HashMap<String, ICommand>();

	public CommandManager() {



	}

	public boolean onCommand(CommandSender sender, Command command, String[] args) {

		if(commandMap.containsKey(command.getName())) {

			ICommand c = commandMap.get(command.getName());

			if(sender instanceof Player) {

				Player player = (Player) sender;
				String permission = c.getPermission();

				if(player.hasPermission(permission)) {

					c.execute(sender, command, args);

				} else {

					player.sendMessage(ChatColor.RED + "You do not have permission to do this.");

				}

			} else {

				if(c.isPlayerOnly()) {

					sender.sendMessage("This command can only be executed by players.");

				} else {

					c.execute(sender, command, args);

				}	
			}
		} else return false;
		return true;
	}
}
