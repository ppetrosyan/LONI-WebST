package edu.ucla.loni.pipeline.client.Requesters.RefreshAllTabs;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.ucla.loni.pipeline.client.Charts.LONI_Chart;
import edu.ucla.loni.pipeline.client.MainPage.Preferences.PreferencesTab;
import edu.ucla.loni.pipeline.client.MainPage.Services.AsyncClientServices;

public class LONIDataRequester {
	
	private AsyncClientServices asyncClientServices;
	private LONI_Chart memChart;
	private LONI_Chart thrdChart;
	private PreferencesTab preferencesTab;
	
	public LONIDataRequester(AsyncClientServices asyncClientServices, LONI_Chart memChart, LONI_Chart thrdChart, 
			PreferencesTab preferencesTab) {
		this.asyncClientServices = asyncClientServices;
		this.memChart = memChart;
		this.thrdChart = thrdChart;
		this.preferencesTab = preferencesTab;
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
            	
            	Window.alert("Resource Tabs refreshed successfully");
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Rfresh of Resource Tabs failed");
            }
        });
	}
	
	public void refreshConfigurationTabs() {
		
		/** Configuration Tabs */
		asyncClientServices.reqConfigurationXMLService.getXMLData(new AsyncCallback<String>() {
            @Override
            public void onSuccess(final String xmlData) {
            	/** Preferences Tab */
            	preferencesTab.refreshPrefTab(xmlData);
                
                Window.alert("Configuration Tabs refreshed successfully");
            }

            @Override
            public void onFailure(Throwable caught) {
            	Window.alert("Refresh of Configuration Tabs failed");
            }
        });
	}
	
	public void refreshTabs() {		
		refreshResourceTabs();
		refreshConfigurationTabs();
	}
	
	public void getWebUrlXml(String url) {
		asyncClientServices.reqwebUrlXMLService.getXML(url, new AsyncCallback<String>() {
	    	@Override
	    	public void onFailure(Throwable caught) {
	    		Window.alert("Retrive of XML file failed, check the URL and try again");
	    	}

	    	@Override
	    	public void onSuccess(String xml) {
	    		Window.alert(xml);
	    	}
	    });
	}
}