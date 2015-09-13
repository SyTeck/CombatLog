package com.syteck.combatlog.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ICommand {

	public void execute(CommandSender sender, Command command, String[] args);
	public boolean isPlayerOnly();
	public String getPermission();
	
}
