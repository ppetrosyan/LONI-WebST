package edu.ucla.loni.pipeline.client.Requesters;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;

public class LONIRequestData {
	
	private RequestBuilder requestBuilder;
	private RequestCallback requestCallback;
	private String url, parameters;
	
	public LONIRequestData(String url, String parameters, RequestCallback requestCallback) {
		this.url = url;
		this.parameters = parameters;
		this.requestCallback = requestCallback;
		
		buildRequest();
		addCallback();
	}
	
	private void addCallback() {
		requestBuilder.setCallback(requestCallback);
	}

	private void buildRequest() {
		requestBuilder = new RequestBuilder(RequestBuilder.GET, url);
		
		requestBuilder.setHeader("Content-type", "application/x-www-form-urlencoded");

		StringBuilder sb = new StringBuilder();
		sb.append(parameters);
		requestBuilder.setRequestData(sb.toString());
	}
	
	public void issueRequest() throws RequestException {
		requestBuilder.send();
	}
}
