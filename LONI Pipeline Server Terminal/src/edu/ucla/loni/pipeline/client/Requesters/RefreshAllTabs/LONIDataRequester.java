package edu.ucla.loni.pipeline.client.Requesters.RefreshAllTabs;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.ucla.loni.pipeline.client.Charts.LONI_Chart;
import edu.ucla.loni.pipeline.client.MainPage.Preferences.PreferencesTab;
import edu.ucla.loni.pipeline.client.MainPage.Services.AsyncClientServices;
import edu.ucla.loni.pipeline.client.MainPage.UserUsage.UserUsageTab;
import edu.ucla.loni.pipeline.client.MainPage.WorkFlows.WorkFlowsTab;
import edu.ucla.loni.pipeline.client.Utilities.WebUrlResponseBuilder;

public class LONIDataRequester {
	
	private AsyncClientServices asyncClientServices;
	private LONI_Chart memChart;
	private LONI_Chart thrdChart;
	private PreferencesTab preferencesTab;
	private WorkFlowsTab workflowtab;
	private UserUsageTab userusagetab;
	
	public LONIDataRequester(AsyncClientServices asyncClientServices, LONI_Chart memChart, LONI_Chart thrdChart, 
			PreferencesTab preferencesTab, WorkFlowsTab workflowtab, UserUsageTab userusagetab ) {
		this.asyncClientServices = asyncClientServices;
		this.memChart = memChart;
		this.thrdChart = thrdChart;
		this.preferencesTab = preferencesTab;
		this.workflowtab = workflowtab;
		this.userusagetab = userusagetab;
	}
	
	public void refreshResourceTabs() {
		
		/** Resource Tabs */
		asyncClientServices.reqResourceXMLService.getXMLData(new AsyncCallback<String>() {
            @Override
            public void onSuccess(final String xmlData) {
            	refreshResourceTabsWithXml(xmlData);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Refresh of Resource Tabs failed");
            }
        });
	}
	
	public void refreshConfigurationTabs() {
		
		/** Configuration Tabs */
		asyncClientServices.reqConfigurationXMLService.getXMLData(new AsyncCallback<String>() {
            @Override
            public void onSuccess(final String xmlData) {
            	refreshConfigurationTabsWithXml(xmlData);
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
	
	private void refreshConfigurationTabsWithXml (String xmlData) {
		/** Preferences Tab */
    	preferencesTab.refreshPrefTab(xmlData);
        Window.alert("Configuration Tabs refreshed successfully");
	}
	
	private void refreshResourceTabsWithXml (String xmlData) {
    	/** Workflow Tab */            	
    	workflowtab.refreshWorkflows(xmlData);
    	
        /** Users Tab */
        
    	/**User UserUsage Tab */
    	userusagetab.refreshUserUsage(xmlData);
    	userusagetab.refreshUserUsageCount(xmlData);
    	
        /** Memory Usage Tab */
    	memChart.getChart().refreshChart(xmlData);
        
        /** Thread Usage Tab */
    	thrdChart.getChart().refreshChart(xmlData);
    	
    	Window.alert("Resource Tabs refreshed successfully");
	}
	
	public void getWebUrlXml(String url) {
		asyncClientServices.reqwebUrlXMLService.getXML(url, new AsyncCallback<WebUrlResponseBuilder>() {
	    	@Override
	    	public void onFailure(Throwable caught) {
	    		Window.alert("Retrive of XML file failed, check the URL and try again");
	    	}

	    	@Override
	    	public void onSuccess(WebUrlResponseBuilder response) {
	    		if ((response.getStatus() == false) || (response.getXml() == null))
	    			Window.alert("Retrive of XML file failed, message from server - " + response.getMessage());
	    		else {
	    			try {
	    				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    				DocumentBuilder builder = factory.newDocumentBuilder();
					  	Document document = builder.parse(response.getXml());
						document.getDocumentElement().normalize();
						String rootTag = document.getDocumentElement().getNodeName();
						if(rootTag.equalsIgnoreCase("LONIConfigurationData"))
							refreshConfigurationTabsWithXml(response.getXml());
						else if(rootTag.equalsIgnoreCase("LONIResourceData"))
							refreshResourceTabsWithXml(response.getXml());
						else 
							Window.alert("Invalid file format, check the URL and try again");
					}
	    			catch (Exception e) {
						Window.alert("Error when parsing server response, check the URL and try again");
					}
	    		}
	    		
	    	}
	    });
	}
}