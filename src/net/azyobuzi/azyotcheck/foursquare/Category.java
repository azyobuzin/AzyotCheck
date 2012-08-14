package net.azyobuzi.azyotcheck.foursquare;

import java.io.Serializable;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel(treatUnknownKeyAsError = false)
public class Category implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8886968234967746650L;
	
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
	String pluralName;
	
	public String getPluralName() {
		return pluralName;
	}
	
	public void setPluralName(String value) {
		pluralName = value;
	}
	
	@JsonKey
	String shortName;
	
	public String getShortName() {
		return shortName;
	}
	
	public void setShortName(String value) {
		shortName = value;
	}
	
	@JsonKey
	Image icon;
	
	public Image getIcon() {
		return icon;
	}
	
	public void setIcon(Image value) {
		icon = value;
	}
	
	@JsonKey
	boolean primary;
	
	public boolean getPrimary() {
		return primary;
	}
	
	public void setPrimary(boolean value) {
		primary = value;
	}
}
