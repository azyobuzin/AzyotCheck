package net.azyobuzi.azyotcheck;

import com.actionbarsherlock.app.SherlockFragmentActivity;

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
		//TODO: 位置情報サービスが無効の時の処理
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
		locMng.requestLocationUpdates(provider, 30 * 1000, 5, this);
	}
	
	@Override
	protected void onPause() {
		locMng.removeUpdates(this);

		super.onPause();
	}
}
