package net.azyobuzi.azyotcheck;

import android.app.Application;

public class AzyotCheckApplication extends Application {
	public AzyotCheckApplication() {
		super();
		instance = this;
	}

	private static AzyotCheckApplication instance = null;
	public static AzyotCheckApplication getInstance() {
		return instance;
	}
}
