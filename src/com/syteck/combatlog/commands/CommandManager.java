package com.syteck.combatlog.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.syteck.combatlog.MessageManager;

public class CommandManager {

	public HashMap<String, ICommand> commandMap = new HashMap<String, ICommand>();

	public CommandManager() {
		
		commandMap.put("combatlog", new CommandCombatlog());
		commandMap.put("timeleft", new CommandTimeleft());

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

					player.sendMessage(MessageManager.get("action.nopermission"));

				}

			} else {

				if(c.isPlayerOnly()) {

					sender.sendMessage(MessageManager.get("action.playeronly"));

				} else {

					c.execute(sender, command, args);

				}	
			}
		} else return false;
		return true;
	}
}
