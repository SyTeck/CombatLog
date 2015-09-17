package com.syteck.combatlog;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class User {

	private UUID id;
	private CombatTimer timer;
	
	public User(UUID id) {
		
		this.id = id;
		
	}
	
	public void startTimer() {
		
		if(!hasCombatTimer()) setCombatTimer(new CombatTimer(this, ConfigManager.getConfig().getYaml().getLong("combatlog.time")));
		timer.runTaskTimerAsynchronously(CombatLog.i, 0L, 20L);
		timer.setCombat(true);
		
		Bukkit.getPlayer(id).sendMessage(MessageManager.get("combat.ontag"));
	}
	public void stopTimer(boolean message) {
		
		if(!hasCombatTimer()) return;
		timer.cancel();
		timer.setCombat(false);
		timer = null;
		
		if(message) CombatLog.i.getServer().getPlayer(id).sendMessage(MessageManager.get("combat.outofcombat"));
	}
	
	public boolean hasCombatTimer() {
		
		return timer != null;
		
	}
	public void setCombatTimer(CombatTimer timer) {
		
		this.timer = timer;
		
	}
	public CombatTimer getCombatTimer() {
		
		return this.timer;
		
	}
	
	public UUID getId() {
		
		return this.id;
		
	}
	public Player getPlayer() {
		
		return Bukkit.getPlayer(id);
		
	}	
}
