package net.azyobuzi.azyotcheck;

import java.util.ArrayList;

import net.azyobuzi.azyotcheck.util.Action1;
import net.azyobuzi.azyotcheck.util.StringUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class StartPageActivity extends Activity implements LocationListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locMng = (LocationManager)this.getSystemService(LOCATION_SERVICE);

        setContentView(R.layout.start_page);
        refreshButtonsEnabled();

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
        	@Override
			public void onClick(View arg0) {
        		if (arg0.getId() == R.id.btn_search_with_place_name) {
        			final View inputView = LayoutInflater.from(StartPageActivity.this)
        					.inflate(R.layout.input_place_name_dialog, null);
        			new AlertDialog.Builder(StartPageActivity.this)
        				.setTitle(R.string.input_place_name)
        				.setView(inputView)
        				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								placeNameQuery = ((EditText)inputView.findViewById(R.id.txt_place_name)).getText().toString();
								getLocation();
							}
        				})
        				.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
        				})
        				.show();
        		} else {
        			getLocation();
        		}
			}
        };
        findViewById(R.id.btn_search_with_here).setOnClickListener(buttonClickListener);
        findViewById(R.id.btn_search_with_place_name).setOnClickListener(buttonClickListener);
    }

    private LocationManager locMng;
    private Location latestLocation;

    private ProgressDialog progressDialog;

    private Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	this.menu = menu;
    	getMenuInflater().inflate(R.menu.start_page_menu, menu);
    	refreshButtonsEnabled();
    	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
	    	case R.id.login_menu:
	    		if (StringUtil.isNullOrEmpty(Setting.getAccessToken())) {
		    		Intent intent = new Intent(Intent.ACTION_VIEW);
		    		intent.setData(Uri.parse(
		    			"https://ja.foursquare.com/oauth2/authorize?client_id=N5IJCYZHYENW5INXNARJVHHA0DFPHDBGTKGO5JPVFCGSL13P&response_type=token&redirect_uri=net.azyobuzi.azyotcheck://callback&display=touch"
		    		));
		    		startActivity(intent);
	    		} else {
	    			Setting.setAccessToken(null);
	    			refreshButtonsEnabled();
	    		}
	    		return true;
	    	case R.id.about_menu:
	    		new AlertDialog.Builder(this)
	    			.setIcon(android.R.drawable.ic_dialog_info)
	    			.setTitle(R.string.about)
	    			.setMessage(R.string.about_this_app)
	    			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					})
	    			.show();
	    		return true;
    		default:
    			return super.onOptionsItemSelected(item);
    	}
    }

    @Override
    protected void onNewIntent(Intent intent) {
    	Uri data = intent.getData();
    	if (data != null) {
			String url = data.toString();
			String tokenFlag = "#access_token=";
			int start = url.indexOf(tokenFlag);
			if (start > -1) {
				Setting.setAccessToken(url.substring(start + tokenFlag.length(), url.length()));
			}
			refreshButtonsEnabled();
		}
    }

    private void refreshButtonsEnabled() {
    	boolean enabled = !StringUtil.isNullOrEmpty(Setting.getAccessToken());
    	findViewById(R.id.btn_search_with_here).setEnabled(enabled);
    	findViewById(R.id.btn_search_with_place_name).setEnabled(enabled);

    	if (menu != null) {
	    	menu.findItem(R.id.login_menu).setTitle(
	    		enabled ? R.string.logout : R.string.login
	    	);
    	}
    }

    private String placeNameQuery;

    private void getLocation() {
    	Criteria criteria = new Criteria();
		criteria.setBearingRequired(false);
		criteria.setSpeedRequired(false);
		String provider = locMng.getBestProvider(criteria, true);
		if (provider == null) {
			new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(R.string.error)
				.setMessage(R.string.not_found_location_provider)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				})
				.show();
			return;
		}

    	progressDialog = new ProgressDialog(StartPageActivity.this);
		progressDialog.setMessage(getText(R.string.getting_location));
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
		progressDialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface arg) {
				locMng.removeUpdates(StartPageActivity.this);
				searchVenues();
			}
		});
		progressDialog.show();

		locMng.requestLocationUpdates(provider, 0, 0, StartPageActivity.this);
    }

	@Override
	public void onLocationChanged(Location location) {
		latestLocation = location;
		progressDialog.cancel();
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	private void searchVenues() {
		if (latestLocation == null) return;

		progressDialog = new ProgressDialog(StartPageActivity.this);
		progressDialog.setMessage(getText(R.string.searching_venues));
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(false);
		progressDialog.show();

		ArrayList<NameValuePair> reqQuery = new ArrayList<NameValuePair>();
		reqQuery.add(new BasicNameValuePair("ll", latestLocation.getLatitude() + "," + latestLocation.getLongitude()));
		reqQuery.add(new BasicNameValuePair("llAcc", String.valueOf(latestLocation.getAccuracy())));
		reqQuery.add(new BasicNameValuePair("alt", String.valueOf(latestLocation.getAltitude())));
		if (!StringUtil.isNullOrEmpty(placeNameQuery))
			reqQuery.add(new BasicNameValuePair("query", placeNameQuery));

		new FoursquareApi(Setting.getAccessToken()).searchVenues(reqQuery, new Action1<String>() {
			@Override
			public void Invoke(String arg) {
				progressDialog.cancel();

				if (arg.startsWith("Error: ")) {
					new AlertDialog.Builder(StartPageActivity.this)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(R.string.error)
						.setMessage(arg.substring("Error: ".length(), arg.length()))
						.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
						})
						.show();
					return;
				}

				Intent intent = new Intent(StartPageActivity.this, SearchResultActivity.class);
				intent.putExtra("result", arg);
				startActivity(intent);
			}
		});
	}
}