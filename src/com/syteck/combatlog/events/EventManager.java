package com.syteck.combatlog.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.syteck.combatlog.ConfigManager;

public class EventManager implements Listener {

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {

		Player player = event.getPlayer();
		if(player.isOp() || player.hasPermission("combatlog.bypass")) return;
		
		
	}

	@EventHandler
	public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event) {
		
		Player player = event.getPlayer();
		if(!ConfigManager.getConfig().getYaml().getBoolean("worlds.enable")) return;
		if(!ConfigManager.getConfig().getYaml().getStringList("worlds.disabledworlds").contains(player.getWorld().getName())) return;

		
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {

		Entity target = event.getEntity(), damager = event.getDamager();
		if(!(target instanceof Player) || !(damager instanceof Player)) return;
		Player p1 = (Player) event.getEntity(), p2 = (Player) event.getDamager();


	}
}
