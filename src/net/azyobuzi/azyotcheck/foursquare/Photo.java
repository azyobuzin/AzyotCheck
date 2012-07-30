package net.azyobuzi.azyotcheck.foursquare;

import java.io.Serializable;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel(treatUnknownKeyAsError = false)
public class Photo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6462491547770473725L;
	
	@JsonKey
	String id;
	
	public String getId() {
		return id;
	}
	
	public void setId(String value) {
		id = value;
	}
	
	@JsonKey
	long createdAt;
	
	public long getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(long value) {
		createdAt = value;
	}
	
	@JsonKey
	Source source;
	
	public Source getSource() {
		return source;
	}
	
	public void setSource(Source value) {
		source = value;
	}
	
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
	
	@JsonKey
	int width;
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int value) {
		width = value;
	}
	
	@JsonKey
	int height;
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int value) {
		height = value;
	}
	
	@JsonKey
	String visibility;
	
	public String getVisibility() {
		return visibility;
	}
	
	public void setVisibility(String value) {
		visibility = value;
	}
}
