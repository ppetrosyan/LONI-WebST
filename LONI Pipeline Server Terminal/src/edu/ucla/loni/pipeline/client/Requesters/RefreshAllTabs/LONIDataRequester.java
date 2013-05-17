package edu.ucla.loni.pipeline.client.Requesters.RefreshAllTabs;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.ucla.loni.pipeline.client.Requesters.Configuration.RequestConfigurationXMLServiceAsync;
import edu.ucla.loni.pipeline.client.Requesters.ResourceUsage.RequestResourceXMLServiceAsync;

public class LONIDataRequester {
	
	private RequestResourceXMLServiceAsync reqResourceXMLService;
	private RequestConfigurationXMLServiceAsync reqConfigurationXMLService;
	
	public LONIDataRequester(RequestResourceXMLServiceAsync reqResourceXMLService, RequestConfigurationXMLServiceAsync reqConfigurationXMLService) {
		this.reqResourceXMLService = reqResourceXMLService;
		this.reqConfigurationXMLService = reqConfigurationXMLService;
	}
	
	public void refreshResourceTabs() {
		
		/** Resource Tabs */
		reqResourceXMLService.getXMLData(new AsyncCallback<String>() {
            @Override
            public void onSuccess(String xmlData) {
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
		reqConfigurationXMLService.getXMLData(new AsyncCallback<String>() {
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