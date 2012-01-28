package net.azyobuzi.azyotcheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.azyobuzi.azyotcheck.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SearchResultActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT >= 11) {
        	getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.search_result_page);

        ListView lv = (ListView)findViewById(R.id.lv_search_result);

        try {
        	JSONArray venuesJsonArray =
        		new JSONObject(getIntent().getStringExtra("result"))
        			.getJSONObject("response")
        			.getJSONArray("venues");
        	for (int index = 0; index < venuesJsonArray.length(); index++) {
        		JSONObject item = venuesJsonArray.getJSONObject(index);
        		Map<String, String> map = new HashMap<String, String>();
        		map.put("id", item.getString("id"));
        		map.put("name", item.getString("name"));
        		JSONObject location = item.getJSONObject("location");
        		ArrayList<CharSequence> addressList = new ArrayList<CharSequence>();
        		if (location.has("state"))
        			addressList.add(location.getString("state"));
        		if (location.has("city"))
        			addressList.add(location.getString("city"));
        		if (location.has("address"))
        			addressList.add(location.getString("address"));
        		map.put("address", StringUtil.join(", ", addressList));
        		map.put("lat", String.valueOf(location.getDouble("lat")));
        		map.put("lng", String.valueOf(location.getDouble("lng")));
        		venues.add(map);
        	}

        	lv.setAdapter(new SimpleAdapter(
        		this,
        		venues,
        		R.layout.venue_item,
        		new String[] { "name", "address" },
        		new int[] { R.id.tv_venue_name, R.id.tv_address }
        	));
        } catch (Exception ex) {
        	new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(R.string.error)
				.setMessage(ex.getMessage())
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				})
				.show();
        }

        lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				@SuppressWarnings("unchecked")
				Map<String, String> item = (Map<String, String>)parent.getItemAtPosition(position);
				Intent intent = new Intent(SearchResultActivity.this, CheckinPageActivity.class)
					.putExtra("id", item.get("id"))
					.putExtra("name", item.get("name"))
					.putExtra("lat", item.get("lat"))
					.putExtra("lng", item.get("lng"));
				startActivity(intent);
			}
        });
	}

	private final List<Map<String, String>> venues = new ArrayList<Map<String, String>>();

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			if (item.getItemId() == android.R.id.home) {
				startActivity(new Intent(this, StartPageActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				return true;
			}
		}

		return super.onOptionsItemSelected(item);
	}
}
