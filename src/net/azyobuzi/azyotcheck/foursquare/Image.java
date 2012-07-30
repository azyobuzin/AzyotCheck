package net.azyobuzi.azyotcheck.foursquare;

import java.io.Serializable;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel(treatUnknownKeyAsError = false)
public class Image implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2332669014140717369L;
	
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
