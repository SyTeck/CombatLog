package com.syteck.combatlog.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.syteck.combatlog.CombatLog;
import com.syteck.combatlog.Config;
import com.syteck.combatlog.ConfigManager;
import com.syteck.combatlog.MessageManager;

public class CommandCombatlog implements ICommand {

	@Override
	public void execute(CommandSender sender, Command command, String[] args) {
		
		if(args.length < 1) {
			
			if(sender instanceof Player) {
				
				sender.sendMessage(ChatColor.YELLOW + "You are running version " + ChatColor.GREEN + CombatLog.VERSION + ChatColor.YELLOW + " of CombatLog.");
				sender.sendMessage(ChatColor.YELLOW + "Type " + ChatColor.GREEN + "'/combatlog reload'" + ChatColor.YELLOW + " to reload config.");
				
			} else {
				
				sender.sendMessage("You are running version " + CombatLog.VERSION + " of CombatLog.");
				sender.sendMessage("Type '/combatlog reload' to reload config.");
				
			}
			
		} else {
			
			String arg = args[0];
			
			if(arg.equalsIgnoreCase("reload")) {
				
				ConfigManager.setup(new Config(new File(CombatLog.i.getDataFolder(), "config.yml")));
				MessageManager.setup(new Config(new File(CombatLog.i.getDataFolder(), "messages.yml")));
				
				if(sender instanceof Player) {
					
					sender.sendMessage(ChatColor.YELLOW + "You successfully reloaded the config.");
					
				} else {
					
					sender.sendMessage("You successfully reloaded the config.");
					
				}
			}
		}
	}

	@Override
	public boolean isPlayerOnly() {
	
		return false;
		
	}

	@Override
	public String getPermission() {
		
		return "combatlog.admin";
		
	}

}
