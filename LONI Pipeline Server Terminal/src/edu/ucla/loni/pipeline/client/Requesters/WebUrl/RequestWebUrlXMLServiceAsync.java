package edu.ucla.loni.pipeline.client.Requesters.WebUrl;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.ucla.loni.pipeline.client.Utilities.WebUrlResponseBuilder;

public interface RequestWebUrlXMLServiceAsync {

	public void getXML(String url, AsyncCallback<WebUrlResponseBuilder> callback);
}
