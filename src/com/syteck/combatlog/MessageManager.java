package com.syteck.combatlog;

import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;

public class MessageManager {

	public static HashMap<String, String> messageMap = new HashMap<String, String>();
	
	public static void setup(Config config) {
		
		if(!config.getFile().exists()) {
			
			config.load();
			
			YamlConfiguration y = config.getYaml();
			y.set("action.nopermission", "&cYou do not have permission for this command.");
			y.set("action.playeronly", "This command can only be used by players.");
			
			y.set("command.reload", "&eYou successfully reloaded the config.");
			y.set("command.timeleft", "&eYou have &a%time%&e seconds left.");
			y.set("command.outofcombat", "&eYou are currently not in combat.");
			y.set("", "");
			y.set("", "");
			y.set("", "");
			y.set("", "");
			y.set("", "");
			y.set("", "");
			
			config.save();
			
		} else config.load();
		
		CombatLog.log(Level.INFO, "Loading " + config.getYaml().getKeys(false).size() + " different messages.");
		for(String str: config.getYaml().getKeys(true)) {
			
			messageMap.put(str, config.getYaml().getString(str));
			
		}
	}
	
	public static String get(String key) {
		
		return messageMap.get(key).replace("&", "§");
		
	}
}
