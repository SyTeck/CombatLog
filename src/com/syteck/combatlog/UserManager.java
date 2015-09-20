package com.syteck.combatlog;

import java.util.HashMap;
import java.util.UUID;

public class UserManager {

	private static HashMap<UUID, User> userMap = new HashMap<UUID, User>();
	
	public static void add(UUID id) {
		
		userMap.put(id, new User(id));
		
	}
	public static User get(UUID id) {
		
		if(exists(id)) add(id);
		
		return userMap.get(id);
	}
	public static boolean exists(UUID id) {
		
		return userMap.containsKey(id);
		
	}
	public static void remove(UUID id) {
		
		userMap.remove(id);
		
	}

	public static void clear() {
		
		for(User user: userMap.values()) {
			
			user.stopTimer(false);
			
		}
		
		userMap.clear();
	}
}
