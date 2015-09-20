package com.syteck.combatlog.events;

import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.syteck.combatlog.CombatLog;
import com.syteck.combatlog.ConfigManager;
import com.syteck.combatlog.MessageManager;
import com.syteck.combatlog.User;
import com.syteck.combatlog.UserManager;
import com.syteck.combatlog.Utils;

public class EventManager implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onPlayerDeathEvent(PlayerDeathEvent event) {

		Player player = event.getEntity();
		if(!Utils.isValid(player.getUniqueId())) return;
		User user = UserManager.get(player.getUniqueId());

		if(user.hasCombatTimer() && user.getCombatTimer().isInCombat()) {

			user.stopTimer(true);

		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerQuitEvent(PlayerQuitEvent event) {

		Player player = event.getPlayer();
		if(!Utils.isValid(player.getUniqueId())) return;
		User user = UserManager.get(player.getUniqueId());
		
		if(user.hasCombatTimer() && user.getCombatTimer().isInCombat()) {

			if(ConfigManager.getConfig().getYaml().getBoolean("punishment.kill")) player.setHealth(0.0);
			if(ConfigManager.getConfig().getYaml().getBoolean("punishment.broadcast")) Bukkit.broadcastMessage(MessageManager.get("punishment.broadcast").replace("%player%", player.getName()));

			CombatLog.log(Level.INFO, "A player called " + player.getName() + " just combat logged.");
		}

		user.stopTimer(false);
		UserManager.remove(player.getUniqueId());
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {

		Player player = event.getPlayer();
		if(!Utils.isValid(player.getUniqueId())) return;
		User user = UserManager.get(player.getUniqueId());
		String command = event.getMessage().split(" ")[0].replace("/", "");
		
		if(ConfigManager.getConfig().getYaml().getStringList("disallow.commands").contains(command)) {
			
			if(user.hasCombatTimer() && user.getCombatTimer().isInCombat()) {
				
				event.setCancelled(true);
				player.sendMessage(MessageManager.get("combat.command"));
				
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerTeleportEvent(PlayerTeleportEvent event) {

		Player player = event.getPlayer();
		if(!Utils.isValid(player.getUniqueId())) return;
		User user = UserManager.get(player.getUniqueId());
		
		if(ConfigManager.getConfig().getYaml().getBoolean("disallow.teleporting")) {

			if(!ConfigManager.getConfig().getYaml().getBoolean("disallow.enderpearls") && event.getCause().equals(TeleportCause.ENDER_PEARL)) return;
			
			if(user.hasCombatTimer() && user.getCombatTimer().isInCombat()) {

				event.setCancelled(true);
				player.sendMessage(MessageManager.get("combat.teleport"));

			}
		}
	}

	//This event tags
	@EventHandler(ignoreCancelled = true)
	public void onPotionSplashEvent(PotionSplashEvent event) {

		if(event.getEntity() instanceof Player) {

			Player player = (Player) event.getEntity();
			if(!Utils.isValid(player.getUniqueId(), false, false)) return;
			
			ArrayList<Player> players = new ArrayList<Player>();
			for(Entity entity: event.getAffectedEntities()) { if(entity instanceof Player) players.add((Player) entity); }
			players.add(player);

			for(Player p: players) {

				if(p.isOp() || p.hasPermission("combatlog.bypass")) continue;
				User user = UserManager.get(p.getUniqueId());

				if(!user.hasCombatTimer()) {

					user.startTimer();

				} else user.getCombatTimer().setLastHit(System.currentTimeMillis());
			}
			
			players.clear();
		}
	}

	//This event tags
	@EventHandler(ignoreCancelled = true)
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {

		Entity target = event.getEntity(), damager = event.getDamager();
		if(!(target instanceof Player) || !(damager instanceof Player)) return;

		Player p1 = (Player) event.getEntity(), p2 = (Player) event.getDamager();
		if(!Utils.isValid(p1.getUniqueId(), false, false)) return;
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
