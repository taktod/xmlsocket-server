package com.ttProject.php;

import java.util.Map;
import java.util.WeakHashMap;

public class ArgumentManager {
	private static ArgumentManager instance = new ArgumentManager();
	private Map<String, Object[]> arguments = new WeakHashMap<String, Object[]>();
	private int id = 0;
	private ArgumentManager() {
		
	}
	public synchronized static ArgumentManager getInstance() {
		if(instance == null) {
			throw new RuntimeException("Argument Manager is broken");
		}
		return instance;
	}
	public String setArgument(Object... params) {
		String key = Integer.toString(id ++);
		arguments.put(key, params);
		return key;
	}
	public Object[] getArgument(String key) {
		return arguments.get(key);
	}
}
