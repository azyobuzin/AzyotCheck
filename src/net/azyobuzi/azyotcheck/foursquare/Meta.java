package net.azyobuzi.azyotcheck.foursquare;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel(treatUnknownKeyAsError = false)
public class Meta {
	@JsonKey
	int code;
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int value) {
		code = value;
	}
	
	@JsonKey
	String errorType;
	
	public String getErrorType() {
		return errorType;
	}
	
	public void setErrorType(String value) {
		errorType = value;
	}
	
	@JsonKey
	String errorDetail;
	
	public String getErrorDetail() {
		return errorDetail;
	}
	
	public void setErrorDetail(String value) {
		errorDetail = value;
	}
	
	@JsonKey
	String errorMessage;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String value) {
		errorMessage = value;
	}
}
