package net.azyobuzi.azyotcheck;

import java.util.List;

import net.azyobuzi.azyotcheck.foursquare.FoursquareResponse;
import net.azyobuzi.azyotcheck.foursquare.Venue;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import android.os.Bundle;

public class SearchVenueListFragment extends VenueListFragment {
	private static final String QUERY = "net.azyobuzi.azyotcheck.SearchVenueListFragment.QUERY";
	
	public static SearchVenueListFragment newInstance(String query) {
		SearchVenueListFragment re = new SearchVenueListFragment();
		re.query = query;
		return re;
	}
	
	private String query;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null && savedInstanceState.containsKey(QUERY)) {
			query = savedInstanceState.getString(QUERY);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putString(QUERY, query);
		
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public CharSequence getTitle() {
		return query;
	}

	@Override
	public boolean useLocation() {
		return true;
	}

	@Override
	public void loadInBackground() {
		aq.foursquareSearchVenue(getLocation(), query, 50, new AjaxCallback<FoursquareResponse<List<Venue>>>() {
			@Override
			public void callback(String url, FoursquareResponse<List<Venue>> object, AjaxStatus status) {
				loadFinished(object.content);
			}
		});
	}

}
