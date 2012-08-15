package net.azyobuzi.azyotcheck;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import net.azyobuzi.azyotcheck.foursquare.Checkin;
import net.azyobuzi.azyotcheck.foursquare.FoursquareAQuery;
import net.azyobuzi.azyotcheck.foursquare.FoursquareResponse;
import net.azyobuzi.azyotcheck.foursquare.User;
import net.azyobuzi.azyotcheck.util.StringUtil;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends LocationActivityBase {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * sections. We use a {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will
     * keep every loaded fragment in memory. If this becomes too memory intensive, it may be best
     * to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    
    private FoursquareAQuery aq;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        aq = new FoursquareAQuery(this);
        
        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);

        if (StringUtil.isNullOrEmpty(Setting.getAccessToken())) {
        	AuthorizeActivity.startAuth();
        }
        
        //test
        /*aq.oauthToken(Setting.getAccessToken())
        	.foursquareGetSelfCheckins(2, new AjaxCallback<FoursquareResponse<List<Checkin>>>() {
	        	@Override
	        	public void callback(String url, FoursquareResponse<List<Checkin>> object, AjaxStatus status) {
	        		int dummy = 0;
	        	}
	        });*/
    }
    
    private Location currentLocation;
    
    @Override
    public void onLocationUpdate(Location location) {
    	currentLocation = location;
    	for (VenueListFragment f : mSectionsPagerAdapter.fragments) {
    		f.setLocation(location);
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getSupportMenuInflater().inflate(R.menu.activity_main, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.menu_activity_main_search:
    			search();
    			return true;
    		default:
    	    	return super.onOptionsItemSelected(item);
    	}
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	outState = new Bundle();
    	super.onSaveInstanceState(outState);
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
    	if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_SEARCH) {
    		search();
    		return true;
    	}
    	
    	return super.dispatchKeyEvent(event);
    }
    
    private void search() {
    	final EditText editText = new EditText(this);
    	new AlertDialog.Builder(this)
    		.setTitle(android.R.string.search_go)
    		.setView(editText)
    		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					SearchVenueListFragment f = SearchVenueListFragment.newInstance(editText.getText().toString());
					mSectionsPagerAdapter.fragments.add(f);
					mSectionsPagerAdapter.notifyDataSetChanged();
					mViewPager.setCurrentItem(mSectionsPagerAdapter.getCount() - 1, true);
					
					if (currentLocation != null)
						f.setLocation(currentLocation);
				}
			})
			.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) { }
			})
			.show();
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {//TODO:改名
    	public final ArrayList<VenueListFragment> fragments = new ArrayList<VenueListFragment>();
    	
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            
            fragments.add(new HistoryVenueListFragment());
            fragments.add(new MyLocationVenueListFragment());
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            VenueListFragment f = fragments.get(position);
            return f != null ? f.getTitle() : null;
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    /*public static class DummySectionFragment extends Fragment {
        public DummySectionFragment() {
        }

        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            Bundle args = getArguments();
            textView.setText(Integer.toString(args.getInt(ARG_SECTION_NUMBER)));
            return textView;
        }
    }*/
}
