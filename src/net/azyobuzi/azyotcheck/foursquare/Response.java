package net.azyobuzi.azyotcheck.foursquare;

import java.io.Serializable;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

@JsonModel(treatUnknownKeyAsError = false)
public class Response implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4640946863728556612L;
	
	@JsonKey
	Meta meta;
	
	public Meta getMeta() {
		return meta;
	}
	
	public void setMeta(Meta value) {
		meta = value;
	}
	
	@JsonKey
	JsonArray notifications;
	
	public JsonArray getNotifications() {
		return notifications;
	}
	
	public void setNotifications(JsonArray value) {
		notifications = value;
	}
	
	@JsonKey
	JsonHash response;
	
	public JsonHash getResponse() {
		return response;
	}
	
	public void setResponse(JsonHash value) {
		response = value;
	}
}
