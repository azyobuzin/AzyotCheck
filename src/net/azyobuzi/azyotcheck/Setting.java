package net.azyobuzi.azyotcheck;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Setting {
	private static SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(AzyotCheckApplication.getInstance());
	private static SharedPreferences.Editor ed = sp.edit();

	public static String getAccessToken() {
		return sp.getString("accessToken", null);
	}

	public static void setAccessToken(String value) {
		ed.putString("accessToken", value);
		ed.commit();
	}

	public static Boolean getShareWithFriends() {
		return sp.getBoolean("shareWithFriends", true);
	}

	public static void setShareWithFriends(Boolean value) {
		ed.putBoolean("shareWithFriends", value);
		ed.commit();
	}

	public static Boolean getShareWithFacebook() {
		return sp.getBoolean("shareWithFacebook", true);
	}

	public static void setShareWithFacebook(Boolean value) {
		ed.putBoolean("shareWithFacebook", value);
		ed.commit();
	}

	public static Boolean getShareWithTwitter() {
		return sp.getBoolean("shareWithTwitter", true);
	}

	public static void setShareWithTwitter(Boolean value) {
		ed.putBoolean("shareWithTwitter", value);
		ed.commit();
	}
}