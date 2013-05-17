package edu.ucla.loni.pipeline.client.Requesters.RefreshAllTabs;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.ucla.loni.pipeline.client.Charts.LONI_Chart;
import edu.ucla.loni.pipeline.client.Requesters.Configuration.RequestConfigurationXMLServiceAsync;
import edu.ucla.loni.pipeline.client.Requesters.ResourceUsage.RequestResourceXMLServiceAsync;

public class LONIDataRequester {
	
	private RequestResourceXMLServiceAsync reqResourceXMLService;
	private RequestConfigurationXMLServiceAsync reqConfigurationXMLService;
	private LONI_Chart memChart;
	private LONI_Chart thrdChart;
	
	public LONIDataRequester(RequestResourceXMLServiceAsync reqResourceXMLService, RequestConfigurationXMLServiceAsync reqConfigurationXMLService, 
			LONI_Chart memChart, LONI_Chart thrdChart) {
		this.reqResourceXMLService = reqResourceXMLService;
		this.reqConfigurationXMLService = reqConfigurationXMLService;
		this.memChart = memChart;
		this.thrdChart = thrdChart;
	}
	
	public void refreshResourceTabs() {
		
		/** Resource Tabs */
		reqResourceXMLService.getXMLData(new AsyncCallback<String>() {
            @Override
            public void onSuccess(String xmlData) {
            	/** Workflow Tab */
                
                /** Users Tab */
                
                /** Memory Usage Tab */
            	memChart.getChart().refreshChart(xmlData);
                
                /** Thread Usage Tab */
            	thrdChart.getChart().refreshChart(xmlData);
            	
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