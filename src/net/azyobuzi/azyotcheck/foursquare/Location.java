package net.azyobuzi.azyotcheck.foursquare;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel(treatUnknownKeyAsError = false)
public class Location {
	@JsonKey
	String address;
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String value) {
		address = value;
	}
	
	@JsonKey
	String crossStreet;
	
	public String getCrossStreet() {
		return crossStreet;
	}
	
	public void setCrossStreet(String value) {
		crossStreet = value;
	}
	
	@JsonKey
	String city;
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String value) {
		city = value;
	}
	
	@JsonKey
	String state;
	
	public String getState() {
		return state;
	}
	
	public void setState(String value) {
		state = value;
	}
	
	@JsonKey
	String postalCode;
	
	public String getPostalCode() {
		return postalCode;
	}
	
	public void setPostalCode(String value) {
		postalCode = value;
	}
	
	@JsonKey
	String country;
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String value) {
		country = value;
	}
	
	@JsonKey
	double lat;
	
	public double getLat() {
		return lat;
	}
	
	public void setLat(double value) {
		lat = value;
	}
	
	@JsonKey
	double lng;
	
	public double getLng() {
		return lng;
	}
	
	public void setLng(double value) {
		lng = value;
	}
	
	@JsonKey
	double distance;
	
	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double value) {
		distance = value;
	}
}
