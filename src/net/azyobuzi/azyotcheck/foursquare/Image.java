package net.azyobuzi.azyotcheck.foursquare;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel(treatUnknownKeyAsError = false)
public class Image {
	@JsonKey
	String prefix;
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setPrefix(String value) {
		prefix = value;
	}
	
	@JsonKey
	String suffix;
	
	public String getSuffix() {
		return suffix;
	}
	
	public void setSuffix(String value) {
		suffix = value;
	}
}
