/*
 * This file is part of LONI Pipeline Web-based Server Terminal.
 * 
 * LONI Pipeline Web-based Server Terminal is free software: 
 * you can redistribute it and/or modify it under the terms of the 
 * GNU Lesser General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * LONI Pipeline Web-based Server Terminal is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the 
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public License
 * along with LONI Pipeline Web-based Server Terminal.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package edu.ucla.loni.pipeline.client.Requesters.RefreshAllTabs;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.gwt.crypto.client.TripleDesCipher;

import edu.ucla.loni.pipeline.client.Charts.LONI_Chart;
import edu.ucla.loni.pipeline.client.MainPage.Preferences.PreferencesTab;
import edu.ucla.loni.pipeline.client.MainPage.Services.AsyncClientServices;
import edu.ucla.loni.pipeline.client.MainPage.UserUsage.UserUsageTab;
import edu.ucla.loni.pipeline.client.MainPage.UsersOnline.UsersOnlineTab;
import edu.ucla.loni.pipeline.client.MainPage.WorkFlows.WorkFlowsTab;
import edu.ucla.loni.pipeline.client.Notifications.LONINotifications;
import edu.ucla.loni.pipeline.client.Utilities.WebUrlResponseBuilder;

/**
 * Client-side Service used to request data from the server and refresh tabs
 * 
 * @author Jared
 * @author Deepak
 */
public class LONIDataRequester {

	private final AsyncClientServices asyncClientServices;
	private final LONI_Chart memChart;
	private final LONI_Chart thrdChart;
	private final PreferencesTab preferencesTab;
	private final WorkFlowsTab workflowtab;
	private final UsersOnlineTab usersonlinetab;
	private final UserUsageTab userusagetab;
	private final LONINotifications notifications;

	/**
	 * Constructor
	 * 
	 * @param asyncClientServices
	 * @param memChart
	 * @param thrdChart
	 * @param preferencesTab
	 * @param workflowtab
	 * @param usersonlinetab
	 * @param userusagetab
	 * @param notifications
	 */
	public LONIDataRequester(AsyncClientServices asyncClientServices,
			LONI_Chart memChart, LONI_Chart thrdChart,
			PreferencesTab preferencesTab, WorkFlowsTab workflowtab,
			UsersOnlineTab usersonlinetab, UserUsageTab userusagetab,
			LONINotifications notifications) {
		this.asyncClientServices = asyncClientServices;
		this.memChart = memChart;
		this.thrdChart = thrdChart;
		this.preferencesTab = preferencesTab;
		this.workflowtab = workflowtab;
		this.usersonlinetab = usersonlinetab;
		this.userusagetab = userusagetab;
		this.notifications = notifications;
	}

	/**
	 * Refreshes the Tabs that depend on Resource Data
	 */
	public void refreshResourceTabs() {

		/** Resource Tabs */
		asyncClientServices.reqResourceXMLService
				.getXMLData(new AsyncCallback<String>() {
					@Override
					public void onSuccess(final String xmlData) {
						refreshResourceTabsWithXml(xmlData);
					}

					@Override
					public void onFailure(Throwable caught) {
						notifications
								.showMessage("ERROR: Resource Tabs did not refresh successfully.");
					}
				});
	}

	/**
	 * Refreshes the Tabs that depend on Configuration Data
	 */
	public void refreshConfigurationTabs() {

		/** Configuration Tabs */
		asyncClientServices.reqConfigurationXMLService
				.getXMLData(new AsyncCallback<String>() {
					@Override
					public void onSuccess(final String xmlData) {
						refreshConfigurationTabsWithXml(xmlData);
					}

					@Override
					public void onFailure(Throwable caught) {
						notifications
								.showMessage("ERROR: Configuration Tabs did not refresh successfully.");
					}
				});
	}

	/**
	 * Refreshes all Tabs
	 */
	public void refreshTabs() {
		refreshResourceTabs();
		refreshConfigurationTabs();
	}

	/**
	 * Helper function to Refresh the Tabs that depend on Configuration Data
	 * 
	 * @param xmlData
	 */
	private void refreshConfigurationTabsWithXml(String xmlData) {
		/** Preferences Tab */
		preferencesTab.refreshPrefTab(xmlData);
		notifications.showMessage("Configuration Tabs refreshed successfully.");
	}

	/**
	 * Helper function to Refresh the Tabs that depend on Resource Data
	 * 
	 * @param xmlData
	 */
	private void refreshResourceTabsWithXml(String xmlData) {
		/** Workflow Tab */
		workflowtab.refreshWorkflows(xmlData);

		/** Users Tab */
		usersonlinetab.refreshUsersOnline(xmlData);

		/** User UserUsage Tab */
		userusagetab.refreshUserUsage(xmlData);
		userusagetab.refreshUserUsageCount(xmlData);

		/** Memory Usage Tab */
		memChart.getChart().refreshChart(xmlData);

		/** Thread Usage Tab */
		thrdChart.getChart().refreshChart(xmlData);

		notifications.showMessage("Resource Tabs refreshed successfully.");
	}

	/**
	 * Retrieves XML file given a URL, username and password
	 * 
	 * @param url
	 * @param username
	 * @param password
	 */
	public void getWebUrlXml(String url, String username, String password) {

		// Encrypt the string
		TripleDesCipher cipher = new TripleDesCipher();
		final byte[] GWT_DES_KEY = new byte[] { 0x10, 0x20, 0x30, 0x40, 0x50,
				0x60, 0x70, (byte) 0x80, (byte) 0x90, (byte) 0xA0, (byte) 0xB0,
				(byte) 0xC0, (byte) 0xD0, (byte) 0xE0, (byte) 0xF0,
				(byte) 0xA1, (byte) 0xB1, (byte) 0xC1, (byte) 0xD1,
				(byte) 0xE1, (byte) 0xF1 };
		cipher.setKey(GWT_DES_KEY);
		String we, ue, pe;

		try {
			we = cipher.encrypt(String.valueOf(url));
			ue = cipher.encrypt(String.valueOf(username));
			pe = cipher.encrypt(String.valueOf(password));
		} catch (Exception e) {
			Window.alert("Encryption failed");
			return;
		}

		asyncClientServices.reqwebUrlXMLService.getXML(we, ue, pe, GWT_DES_KEY,
				new AsyncCallback<WebUrlResponseBuilder>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Retrive of XML file failed, check the URL and try again");
					}

					@Override
					public void onSuccess(WebUrlResponseBuilder response) {
						if ((response.getStatus() == false)
								|| (response.getXml() == null)) {
							Window.alert("Retrive of XML file failed, message from server - "
									+ response.getMessage());
						} else {
							String rootTag = response.getRootTag();
							if (rootTag
									.equalsIgnoreCase("LONIConfigurationData")) {
								refreshConfigurationTabsWithXml(response
										.getXml());
							} else if (rootTag
									.equalsIgnoreCase("LONIResourceData")) {
								refreshResourceTabsWithXml(response.getXml());
							} else {
								Window.alert(rootTag
										+ "Invalid file format, check the URL and try again");
							}
						}

					}
				});
	}
}