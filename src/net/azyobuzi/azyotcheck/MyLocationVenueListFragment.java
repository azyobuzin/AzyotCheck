package net.azyobuzi.azyotcheck;

import java.util.List;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import net.azyobuzi.azyotcheck.foursquare.FoursquareResponse;
import net.azyobuzi.azyotcheck.foursquare.Venue;

public class MyLocationVenueListFragment extends VenueListFragment {
	@Override
	public CharSequence getTitle() {
		return AzyotCheckApplication.getInstance().getText(R.string.my_location);
	}
	
	@Override
	public boolean useLocation() {
		return true;
	}

	@Override
	public void loadInBackground() {
		aq.foursquareSearchVenue(getLocation(), "", 50, new AjaxCallback<FoursquareResponse<List<Venue>>>() {
			@Override
			public void callback(String url, FoursquareResponse<List<Venue>> object, AjaxStatus status) {
				loadFinished(object.content);
			}
		});
	}

}
