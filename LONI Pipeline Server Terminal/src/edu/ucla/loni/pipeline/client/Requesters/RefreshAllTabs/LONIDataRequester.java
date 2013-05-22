package edu.ucla.loni.pipeline.client.Requesters.RefreshAllTabs;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.ucla.loni.pipeline.client.Charts.LONI_Chart;
import edu.ucla.loni.pipeline.client.MainPage.Services.AsyncClientServices;

public class LONIDataRequester {
	
	private AsyncClientServices asyncClientServices;
	private LONI_Chart memChart;
	private LONI_Chart thrdChart;
	
	public LONIDataRequester(AsyncClientServices asyncClientServices, LONI_Chart memChart, LONI_Chart thrdChart) {
		this.asyncClientServices = asyncClientServices;
		this.memChart = memChart;
		this.thrdChart = thrdChart;
	}
	
	public void refreshResourceTabs() {
		
		/** Resource Tabs */
		asyncClientServices.reqResourceXMLService.getXMLData(new AsyncCallback<String>() {
            @Override
            public void onSuccess(final String xmlData) {
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
		asyncClientServices.reqConfigurationXMLService.getXMLData(new AsyncCallback<String>() {
            @Override
            public void onSuccess(final String xmlData) {
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