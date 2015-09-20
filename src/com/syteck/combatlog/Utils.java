package com.syteck.combatlog;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Utils {

	public static boolean isValid(UUID id) {
		
		Player player = Bukkit.getPlayer(id);
		
		if(!UserManager.exists(player.getUniqueId())) return false;
		if(player.isOp() || player.hasPermission("combatlog.bypass")) return false;
		if(!ConfigManager.getConfig().getYaml().getBoolean("combatlog.enabled")) return false;
		if(!ConfigManager.getConfig().getYaml().getStringList("combatlog.disabledworlds").contains(player.getWorld().getName())) return false;
		
		return true;
	}
	
	public static boolean isValid(UUID id, boolean user, boolean permission) {
		
		Player player = Bukkit.getPlayer(id);
		
		if(user) if(!UserManager.exists(player.getUniqueId())) return false;
		if(permission) if(player.isOp() || player.hasPermission("combatlog.bypass")) return false;
		if(!ConfigManager.getConfig().getYaml().getBoolean("combatlog.enabled")) return false;
		if(!ConfigManager.getConfig().getYaml().getStringList("combatlog.disabledworlds").contains(player.getWorld().getName())) return false;
		
		return true;
	}
}
