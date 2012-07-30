package net.azyobuzi.azyotcheck.foursquare;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import net.azyobuzi.azyotcheck.util.StringUtil;
import net.vvakame.util.jsonpullparser.JsonFormatException;

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
		
		for (int i = 0; i <= nameAndValue.length; i += 2) {
			ub.appendQueryParameter(nameAndValue[i], nameAndValue[i + 1]);
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
						} catch (IOException e) {
							e.printStackTrace();
						} catch (JsonFormatException e) {
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
