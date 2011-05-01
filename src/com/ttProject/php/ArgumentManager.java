package com.ttProject.php;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Argument managing singleton instance.
 * <pre>
 * I couldn't find any way to pass java object to php process.
 * Therefore, we need this class.
 * </pre>
 */
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
	/**
	 * set argument data by Java
	 * @param params objects to set
	 * @return key for this arguments
	 */
	public String setArgument(Object... params) {
		String key = Integer.toString(id ++);
		arguments.put(key, params);
		return key;
	}
	/**
	 * get argument data in PHP
	 * @param key for arguments
	 * @return objects to set
	 */
	public Object[] getArgument(String key) {
		return arguments.get(key);
	}
}
