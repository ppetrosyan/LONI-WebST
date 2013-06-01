package edu.ucla.loni.pipeline.client.Requesters.RefreshAllTabs;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.google.gwt.xml.client.Document;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.XMLParser;
import com.googlecode.gwt.crypto.client.TripleDesCipher;

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
                Window.alert("Resource Tabs did not refresh successfully.");
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
            	Window.alert("Configuration Tabs did not refresh successfully.");
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
        Window.alert("Configuration Tabs refreshed successfully.");
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
    	
    	Window.alert("Resource Tabs refreshed successfully.");
	}
	
	public void getWebUrlXml(String url, String username, String password) {
		
        // Encrypt the string
		TripleDesCipher cipher = new TripleDesCipher();
		final byte[] GWT_DES_KEY = new byte[] {0x10, 0x20, 0x30, 0x40, 0x50, 0x60, 0x70, 
				  (byte) 0x80, (byte) 0x90, (byte) 0xA0, (byte) 0xB0, 
				  (byte) 0xC0, (byte) 0xD0, (byte) 0xE0, (byte) 0xF0, 
				  (byte) 0xA1, (byte) 0xB1, (byte) 0xC1, (byte) 0xD1, 
				  (byte) 0xE1, (byte) 0xF1};
		cipher.setKey(GWT_DES_KEY);
		String we, ue, pe;
		
		try {
			we = cipher.encrypt(String.valueOf(url));
			ue = cipher.encrypt(String.valueOf(username));
			pe = cipher.encrypt(String.valueOf(password));
		} 
		catch (Exception e) {
    		Window.alert("Encryption failed");
    		return;
		}
		
		asyncClientServices.reqwebUrlXMLService.getXML(we, ue, pe, GWT_DES_KEY, new AsyncCallback<WebUrlResponseBuilder>() {
	    	@Override
	    	public void onFailure(Throwable caught) {
	    		Window.alert("Retrive of XML file failed, check the URL and try again");
	    	}

	    	@Override
	    	public void onSuccess(WebUrlResponseBuilder response) {
	    		if ((response.getStatus() == false) || (response.getXml() == null))
	    			Window.alert(response.getXml() + "Retrive of XML file failed, message from server - " + response.getMessage());
	    		else {
	    			try {
					  	Document document = XMLParser.parse(response.getXml());
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