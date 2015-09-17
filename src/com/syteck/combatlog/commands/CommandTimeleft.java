package com.syteck.combatlog.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.syteck.combatlog.MessageManager;
import com.syteck.combatlog.User;
import com.syteck.combatlog.UserManager;

public class CommandTimeleft implements ICommand {

	@Override
	public void execute(CommandSender sender, Command command, String[] args) {

		Player player = (Player) sender;
		User user = UserManager.get(player.getUniqueId());

		if(player.isOp() || player.hasPermission("combatlog.bypass")) {

			player.sendMessage(ChatColor.YELLOW+"You have permission to bypass the system.");

		} else {

			if(user.hasCombatTimer()) {

				player.sendMessage(MessageManager.get("command.timeleft").replace("%time%", "" + user.getCombatTimer().getTimeleft()));

			} else {

				player.sendMessage(MessageManager.get("command.outofcombat"));

			}
		}
	}

	@Override
	public boolean isPlayerOnly() {

		return true;

	}

	@Override
	public String getPermission() {

		return "combatlog.user";

	}

}
