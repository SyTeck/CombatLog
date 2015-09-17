package com.syteck.combatlog;

import org.bukkit.scheduler.BukkitRunnable;

public class CombatTimer extends BukkitRunnable {

	private User user;
	private boolean combat;
	private long lastHit, period;
	
	public CombatTimer(User user, long period) {
		
		this.user = user;
		this.lastHit = System.currentTimeMillis();
		this.period = period;
		
	}
	
	public void setCombat(boolean value) {
		
		combat = value;
		
	}
	public boolean isInCombat() {
		
		return this.combat;
		
	}
	public long getTimeleft() {
		
		return ((lastHit + (period * 1000)) - System.currentTimeMillis()) / 1000;
		
	}
 	public void setLastHit(long lastHit) {
		
		this.lastHit = lastHit;
		
	}
	
	@Override
	public void run() {
		
		if(!isInCombat()) return;
		if(System.currentTimeMillis() >= lastHit + (period * 1000)) {
			
			user.stopTimer(true);
			
		}
	}

}
