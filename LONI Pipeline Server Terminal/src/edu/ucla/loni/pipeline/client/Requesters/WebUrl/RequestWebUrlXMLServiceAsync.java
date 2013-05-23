package edu.ucla.loni.pipeline.client.Requesters.WebUrl;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RequestWebUrlXMLServiceAsync {

	public void getXML(String url, AsyncCallback<String> callback);
}
