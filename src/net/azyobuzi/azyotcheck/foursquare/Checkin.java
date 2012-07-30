package net.azyobuzi.azyotcheck.foursquare;

import java.io.Serializable;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel(treatUnknownKeyAsError = false)
public class Checkin implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -626999591516840113L;
	
	@JsonKey
	String id;
	
	public String getId() {
		return id;
	}
	
	public void setId(String value) {
		id = value;
	}
	
	@JsonKey
	String type;
	
	public String getType() {
		return type;
	}
	
	public void setType(String value) {
		type = value;
	}
	
	@JsonKey
	Venue venue;
	
	public Venue getVenue() {
		return venue;
	}
	
	public void setVenue(Venue value) {
		venue = value;
	}
	
	@JsonKey
	String shout;
	
	public String getShout() {
		return shout;
	}
	
	public void setShout(String value) {
		shout = value;
	}
	
	@JsonKey
	Source source;
	
	public Source getSource() {
		return source;
	}
	
	public void setSource(Source value) {
		source = value;
	}
}
