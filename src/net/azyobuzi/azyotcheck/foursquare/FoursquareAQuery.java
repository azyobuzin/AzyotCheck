package net.azyobuzi.azyotcheck.foursquare;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.http.entity.FileEntity;

import net.azyobuzi.azyotcheck.util.StringUtil;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.androidquery.AbstractAQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

public class FoursquareAQuery extends AbstractAQuery<FoursquareAQuery> {

	public FoursquareAQuery(Activity act) {
		super(act);
	}

	public FoursquareAQuery(View root) {
		super(root);
	}

	public FoursquareAQuery(Context context) {
		super(context);
	}

	public FoursquareAQuery(Activity act, View root) {
		super(act, root);
	}
	
	private String m_oauthToken;
	
	public FoursquareAQuery oauthToken(String value) {
		m_oauthToken = value;
		return this;
	}
	
	public String getOAuthToken() {
		return m_oauthToken;
	}
	
	private String createUri(String base, String... nameAndValue) {
		Uri.Builder ub = Uri.parse(base).buildUpon();
		
		for (int i = 0; i < nameAndValue.length; i += 2) {
			String value = nameAndValue[i + 1];
			value = value != null ? value : "";
			ub.appendQueryParameter(nameAndValue[i], value);
		}
		
		return ub.build().toString();
	}
	
	public FoursquareAQuery foursquareGetSelfInformation(AjaxCallback<FoursquareResponse<User>> callback) {
		return ajax(
			createUri(Endpoints.SELF_INFORMATION, "v", Endpoints.V, "oauth_token", getOAuthToken()),
			String.class,
			new WrappedAjaxCallback<String, FoursquareResponse<User>>(callback) {
				@Override
				public void callback(String url, String object, AjaxStatus status) {
					FoursquareResponse<User> result = null;
					
					String msg = StringUtil.isNullOrEmpty(object) ? status.getError() : object;
					if (!StringUtil.isNullOrEmpty(msg)) {
						try {
							Response res = ResponseGen.get(msg);
							User user = null;
							if (res.response != null)
								user = UserGen.get(res.response.getJsonHashOrNull("user").toString());
							
							result = new FoursquareResponse<User>();
							result.meta = res.meta;
							result.content = user;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					outCallback.callback(url, result, status);
				}
			}
		);
	}
	
	public FoursquareAQuery foursquareSearchVenue(android.location.Location location, String query, int limit, AjaxCallback<FoursquareResponse<List<Venue>>> callback) {
		return ajax(
			createUri(
				Endpoints.SEARCH_VENUES,
				"v", Endpoints.V,
				"oauth_token", getOAuthToken(),
				"ll", location.getLatitude() + "," + location.getLongitude(),
				"llAcc", String.valueOf(location.getAccuracy()),
				"query", query,
				"limit", String.valueOf(limit)
			),
			String.class,
			new WrappedAjaxCallback<String, FoursquareResponse<List<Venue>>>(callback) {
				@Override
				public void callback(String url, String object, AjaxStatus status) {
					FoursquareResponse<List<Venue>> result = null;
					
					String msg = StringUtil.isNullOrEmpty(object) ? status.getError() : object;
					if (!StringUtil.isNullOrEmpty(msg)) {
						try {
							Response res = ResponseGen.get(msg);
							List<Venue> venues = null;
							if (res.response != null)
								venues = VenueGen.getList(res.response.getJsonArrayOrNull("venues").toString());
							
							result = new FoursquareResponse<List<Venue>>();
							result.meta = res.meta;
							result.content = venues;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					outCallback.callback(url, result, status);
				}
			}
		);
	}
	
	public FoursquareAQuery foursquareGetSelfCheckins(int limit, AjaxCallback<FoursquareResponse<List<Checkin>>> callback) {
		return ajax(
			createUri(
				Endpoints.SELF_CHECKINS,
				"v", Endpoints.V,
				"oauth_token", getOAuthToken(),
				"limit", String.valueOf(limit)
			),
			String.class,
			new WrappedAjaxCallback<String, FoursquareResponse<List<Checkin>>>(callback) {
				@Override
				public void callback(String url, String object, AjaxStatus status) {
					FoursquareResponse<List<Checkin>> result = null;
					
					String msg = StringUtil.isNullOrEmpty(object) ? status.getError() : object;
					if (!StringUtil.isNullOrEmpty(msg)) {
						try {
							Response res = ResponseGen.get(msg);
							List<Checkin> items = null;
							if (res.response != null)
								items = CheckinGen.getList(
									res.response
										.getJsonHashOrNull("checkins")
										.getJsonArrayOrNull("items")
										.toString()
								);
							
							result = new FoursquareResponse<List<Checkin>>();
							result.meta = res.meta;
							result.content = items;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					outCallback.callback(url, result, status);
				}
			}
		);
	}
	
	public FoursquareAQuery foursquareCreateCheckin(String venueId, String shout, boolean sharePublic, boolean facebook, boolean twitter, AjaxCallback<FoursquareResponse<Checkin>> callback) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("venueId", venueId);
		params.put("shout", shout);
		String broadcast = sharePublic ? "public" : "private";
		broadcast = facebook ? broadcast + ",facebook" : broadcast;
		broadcast = twitter ? broadcast + ",twitter" : broadcast;
		params.put("broadcast", broadcast);
		
		return ajax(
			createUri(Endpoints.CREATE_CHECKIN, "v", Endpoints.V, "oauth_token", getOAuthToken()),
			params,
			String.class,
			new WrappedAjaxCallback<String, FoursquareResponse<Checkin>>(callback) {
				@Override
				public void callback(String url, String object, AjaxStatus status) {
					FoursquareResponse<Checkin> result = null;
					
					String msg = StringUtil.isNullOrEmpty(object) ? status.getError() : object;
					if (!StringUtil.isNullOrEmpty(msg)) {
						try {
							Response res = ResponseGen.get(msg);
							Checkin checkin = null;
							if (res.response != null)
								checkin = CheckinGen.get(res.response.getJsonHashOrNull("checkin").toString());
							
							result = new FoursquareResponse<Checkin>();
							result.meta = res.meta;
							result.content = checkin;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					outCallback.callback(url, result, status);
				}
			}
		);
	}
	
	public FoursquareAQuery foursquareAddPhoto(File photo, String checkinId, boolean sharePublic, boolean facebook, boolean twitter, AjaxCallback<FoursquareResponse<Photo>> callback) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(POST_ENTITY, new FileEntity(photo, "image/jpeg"));
		
		return ajax(
			createUri(
				Endpoints.CREATE_CHECKIN,
				"v", Endpoints.V,
				"oauth_token", getOAuthToken(),
				"checkinId", checkinId,
				"broadcast", facebook && twitter
					? "twitter,facebook"
					: facebook
						? "facebook"
						: twitter
							? "twitter"
							: null
			),
			params,
			String.class,
			new WrappedAjaxCallback<String, FoursquareResponse<Photo>>(callback) {
				@Override
				public void callback(String url, String object, AjaxStatus status) {
					FoursquareResponse<Photo> result = null;
					
					String msg = StringUtil.isNullOrEmpty(object) ? status.getError() : object;
					if (!StringUtil.isNullOrEmpty(msg)) {
						try {
							Response res = ResponseGen.get(msg);
							Photo photo = null;
							if (res.response != null)
								photo = PhotoGen.get(res.response.getJsonHashOrNull("photo").toString());
							
							result = new FoursquareResponse<Photo>();
							result.meta = res.meta;
							result.content = photo;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					outCallback.callback(url, result, status);
				}
			}
		);
	}
	
	private abstract class WrappedAjaxCallback<In, Out> extends AjaxCallback<In> {
		public WrappedAjaxCallback(AjaxCallback<Out> outCallback) {
			this.outCallback = outCallback;
		}
		
		protected AjaxCallback<Out> outCallback;
		
		@Override
		public abstract void callback(String url, In object, AjaxStatus status);
	}
}
