package com.syteck.combatlog;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.syteck.combatlog.commands.CommandManager;
import com.syteck.combatlog.events.EventManager;

public class CombatLog extends JavaPlugin {
	
	public CommandManager commandManager;
	public EventManager eventManager;

	public static CombatLog i;
	public static void log(Level level, String message) {
		
		Bukkit.getLogger().log(level, "[CombatLog] " + message);
		
	}
	
	@Override
	public void onDisable() {
		
		UserManager.clear();

		super.onDisable();
	}

	@Override
	public void onEnable() {

		i = this;
		ConfigManager.setup(new Config(new File(this.getDataFolder(), "config.yml")));
		MessageManager.setup(new Config(new File(this.getDataFolder(), "messages.yml")));
		
		commandManager = new CommandManager();
		eventManager = new EventManager();
		
		Bukkit.getPluginManager().registerEvents(eventManager, this);
		
		super.onEnable();
	}


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		return commandManager.onCommand(sender, command, args);

	}
}
