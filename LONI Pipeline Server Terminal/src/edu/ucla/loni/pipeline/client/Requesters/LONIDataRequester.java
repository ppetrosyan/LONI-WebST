package edu.ucla.loni.pipeline.client.Requesters;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LONIDataRequester {
	
	private XMLDataServiceAsync xmlDataService;
	
	public LONIDataRequester(XMLDataServiceAsync xmlDataService) {
		this.xmlDataService = xmlDataService;
	}
	
	public void refreshResourceTabs() {
		
		/** Resource Tabs */
		xmlDataService.getXMLData("ResourceData", new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
            	/** Workflow Tab */
                
                /** Users Tab */
                
                /** Memory Usage Tab */
                
                /** Thread Usage Tab */
            	
            	Window.alert("Resource Tabs refreshed successfully.");
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Resource Tabs did not refresh successfully.");
            }
        });
	}
	
	public void refreshConfigurationTabs() {
		
		/** Configuration Tabs */
		xmlDataService.getXMLData("ConfigurationData", new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
            	/** Preferences Tab */
                
                Window.alert("Configuration Tabs refreshed successfully.");
            }

            @Override
            public void onFailure(Throwable caught) {
            	Window.alert("Configuration Tabs did not refresh successfully.");
            }
        });
	}
	
	public void refreshTabs() {		
		refreshResourceTabs();
		refreshConfigurationTabs();
	}
}