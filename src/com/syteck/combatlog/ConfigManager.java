package com.syteck.combatlog;

import java.util.logging.Level;

public class ConfigManager {

	public static final double VERSION = 0.1;

	public static void setup(Config config) {

		if(!config.getFile().exists()) {

			CombatLog.i.saveDefaultConfig();
			config.load();

		} else config.load();

		if(config.getYaml().getDouble("VERSION") != VERSION) {

			CombatLog.log(Level.INFO, "The config is outdated and is being updated.");

			if(config.getFile().delete()) {

				CombatLog.i.saveDefaultConfig();
				config.load();

			} else {

				CombatLog.log(Level.WARNING, "The config could not be updated and errors might occur.");

			}
		}
	}

}