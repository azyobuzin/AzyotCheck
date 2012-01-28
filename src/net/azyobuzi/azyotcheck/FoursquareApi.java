package net.azyobuzi.azyotcheck;

import java.util.List;

import net.azyobuzi.azyotcheck.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class FoursquareApi {
	private class HttpGetTask extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... arg0) {
			try {
				HttpClient hc = new DefaultHttpClient();
				HttpGet req = new HttpGet(arg0[0]);
				HttpResponse res = hc.execute(req);
				int statusCode = res.getStatusLine().getStatusCode();
				String result = new String(EntityUtils.toByteArray(res.getEntity()), "UTF-8");
				Log.v("net.azyobuzi.azyotcheck", result);
				if (statusCode == 200) {
					return result;
				} else {
					return "Error: " + result;
				}
			} catch (Exception ex) {
				return "Error: " + ex.getMessage();
			}
		}

		public Action1<String> completedHandler = null;

		@Override
		protected void onPostExecute(String result) {
			if (completedHandler != null)
				completedHandler.Invoke(result);
		}
	}

	private class HttpPostTask extends AsyncTask<Tuple2<String, List<NameValuePair>>, String, String> {
		@Override
		protected String doInBackground(Tuple2<String, List<NameValuePair>>... arg0) {
			try {
				HttpClient hc = new DefaultHttpClient();
				HttpPost req = new HttpPost(arg0[0].Item1);
				req.setEntity(new UrlEncodedFormEntity(arg0[0].Item2, "UTF-8"));
				HttpResponse res = hc.execute(req);
				int statusCode = res.getStatusLine().getStatusCode();
				String result = new String(EntityUtils.toByteArray(res.getEntity()), "UTF-8");
				Log.v("net.azyobuzi.azyotcheck", result);
				if (statusCode == 200) {
					return result;
				} else {
					return "Error: " + result;
				}
			} catch (Exception ex) {
				return "Error: " + ex.getMessage();
			}
		}

		public Action1<String> completedHandler = null;

		@Override
		protected void onPostExecute(String result) {
			if (completedHandler != null)
				completedHandler.Invoke(result);
		}
	}

	public FoursquareApi(String oauthToken) {
		this.oauthToken = oauthToken;
	}

	private final static String version = "20120126";

	private String oauthToken;

	public void searchVenues(List<NameValuePair> arg, final Action1<String> callback) {
		String query = "?oauth_token=" + this.oauthToken + "&v=" + version;
		for (NameValuePair item : arg) {
			query += "&" + item.getName() + "=" + Uri.encode(item.getValue());
		}

		HttpGetTask task = new HttpGetTask();
		task.completedHandler = new Action1<String>() {
			@Override
			public void Invoke(String arg) {
				/*if (arg.startsWith("Error: ")) {
					callback.Invoke(null, arg);
					return;
				}

				try {
					ArrayList<Venue> re = new ArrayList<Venue>();
					JSONArray venues = new JSONObject(arg)
						.getJSONObject("response")
						.getJSONArray("venues");
					for (int index = 0; index < venues.length(); index++) {
						re.add(Venue.fromJson(venues.getJSONObject(index)));
					}
					callback.Invoke(re, null);
				} catch(Exception ex) {
					callback.Invoke(null, "Error: " + ex.getMessage());
				}*/
				callback.Invoke(arg);
			}
		};
		task.execute("https://api.foursquare.com/v2/venues/search" + query);
	}

	@SuppressWarnings("unchecked")
	public void addCheckin(List<NameValuePair> arg, final Action1<String> callback) {
		arg.add(new BasicNameValuePair("oauth_token", oauthToken));
		arg.add(new BasicNameValuePair("v", version));

		HttpPostTask task = new HttpPostTask();
		task.completedHandler = new Action1<String>() {
			@Override
			public void Invoke(String arg) {
				callback.Invoke(arg);
			}
		};
		task.execute(new Tuple2<String, List<NameValuePair>>("https://api.foursquare.com/v2/checkins/add", arg));
	}
}