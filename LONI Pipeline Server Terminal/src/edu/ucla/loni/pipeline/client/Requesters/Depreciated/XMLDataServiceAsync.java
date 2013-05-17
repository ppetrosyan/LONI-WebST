package edu.ucla.loni.pipeline.client.Requesters.Depreciated;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface XMLDataServiceAsync {

	public void getXMLData(String xmlDataType, AsyncCallback<String> callback);
}
