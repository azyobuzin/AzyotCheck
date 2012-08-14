package net.azyobuzi.azyotcheck;

import java.util.ArrayList;
import java.util.List;

import net.azyobuzi.azyotcheck.foursquare.Checkin;
import net.azyobuzi.azyotcheck.foursquare.FoursquareResponse;
import net.azyobuzi.azyotcheck.foursquare.Venue;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

public class HistoryVenueListFragment extends VenueListFragment {

	@Override
	public CharSequence getTitle() {
		return AzyotCheckApplication.getInstance().getText(R.string.history);
	}

	@Override
	public boolean useLocation() {
		return false;
	}

	@Override
	public void loadInBackground() {
		aq.foursquareGetSelfCheckins(50, new AjaxCallback<FoursquareResponse<List<Checkin>>>() {
			@Override
			public void callback(String url, FoursquareResponse<List<Checkin>> object, AjaxStatus status) {
				if (object == null) {
					loadFinished(null);
					return;
				}
				
				ArrayList<Venue> result = new ArrayList<Venue>();
				for (Checkin checkin : object.content) {
					result.add(checkin.getVenue());
				}
				
				loadFinished(result);
			}
		});
	}

}
