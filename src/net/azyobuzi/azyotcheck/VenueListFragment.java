package net.azyobuzi.azyotcheck;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockListFragment;
import com.androidquery.AQuery;

import net.azyobuzi.azyotcheck.foursquare.Category;
import net.azyobuzi.azyotcheck.foursquare.FoursquareAQuery;
import net.azyobuzi.azyotcheck.foursquare.Image;
import net.azyobuzi.azyotcheck.foursquare.Location;
import net.azyobuzi.azyotcheck.foursquare.Venue;
import net.azyobuzi.azyotcheck.util.StringUtil;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class VenueListFragment extends SherlockListFragment {
	protected FoursquareAQuery aq;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_fragment_content, null);
		aq = new FoursquareAQuery(view);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		adapter = new VenueAdapter();
		setListAdapter(adapter);
		
		if (!useLocation())
			startLoad();
	}
	
	private VenueAdapter adapter;
	
	private Handler h = new Handler();
	
	public abstract CharSequence getTitle();
	
	public abstract boolean useLocation();
	
	private boolean loadStarted = false;
	
	private android.location.Location location;
	
	public android.location.Location getLocation() {
		return location;
	}
	
	public void setLocation(android.location.Location value) {
		location = value;
		
		if (!loadStarted)
			startLoad();
	}
	
	public void startLoad() {
		loadStarted = true;
		
		aq.id(R.id.pb_list_fragment_content).visible();
		aq.id(R.id.tv_list_fragment_content_message).gone();
		
		adapter.getVenues().clear();
		adapter.notifyDataSetChanged();
		
		aq.oauthToken(Setting.getAccessToken());
		
		loadInBackground();
	}
	
	public abstract void loadInBackground();
	
	public void loadFinished(final List<Venue> result) {
		h.post(new Runnable() {
			@Override
			public void run() {
				aq.id(R.id.pb_list_fragment_content).gone();
				
				if (result == null || result.isEmpty()) {
					aq.id(R.id.tv_list_fragment_content_message).visible();
				} else {
					adapter.getVenues().addAll(result);
					adapter.notifyDataSetChanged();
				}
			}
		});
	}

	private class VenueAdapter extends BaseAdapter {
		private final ArrayList<Venue> venues = new ArrayList<Venue>();
		public ArrayList<Venue> getVenues() {
			return venues;
		}
		
		@Override
		public int getCount() {
			return venues.size();
		}

		public Venue getVenue(int index) {
			return venues.get(index);
		}
		
		@Override
		public Object getItem(int arg0) {
			return getVenue(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			View view = arg1 != null
				? arg1
				: getActivity().getLayoutInflater().inflate(R.layout.venue_item, null);
			
			ViewHolder vh = (ViewHolder)view.getTag();
			if (vh == null) {
				vh = new ViewHolder();
				AQuery aq = new AQuery(view);
				vh.category = aq.id(R.id.iv_venue_item_category).getImageView();
				vh.name = aq.id(R.id.tv_venue_item_name).getTextView();
				vh.location = aq.id(R.id.tv_venue_item_location).getTextView();
			}
			
			Venue item = getVenue(arg0);
			
			String categoryImg = null;
			for (Category c : item.getFormatedCategories()) {
				if (c.getPrimary()) {
					Image icon = c.getIcon();
					categoryImg = icon.getPrefix() + "64" + icon.getSuffix();
					break;
				}
			}
			new AQuery(vh.category).image(categoryImg, true, true);

			new AQuery(vh.name).text(item.getName());
			
			ArrayList<CharSequence> addressList = new ArrayList<CharSequence>();
			Location loc = item.getLocation();
			if (!StringUtil.isNullOrEmpty(loc.getAddress()))
				addressList.add(loc.getAddress());
			if (!StringUtil.isNullOrEmpty(loc.getCity()))
				addressList.add(loc.getCity());
			if (!StringUtil.isNullOrEmpty(loc.getState()))
				addressList.add(loc.getState());
			new AQuery(vh.location).text(StringUtil.join(", ", addressList));
			
			return view;
		}
	}
	
	private static class ViewHolder {
		public ImageView category;
		public TextView name;
		public TextView location;
	}
}
