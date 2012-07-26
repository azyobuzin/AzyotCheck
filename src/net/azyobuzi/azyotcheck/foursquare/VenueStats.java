package net.azyobuzi.azyotcheck.foursquare;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel(treatUnknownKeyAsError = false)
public class VenueStats {
	@JsonKey
	int checkinsCount;
	
	public int getCheckinsCount() {
		return checkinsCount;
	}
	
	public void setCheckinsCount(int value) {
		checkinsCount = value;
	}
	
	@JsonKey
	int usersCount;
	
	public int getUsersCount() {
		return usersCount;
	}
	
	public void setUsersCount(int value) {
		usersCount = value;
	}
	
	@JsonKey
	int tipCount;
	
	public int getTipCount() {
		return tipCount;
	}
	
	public void setTipCount(int value) {
		tipCount = value;
	}
}
