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

package edu.ucla.loni.pipeline.client.MainPage.UserUsage;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.impl.DOMParseException;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

import edu.ucla.loni.pipeline.client.MainPage.Services.AsyncClientServices;
import edu.ucla.loni.pipeline.client.Notifications.LONINotifications;

public class UserUsageTab {
	private final ListGrid listUserUsage, listUserUsageCount;
	private final AsyncClientServices asyncClientServices;
	private int TotalWorkflows;
	private final LONINotifications notifications;

	public UserUsageTab(AsyncClientServices asyncClientServices,
			LONINotifications notifications) {
		listUserUsage = new ListGrid();
		listUserUsageCount = new ListGrid();
		TotalWorkflows = 0;
		this.asyncClientServices = asyncClientServices;
		this.notifications = notifications;
	}

	public Tab setTab() {
		Tab tabUserUsage = new Tab("User Usage");

		VLayout layoutUserUsage = new VLayout();
		layoutUserUsage.setSize("100%", "100%");
		layoutUserUsage.setDefaultLayoutAlign(Alignment.CENTER);
		layoutUserUsage.setMembersMargin(10);

		VLayout layoutintro = new VLayout();
		layoutintro.setDefaultLayoutAlign(Alignment.LEFT);

		// Label that show statistic
		final com.smartgwt.client.widgets.Label intro = new com.smartgwt.client.widgets.Label(
				"Loading... (If it takes too long, check the xml file format)");
		intro.setSize("500px", "49px");
		layoutintro.addMember(intro);
		layoutUserUsage.addMember(layoutintro);

		// List UserUsage
		listUserUsage.setSize("100%", "50%");
		listUserUsage.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUserUsage.setCanPickFields(false);
		listUserUsage.setCanFreezeFields(false);
		listUserUsage.setAutoFitFieldWidths(true);

		// specific which field you want to expend
		listUserUsage.setAutoFitExpandField("workflowID");

		// Declare fields for User Usage list
		// Edit the behavior of the fields here
		final ListGridField usernamefield = new ListGridField(
				"username",
				"Username&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;");
		usernamefield.setCellAlign(Alignment.CENTER);

		final ListGridField workflowidfield = new ListGridField("workflowID",
				"Workflow ID");
		workflowidfield.setCellAlign(Alignment.CENTER);

		final ListGridField nodenamefield = new ListGridField(
				"nodeName",
				"NodeName&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;");
		nodenamefield.setCellAlign(Alignment.CENTER);

		final ListGridField instancefield = new ListGridField(
				"instance",
				"Instance&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;");
		instancefield.setCellAlign(Alignment.RIGHT);

		listUserUsage.setFields(usernamefield, workflowidfield, nodenamefield,
				instancefield);

		// List UserUsageCount
		listUserUsageCount.setSize("50%", "50%");
		listUserUsageCount.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUserUsageCount.setCanPickFields(false);
		listUserUsageCount.setCanFreezeFields(false);
		listUserUsageCount.setAutoFitFieldWidths(true);

		// specific which field you want to expend
		listUserUsageCount.setAutoFitExpandField("username");

		// Declare fields for User Usage Count list
		// Edit the behavior of the fields here
		final ListGridField username2field = new ListGridField("username",
				"Username");
		username2field.setCellAlign(Alignment.CENTER);

		final ListGridField countfield = new ListGridField(
				"count",
				"Count&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;");
		countfield.setCellAlign(Alignment.RIGHT);

		listUserUsageCount.setFields(username2field, countfield);

		layoutUserUsage.addMember(listUserUsage);
		layoutUserUsage.addMember(listUserUsageCount);

		tabUserUsage.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				Update(intro);
			}
		});

		VLayout layoutbotton = new VLayout();

		Button userusagerefreshbutton = new Button("Refresh");
		userusagerefreshbutton.setAlign(Alignment.CENTER);
		layoutbotton.addMember(userusagerefreshbutton);
		userusagerefreshbutton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Update(intro);
			}
		});

		layoutUserUsage.addMember(layoutbotton);
		tabUserUsage.setPane(layoutUserUsage);

		return tabUserUsage;
	}

	public void Update(final com.smartgwt.client.widgets.Label intro) {
		asyncClientServices.reqResourceXMLService
				.getXMLData(new AsyncCallback<String>() {
					@Override
					public void onSuccess(final String xmlData) {
						refreshUserUsage(xmlData);
						refreshUserUsageCount(xmlData);
						System.out.println("UserUsage refreshed successfully");

						// get current time with specific format
						Date time = new Date();
						DateTimeFormat ft = DateTimeFormat
								.getFormat("EEE MMM d HH:mm:ss ZZZZ yyyy");

						// Update the content of the top label
						intro.setContents("User Usage ( " + TotalWorkflows
								+ "&#160;) " + "&#160;&#160;&#160;Updated: "
								+ ft.format(time));

						notifications
								.showMessage("User Usage updated successfully.");
					}

					@Override
					public void onFailure(Throwable caught) {
						notifications
								.showMessage("ERROR: User Usage did not update successfully.");
					}
				});
	}

	public void refreshUserUsage(String xml) {

		// remove whitespace
		String cleanXml = xml.replaceAll("\t", "");
		cleanXml.replaceAll("\n", "");

		try {
			Document doc = XMLParser.parse(cleanXml);
			parseUserUsageXML(doc);
		} catch (DOMParseException e) {
			System.err
					.println("Could not parse XML file. Check XML file format.");
			return;
		}
	}

	public void refreshUserUsageCount(String xml) {

		// remove whitespace
		String cleanXml = xml.replaceAll("\t", "");
		cleanXml.replaceAll("\n", "");

		try {
			Document doc = XMLParser.parse(cleanXml);
			parseUserUsageCountXML(doc);
		} catch (DOMParseException e) {
			System.err
					.println("Could not parse XML file. Check XML file format.");
			return;
		}
	}

	public void parseUserUsageXML(Document doc) {
		NodeList UserUsageList = doc.getElementsByTagName("UserUsageEntry");
		// get this total workflows here for display
		TotalWorkflows = UserUsageList.getLength();

		UserUsageRecord array[] = new UserUsageRecord[TotalWorkflows];
		for (int k = 0; k < TotalWorkflows; k++) {
			Node UserUsageNode = UserUsageList.item(k);
			if (UserUsageNode.getNodeType() == Node.ELEMENT_NODE) {
				Element UserUsageElement = (Element) UserUsageNode;

				// Username
				NodeList UsernameList = UserUsageElement
						.getElementsByTagName("Username");
				Element UsernameElement = (Element) UsernameList.item(0);
				NodeList textUsernameList = UsernameElement.getChildNodes();

				// WorkflowID
				NodeList WorkflowIDList = UserUsageElement
						.getElementsByTagName("WorkflowID");
				Element WorkflowIDElement = (Element) WorkflowIDList.item(0);
				NodeList textWorkflowIDList = WorkflowIDElement.getChildNodes();

				// NodeName
				NodeList NodeNameList = UserUsageElement
						.getElementsByTagName("NodeName");
				Element NodeNameElement = (Element) NodeNameList.item(0);
				NodeList textNodeNameList = NodeNameElement.getChildNodes();

				// Instance
				NodeList InstanceList = UserUsageElement
						.getElementsByTagName("Instance");
				Element InstanceElement = (Element) InstanceList.item(0);
				NodeList textInstanceList = InstanceElement.getChildNodes();

				array[k] = new UserUsageRecord(textUsernameList.item(0)
						.getNodeValue().trim(), textWorkflowIDList.item(0)
						.getNodeValue().trim(), textNodeNameList.item(0)
						.getNodeValue().trim(), textInstanceList.item(0)
						.getNodeValue().trim());
			}// end if
		}// end loop
		listUserUsage.setData(array);
	}

	public void parseUserUsageCountXML(Document doc) {
		NodeList UserUsageCountList = doc
				.getElementsByTagName("UserUsageCountEntry");
		int TotalUserUsageCount = UserUsageCountList.getLength();

		UserUsageCountRecord array[] = new UserUsageCountRecord[TotalUserUsageCount];
		for (int k = 0; k < TotalUserUsageCount; k++) {
			Node UserUsageCountNode = UserUsageCountList.item(k);
			if (UserUsageCountNode.getNodeType() == Node.ELEMENT_NODE) {
				Element UserUsageCountElement = (Element) UserUsageCountNode;

				// Username
				NodeList UsernameList = UserUsageCountElement
						.getElementsByTagName("Username");
				Element UsernameElement = (Element) UsernameList.item(0);
				NodeList textUsernameList = UsernameElement.getChildNodes();

				// Count
				NodeList CountList = UserUsageCountElement
						.getElementsByTagName("Count");
				Element CountElement = (Element) CountList.item(0);
				NodeList textCountList = CountElement.getChildNodes();

				array[k] = new UserUsageCountRecord(textUsernameList.item(0)
						.getNodeValue().trim(), textCountList.item(0)
						.getNodeValue().trim());
			}// end if
		}// end loop
		listUserUsageCount.setData(array);
	}
}
