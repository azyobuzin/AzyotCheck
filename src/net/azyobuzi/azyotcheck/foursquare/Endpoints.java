package net.azyobuzi.azyotcheck.foursquare;

public class Endpoints {
	private Endpoints() { }
	
	public static final String V = "20120725";
	
	public static final String SELF_INFORMATION = "https://api.foursquare.com/v2/users/self";
	public static final String SEARCH_VENUES = "https://api.foursquare.com/v2/venues/search";
	public static final String SELF_CHECKINS = "https://api.foursquare.com/v2/users/self/checkins";
	public static final String CREATE_CHECKIN = "https://api.foursquare.com/v2/checkins/add";
	public static final String ADD_PHOTO = "https://api.foursquare.com/v2/photos/add";
}
