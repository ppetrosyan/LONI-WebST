package edu.ucla.loni.pipeline.client.Savers.Configuration;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SaveResourceCSVServiceAsync {
	public void setCSVData(String csvData, AsyncCallback<Boolean> callback);
}
