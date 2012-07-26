package net.azyobuzi.azyotcheck.foursquare;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel(treatUnknownKeyAsError = false)
public class Source {
	@JsonKey
	String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String value) {
		name = value;
	}
	
	@JsonKey
	String url;
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String value) {
		url = value;
	}
}
