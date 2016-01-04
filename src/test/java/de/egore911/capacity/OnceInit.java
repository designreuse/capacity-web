package de.egore911.capacity;

import de.egore911.capacity.util.listener.StartupListener;

public class OnceInit {

	private static boolean once = false;
	
	public static void init() {
		if (!once) {
			new StartupListener().contextInitialized(null);
			once = true;
		}
	}
}
