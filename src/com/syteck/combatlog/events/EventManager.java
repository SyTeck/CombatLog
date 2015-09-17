package com.syteck.combatlog.events;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.syteck.combatlog.CombatLog;
import com.syteck.combatlog.ConfigManager;
import com.syteck.combatlog.MessageManager;
import com.syteck.combatlog.User;
import com.syteck.combatlog.UserManager;

public class EventManager implements Listener {

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {

		UserManager.add(event.getPlayer().getUniqueId());

	}

	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event) {

		Player player = event.getEntity();
		User user = UserManager.get(player.getUniqueId());

		if(user.hasCombatTimer() && user.getCombatTimer().isInCombat()) {

			user.stopTimer(true);

		}
	}

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {

		Player player = event.getPlayer();
		User user = UserManager.get(player.getUniqueId());

		if(player.isOp() || player.hasPermission("combatlog.bypass")) return;
		if(!ConfigManager.getConfig().getYaml().getBoolean("combatlog.enabled")) return;
		if(!ConfigManager.getConfig().getYaml().getStringList("combatlog.disabledworlds").contains(player.getWorld().getName())) return;

		if(user.hasCombatTimer() && user.getCombatTimer().isInCombat()) {

			if(ConfigManager.getConfig().getYaml().getBoolean("punishment.kill")) player.setHealth(0.0);
			if(ConfigManager.getConfig().getYaml().getBoolean("punishment.broadcast")) Bukkit.broadcastMessage(MessageManager.get("punishment.broadcast").replace("%player%", player.getName()));

			CombatLog.log(Level.INFO, "A player called " + player.getName() + " just combat logged.");
		}

		user.stopTimer(false);
		UserManager.remove(player.getUniqueId());
	}

	@EventHandler
	public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event) {

		Player player = event.getPlayer();
		User user = UserManager.get(player.getUniqueId());

		if(player.isOp() || player.hasPermission("combatlog.bypass")) return;
		if(!ConfigManager.getConfig().getYaml().getBoolean("combatlog.enabled")) return;
		if(!ConfigManager.getConfig().getYaml().getStringList("combatlog.disabledworlds").contains(player.getWorld().getName())) return;

		if(!ConfigManager.getConfig().getYaml().getBoolean("worlds.enabled")) return;
		if(!ConfigManager.getConfig().getYaml().getStringList("worlds.disabledworlds").contains(player.getWorld().getName())) return;

		if(user.hasCombatTimer() && user.getCombatTimer().isInCombat()) {

			player.teleport(player.getLastDamageCause().getEntity());
			if(ConfigManager.getConfig().getYaml().getBoolean("punishment.kill")) player.setHealth(0.0);
			if(ConfigManager.getConfig().getYaml().getBoolean("punishment.broadcast")) Bukkit.broadcastMessage(MessageManager.get("punishment.broadcast").replace("%player%", player.getName()));

			CombatLog.log(Level.INFO, "A player called " + player.getName() + " just combat logged.");
			user.stopTimer(true);
		}
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {

		Entity target = event.getEntity(), damager = event.getDamager();
		if(!(target instanceof Player) || !(damager instanceof Player)) return;

		Player p1 = (Player) event.getEntity(), p2 = (Player) event.getDamager();
		User u1 = UserManager.get(p1.getUniqueId()), u2 = UserManager.get(p2.getUniqueId());

		if(!p1.isOp() && !p1.hasPermission("combatlog.bypass")) {

			if(!u1.hasCombatTimer()) {

				u1.startTimer();

			} else u1.getCombatTimer().setLastHit(System.currentTimeMillis());
		}

		if(!p2.isOp() && !p2.hasPermission("combatlog.bypass")) {

			if(!u2.hasCombatTimer()) {

				u2.startTimer();

			} else u2.getCombatTimer().setLastHit(System.currentTimeMillis());
		}
	}
}
