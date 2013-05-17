package edu.ucla.loni.pipeline.client.Requesters.Configuration;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RequestConfigurationXMLServiceAsync {

	public void getXMLData(AsyncCallback<String> callback);
}
