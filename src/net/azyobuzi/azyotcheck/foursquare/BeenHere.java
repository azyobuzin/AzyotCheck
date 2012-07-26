package net.azyobuzi.azyotcheck.foursquare;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel(treatUnknownKeyAsError = false)
public class BeenHere {
	@JsonKey
	int count;
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int value) {
		count = value;
	}
	
	@JsonKey
	boolean marked;
	
	public boolean getMarked() {
		return marked;
	}
	
	public void setMarked(boolean value) {
		marked = value;
	}
}
