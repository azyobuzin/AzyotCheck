package net.azyobuzi.azyotcheck.foursquare;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel(treatUnknownKeyAsError = false)
public class User {
	@JsonKey
	String id;
	
	public String getId() {
		return id;
	}
	
	public void setId(String value) {
		id = value;
	}
	
	@JsonKey
	String firstName;
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String value) {
		firstName = value;
	}
	
	@JsonKey
	String lastName;
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String value) {
		lastName = value;
	}
	
	@JsonKey
	String homeCity;
	
	public String getHomeCity() {
		return homeCity;
	}
	
	public void setHomeCity(String value) {
		homeCity = value;
	}
	
	@JsonKey
	Image photo;
	
	public Image getPhoto() {
		return photo;
	}
	
	public void setPhoto(Image value) {
		photo = value;
	}
	
	@JsonKey
	String gender;
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String value) {
		gender = value;
	}
	
	//大分省略
}
