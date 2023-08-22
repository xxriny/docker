package pep.per.mint.common.data.basic;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import pep.per.mint.common.data.CacheableObject;


public class RequestApi extends CacheableObject {
	private String url;
	private HttpMethod method;
	
	private Map<String,Object> requestData = new HashMap<>();
	private Map<String,String> headers;

	public String getUrl() {
		return url;
	}
	
	public void setRequestData(String key, Object value) {
		requestData.put(key, value);
	}
	public Map<String,Object> getRequestData() {
		return requestData;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public HttpMethod getMethod() {
		return method;
	}
	public void setMethod(HttpMethod method) {
		this.method = method;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
}