package edu.ucla.loni.pipeline.client.Savers.Configuration;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SaveConfigurationXMLServiceAsync {
	public void setXMLData(String xmlData, AsyncCallback<Boolean> callback);
}
