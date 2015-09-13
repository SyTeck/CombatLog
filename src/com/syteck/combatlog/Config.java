package com.syteck.combatlog;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	File file;
	YamlConfiguration yaml;

	boolean loaded;

	public Config(File configFile) {

		this.file = configFile;

	}

	public File getFile() {
		
		return this.file;
		
	}
	public YamlConfiguration getYaml() {
		
		return this.yaml;
		
	}

	public boolean isLoaded() {
		
		return this.loaded;
		
	}
	
	public void save() {

		try {

			yaml.save(file);

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	public void load() {

		if(!file.exists()) {

			try {

				file.createNewFile();

			} catch (IOException e) {

				e.printStackTrace();

			}
		}

		yaml = YamlConfiguration.loadConfiguration(file);
		loaded = true;
	}
}
