package net.azyobuzi.azyotcheck.foursquare;

import java.io.Serializable;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel(treatUnknownKeyAsError = false)
public class Source implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4153148452711132778L;
	
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
