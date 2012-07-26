package net.azyobuzi.azyotcheck.foursquare;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;
import net.vvakame.util.jsonpullparser.util.JsonArray;

@JsonModel(treatUnknownKeyAsError = false)
public class HereNow {
	@JsonKey
	int count;
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int value) {
		count = value;
	}
	
	@JsonKey
	JsonArray groups;
	
	public JsonArray getGroups() {
		return groups;
	}
	
	public void setGroups(JsonArray value) {
		groups = value;
	}
}
