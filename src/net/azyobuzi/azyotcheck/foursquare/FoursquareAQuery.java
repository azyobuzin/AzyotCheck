package net.azyobuzi.azyotcheck.foursquare;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.androidquery.AbstractAQuery;

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

	//TODO
}
