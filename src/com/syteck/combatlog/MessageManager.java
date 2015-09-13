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
			y.set("", "");
			y.set("", "");
			y.set("", "");
			y.set("", "");
			y.set("", "");
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
