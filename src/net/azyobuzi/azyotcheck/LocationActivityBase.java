package net.azyobuzi.azyotcheck;

import net.azyobuzi.azyotcheck.util.StringUtil;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public abstract class LocationActivityBase extends SherlockFragmentActivity implements LocationListener {
	public abstract void onLocationUpdate(Location location);
	
	private LocationManager locMng;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		locMng = (LocationManager)getSystemService(LOCATION_SERVICE);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		onLocationUpdate(location);
	}
	
	@Override
	public void onProviderDisabled(String provider) { }
	
	@Override
	public void onProviderEnabled(String provider) { }
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) { }
	
	@Override
	protected void onResume() {
		super.onResume();
		
		Criteria criteria = new Criteria();
		criteria.setBearingRequired(false);
		criteria.setSpeedRequired(false);
		String provider = locMng.getBestProvider(criteria, true);
		
		if (StringUtil.isNullOrEmpty(provider)) {
			new AlertDialog.Builder(this)
				.setTitle(android.R.string.dialog_alert_title)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setMessage(R.string.cant_get_location)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) { }
				})
				.show();
		} else {
			locMng.requestLocationUpdates(provider, 30 * 1000, 5, this);
		}
	}
	
	@Override
	protected void onPause() {
		locMng.removeUpdates(this);

		super.onPause();
	}
}
