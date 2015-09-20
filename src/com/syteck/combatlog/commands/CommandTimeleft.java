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

		if(player.isOp() || player.hasPermission("combatlog.bypass")) {

			player.sendMessage(ChatColor.YELLOW+"You have permission to bypass the system.");

		} else {

			if(UserManager.exists(player.getUniqueId()) && UserManager.get(player.getUniqueId()).hasCombatTimer()) {

				User user = UserManager.get(player.getUniqueId());
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
