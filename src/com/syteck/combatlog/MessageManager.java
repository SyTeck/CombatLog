package com.syteck.combatlog;

import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;

public class MessageManager {

	public static final double VERSION = 0.1;
	public static HashMap<String, String> messageMap = new HashMap<String, String>();
	
	public static void setup(Config config) {
		
		config.load();
		
		if(config.getYaml().get("version") == null || config.getYaml().getDouble("version") != VERSION) {
			
			if(config.getYaml().get("version") != null && config.getYaml().getDouble("version") != VERSION) {
				
				CombatLog.log(Level.INFO, "The message config is outdated and is being updated.");
				CombatLog.log(Level.WARNING, "The messages might need reconfiguring after update.");
				
				config.getFile().delete();
				config.load();
				
			}
			
			YamlConfiguration y = config.getYaml();
			y.set("version", VERSION);
			y.set("action.nopermission", "&cYou do not have permission for this command.");
			y.set("action.playeronly", "This command can only be used by players.");
			
			y.set("command.reload", "&eYou successfully reloaded the config.");
			y.set("command.timeleft", "&eYou have &a%time%&e seconds left.");
			y.set("command.outofcombat", "&eYou are out of combat.");
			
			y.set("punishment.broadcast", "&cThe player &a%player%&e has just combat logged!");
			
			y.set("combat.ontag", "&cYou are now tagged as in combat.");
			y.set("combat.outofcombat", "&eYou are now out of combat.");
			
			config.save();	
		}
		
		for(String str: config.getYaml().getKeys(true)) {
			
			messageMap.put(str, config.getYaml().getString(str));
			
		}
	}
	
	public static String get(String key) {
		
		return messageMap.get(key).replace("&", "§");
		
	}
}
