package net.azyobuzi.azyotcheck;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class AuthorizeActivity extends Activity {
	public static void startAuth() {
		AzyotCheckApplication.getInstance().startActivity(
			new Intent(Intent.ACTION_VIEW)
			.setData(Uri.parse("https://ja.foursquare.com/oauth2/authorize?client_id=N5IJCYZHYENW5INXNARJVHHA0DFPHDBGTKGO5JPVFCGSL13P&response_type=token&redirect_uri=net.azyobuzi.azyotcheck://callback&display=touch"))
			.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Uri data = getIntent().getData();
    	if (data != null) {
			String url = data.toString();
			String tokenFlag = "#access_token=";
			int start = url.indexOf(tokenFlag);
			if (start > -1) {
				Setting.setAccessToken(url.substring(start + tokenFlag.length(), url.length()));
			}
		}
    	
    	finish();
	}
}
