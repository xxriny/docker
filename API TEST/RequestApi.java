package pep.per.mint.common.data.basic;

import java.util.Map;

import org.springframework.http.HttpMethod;

import pep.per.mint.common.data.CacheableObject;

public class RequestApi extends CacheableObject {
	private String url;
	private HttpMethod method;
	private Map<String,String> headers;
	private String body;
	
	public String getUrl() {
		return url;
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
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
