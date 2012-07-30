package net.azyobuzi.azyotcheck.foursquare;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;
import net.vvakame.util.jsonpullparser.util.JsonArray;

@JsonModel(treatUnknownKeyAsError = false)
public class Venue implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6314527886278817801L;
	
	@JsonKey
	String id;
	
	public String getId() {
		return id;
	}
	
	public void setId(String value) {
		id = value;
	}
	
	@JsonKey
	String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String value) {
		name = value;
	}
	
	@JsonKey
	Location location;
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location value) {
		location = value;
	}
	
	@JsonKey
	JsonArray categories;
	List<Category> formatedCategories;
	
	public JsonArray getCategories() {
		return categories;
	}
	
	public void setCategories(JsonArray value) {
		categories = value;
		
		try {
			formatedCategories = CategoryGen.getList(value.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JsonFormatException e) {
			e.printStackTrace();
		}
	}
	
	public List<Category> getFormatedCategories() {
		return formatedCategories;
	}
	
	@JsonKey
	boolean verified;
	
	public boolean getVerified() {
		return verified;
	}
	
	public void setVerified(boolean value) {
		verified = value;
	}
	
	@JsonKey
	VenueStats stats;
	
	public VenueStats getStats() {
		return stats;
	}
	
	public void setStats(VenueStats value) {
		stats = value;
	}
	
	@JsonKey
	String url;
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String value) {
		url = value;
	}
	
	@JsonKey
	BeenHere beenHere;
	
	public BeenHere getBeenHere() {
		return beenHere;
	}
	
	public void setBeenHere(BeenHere value) {
		beenHere = value;
	}
	
	@JsonKey
	HereNow hereNow;
	
	public HereNow getHereNow() {
		return hereNow;
	}
	
	public void setHereNow(HereNow value) {
		hereNow = value;
	}
}
