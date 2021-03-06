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

package edu.ucla.loni.pipeline.client.MainPage.Preferences;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.impl.DOMParseException;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import edu.ucla.loni.pipeline.client.MainPage.Preferences.Access.AccessTab;
import edu.ucla.loni.pipeline.client.MainPage.Preferences.Advanced.AdvancedTab;
import edu.ucla.loni.pipeline.client.MainPage.Preferences.Executables.ExecutablesTab;
import edu.ucla.loni.pipeline.client.MainPage.Preferences.General.GeneralTab;
import edu.ucla.loni.pipeline.client.MainPage.Preferences.Grid.GridTab;
import edu.ucla.loni.pipeline.client.MainPage.Preferences.Packages.PackagesTab;
import edu.ucla.loni.pipeline.client.MainPage.Services.AsyncClientServices;
import edu.ucla.loni.pipeline.client.Notifications.LONINotifications;

public class PreferencesTab {

	private VLayout padding;
	private GeneralTab generalTab;
	private GridTab gridTab;
	private AccessTab accessTab;
	private PackagesTab packagesTab;
	private ExecutablesTab executablesTab;
	private AdvancedTab advancedTab;
	private final AsyncClientServices asyncClientServices;
	private final LONINotifications notifications;

	public PreferencesTab(VLayout padding,
			AsyncClientServices asyncClientServices,
			LONINotifications notifications) {
		this.padding = padding;
		this.asyncClientServices = asyncClientServices;
		this.notifications = notifications;
	}

	public Tab setTab() {
		Tab tabPreferences = new Tab("Preferences");

		VLayout prefLayout = new VLayout();

		// Header with Heading and logout button
		HLayout headLayout = new HLayout();
		headLayout.setMembersMargin(20);
		headLayout.setSize("100%", "2%");

		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("50%", "100%");
		headLayout.addMember(padding);

		Button refreshConfig = new Button("Refresh Config");
		refreshConfig.setAlign(Alignment.CENTER);
		refreshConfig.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				asyncClientServices.reqConfigurationXMLService
						.getXMLData(new AsyncCallback<String>() {
							@Override
							public void onSuccess(final String xmlData) {
								refreshPrefTab(xmlData);

								notifications
										.showMessage("Preferences updated successfully.");
							}

							@Override
							public void onFailure(Throwable caught) {
								notifications
										.showMessage("ERROR: Preferences did not update successfully.");
							}
						});
			}
		});
		headLayout.addMember(refreshConfig);

		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("1%", "100%");
		headLayout.addMember(padding);

		Button saveConfig = new Button("Save Config");
		saveConfig.setAlign(Alignment.CENTER);
		saveConfig.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO: Save Config logic
			}
		});
		headLayout.addMember(saveConfig);

		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("1%", "100%");
		headLayout.addMember(padding);

		headLayout.draw();
		prefLayout.addMember(headLayout);

		// Body with tabs
		TabSet tabSet = new TabSet();
		tabSet.setSize("100%", "100%");
		tabSet.setPaneMargin(30);

		generalTab = new GeneralTab();
		tabSet.addTab(generalTab.setTab());

		gridTab = new GridTab();
		tabSet.addTab(gridTab.setTab());

		accessTab = new AccessTab();
		tabSet.addTab(accessTab.setTab());

		packagesTab = new PackagesTab();
		tabSet.addTab(packagesTab.setTab());

		executablesTab = new ExecutablesTab();
		tabSet.addTab(executablesTab.setTab());

		advancedTab = new AdvancedTab();
		tabSet.addTab(advancedTab.setTab());

		tabSet.draw();
		prefLayout.addMember(tabSet);
		prefLayout.draw();

		tabPreferences.setPane(prefLayout);
		return tabPreferences;
	}

	public void refreshPrefTab(String xml) {
		parsePrefXML(xml);
	}

	private void parsePrefXML(String xml) {
		System.out.println("parsePrefXML");

		// remove whitespace
		String cleanXml = xml.replaceAll("\t", "");
		cleanXml.replaceAll("\n", "");

		try {
			Document doc = XMLParser.parse(cleanXml);

			generalTab.parseGeneralXML(doc);
			gridTab.parseGridXML(doc);
			accessTab.parseAccessXML(doc);
			packagesTab.parsePackageXML(doc);
			executablesTab.parseExecutablesXML(doc);
			advancedTab.parseAdvancedXML(doc);
		} catch (DOMParseException e) {
			System.err
					.println("Could not parse XML file. Check XML file format.");
			return;
		}
	}
}
