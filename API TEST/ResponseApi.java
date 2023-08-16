package pep.per.mint.common.data.basic;

import java.util.Map;

import pep.per.mint.common.data.CacheableObject;

public class ResponseApi extends CacheableObject{
	private int statusCode;
	private Map<String,String> headers;
	private String body;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
