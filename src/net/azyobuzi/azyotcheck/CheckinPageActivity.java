package net.azyobuzi.azyotcheck;

import java.util.ArrayList;

import net.azyobuzi.azyotcheck.util.Action1;
import net.azyobuzi.azyotcheck.util.StringUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Toast;

public class CheckinPageActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT >= 11) {
	        ActionBar ab = getActionBar();
	        ab.setDisplayHomeAsUpEnabled(true);
	        ab.setTitle(getIntent().getStringExtra("name"));
        }

        setContentView(R.layout.checkin_page);

        ((CheckBox)findViewById(R.id.sw_share_with_friends)).setChecked(Setting.getShareWithFriends());
        ((CheckBox)findViewById(R.id.sw_share_with_facebook)).setChecked(Setting.getShareWithFacebook());
        ((CheckBox)findViewById(R.id.sw_share_with_twitter)).setChecked(Setting.getShareWithTwitter());
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.checkin_page_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			if (item.getItemId() == android.R.id.home) {
				finish();
				return true;
			}
		}

		switch (item.getItemId()) {
			case R.id.checkin_menu:
				final ProgressDialog dialog = new ProgressDialog(this);
				dialog.setMessage(getText(R.string.being_checked_in));
				dialog.setIndeterminate(true);
				dialog.setCancelable(false);
				dialog.show();

				boolean shareWithFriends = ((CheckBox)findViewById(R.id.sw_share_with_friends)).isChecked();
				boolean shareWithFacebook = ((CheckBox)findViewById(R.id.sw_share_with_facebook)).isChecked();
				boolean shareWithTwitter = ((CheckBox)findViewById(R.id.sw_share_with_twitter)).isChecked();

				Setting.setShareWithFriends(shareWithFriends);
				Setting.setShareWithFacebook(shareWithFacebook);
				Setting.setShareWithTwitter(shareWithTwitter);

				ArrayList<NameValuePair> reqQuery = new ArrayList<NameValuePair>();
				reqQuery.add(new BasicNameValuePair("venueId", getIntent().getStringExtra("id")));
				String shout = ((EditText)findViewById(R.id.txt_shout)).getText().toString();
				if (!StringUtil.isNullOrEmpty(shout))
					reqQuery.add(new BasicNameValuePair("shout", shout));
				String broadcast = shareWithFriends ? "public" : "private";
				if (shareWithFacebook)
					broadcast += ",facebook";
				if (shareWithTwitter)
					broadcast += ",twitter";
				reqQuery.add(new BasicNameValuePair("broadcast", broadcast));

				new FoursquareApi(Setting.getAccessToken())
					.addCheckin(reqQuery, new Action1<String>() {
						@Override
						public void Invoke(String arg) {
							dialog.cancel();
							if (arg.startsWith("Error: ")) {
								new AlertDialog.Builder(CheckinPageActivity.this)
									.setIcon(android.R.drawable.ic_dialog_alert)
									.setTitle(R.string.error)
									.setMessage(arg.substring("Error: ".length(), arg.length()))
									.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
										}
									})
									.show();
							} else {
								Toast.makeText(CheckinPageActivity.this, R.string.completed, Toast.LENGTH_SHORT).show();
								finish();
							}
						}
					});
				return true;
			case R.id.show_map_menu:
				try {
					String lat = getIntent().getStringExtra("lat");
					String lng = getIntent().getStringExtra("lng");
					Intent intent = new Intent(Intent.ACTION_VIEW).setData(
						Uri.parse(
							"geo:" + lat + "," + lng + "?q=" + lat + "," + lng
						)
					);
					startActivity(intent);
				} catch (Exception ex) {
					Toast.makeText(this, R.string.not_found_show_map_app, Toast.LENGTH_LONG).show();
				}
				return true;
            default:
                return super.onOptionsItemSelected(item);
		}
	}
}
