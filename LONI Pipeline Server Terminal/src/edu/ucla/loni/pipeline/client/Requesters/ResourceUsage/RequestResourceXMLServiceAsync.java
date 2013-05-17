package edu.ucla.loni.pipeline.client.Requesters.ResourceUsage;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RequestResourceXMLServiceAsync {

	public void getXMLData(AsyncCallback<String> callback);
}
