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

package edu.ucla.loni.pipeline.client.MainPage.UsersOnline;

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
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

import edu.ucla.loni.pipeline.client.MainPage.Services.AsyncClientServices;
import edu.ucla.loni.pipeline.client.Notifications.LONINotifications;

public class UsersOnlineTab {

	private ListGrid listUsersOnline;
	private final AsyncClientServices asyncClientServices;
	private int TotalUsersOnline;
	private final LONINotifications notifications;

	public UsersOnlineTab(AsyncClientServices asyncClientServices,
			LONINotifications notifications) {
		TotalUsersOnline = 0;
		this.asyncClientServices = asyncClientServices;
		this.notifications = notifications;
		initializeListUsersOnline();
	}

	public Tab setTab() {
		Tab tabUsersOnline = new Tab("Users Online");

		VLayout layoutUsersOnline = new VLayout();
		layoutUsersOnline.setSize("100%", "100%");
		layoutUsersOnline.setMembersMargin(10);

		// Label that show statistic
		final com.smartgwt.client.widgets.Label intro = new com.smartgwt.client.widgets.Label(
				"Loading... (If it takes too long, check the xml file format)");
		intro.setSize("500px", "49px");
		layoutUsersOnline.addMember(intro);

		// List UsersOnline
		listUsersOnline.setShowRecordComponents(true);
		listUsersOnline.setShowRecordComponentsByCell(true);
		listUsersOnline.setShowAllRecords(true);
		listUsersOnline.setSize("100%", "100%");
		listUsersOnline.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersOnline.setCanPickFields(false);
		listUsersOnline.setCanFreezeFields(false);
		listUsersOnline.setAutoFitFieldWidths(true);

		// specific which field you want to expend
		listUsersOnline.setAutoFitExpandField("pipelineInterface");

		// Need to declare these fields here so we can edit their behavior
		final ListGridField disconnectfield = new ListGridField("Disconnect",
				"Disconnect&#160;&#160;&#160;&#160;&#160;");
		disconnectfield.setAlign(Alignment.CENTER);

		listUsersOnline
				.setFields(
						new ListGridField("Username",
								"Username&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
						new ListGridField("IPAddress",
								"IP Address&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
						new ListGridField("PipelineInterface",
								"Pipeline Interface"),
						new ListGridField("PipelineVersion",
								"Pipeline Version&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
						new ListGridField("OSVersion",
								"OS Version&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
						new ListGridField("ConnectTime",
								"Connect Time&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
						new ListGridField("LastActivity",
								"Last Activity&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
						disconnectfield);

		layoutUsersOnline.addMember(listUsersOnline);

		// export to csv label
		final com.smartgwt.client.widgets.Label exportUsersOnline = new com.smartgwt.client.widgets.Label(
				"");

		tabUsersOnline.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				Update(intro, exportUsersOnline);
			}
		});

		// horizontal layout
		HLayout useronlinehLayout = new HLayout();
		useronlinehLayout.setMembersMargin(10);

		Button usersonlinerefreshbutton = new Button("Refresh");
		usersonlinerefreshbutton.setAlign(Alignment.CENTER);
		useronlinehLayout.addMember(usersonlinerefreshbutton);
		usersonlinerefreshbutton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Update(intro, exportUsersOnline);
			}
		});
		// Button exportUsersOnline = new Button("Export to CSV");
		// exportUsersOnline.setAlign(Alignment.CENTER);
		useronlinehLayout.addMember(exportUsersOnline);
		// exportUsersOnline.addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event) {
		// TODO: Export to CSV logic
		// }
		// });
		layoutUsersOnline.addMember(useronlinehLayout);

		tabUsersOnline.setPane(layoutUsersOnline);
		return tabUsersOnline;
	}

	private StringBuilder GetCSVString(ListGrid listGrid) {
		StringBuilder stringBuilder = new StringBuilder();

		// %0A = \n
		// headers
		ListGridField[] fields = listGrid.getFields();
		for (int i = 0; i < fields.length - 1; i++) {
			ListGridField listGridField = fields[i];
			stringBuilder.append(listGridField.getName());
			stringBuilder.append(",");
		}
		// remove last ","
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		stringBuilder.append("%0A");

		// data
		ListGridRecord[] records = listGrid.getRecords();
		for (int i = 0; i < records.length; i++) {
			ListGridRecord listGridRecord = records[i];
			ListGridField[] listGridFields = listGrid.getFields();
			for (int j = 0; j < listGridFields.length - 1; j++) {
				ListGridField listGridField = listGridFields[j];
				stringBuilder.append(listGridRecord.getAttribute(listGridField
						.getName()));
				stringBuilder.append(",");
			}
			// remove last ","
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			stringBuilder.append("%0A");
		}
		return stringBuilder;
	}

	public void Update(final com.smartgwt.client.widgets.Label intro,
			final com.smartgwt.client.widgets.Label exportUsersOnline) {
		asyncClientServices.reqResourceXMLService
				.getXMLData(new AsyncCallback<String>() {
					@Override
					public void onSuccess(final String xmlData) {
						refreshUsersOnline(xmlData);
						notifications.showMessage(
								"Users Online Tab refreshed successfully.",
								true);

						// get current time with specific format
						Date time = new Date();
						DateTimeFormat ft = DateTimeFormat
								.getFormat("EEE MMM d HH:mm:ss ZZZZ yyyy");

						// Update the content of the top label
						intro.setContents("Users Online ( " + TotalUsersOnline
								+ "&#160;) " + "&#160;&#160;&#160;Updated: "
								+ ft.format(time));

						// Update the csv string
						String content = new String(
								GetCSVString(listUsersOnline));
						String Link = new String(
								"<a download='usersonline.csv' href='data:application/csv;charset=utf-8,"
										+ content
										+ "'>Download&#160;CSV&#160;file.</a>");
						exportUsersOnline.setContents(Link);
					}

					@Override
					public void onFailure(Throwable caught) {
						notifications
								.showMessage(
										"Users Online Tab did not refresh successfully.",
										true);
					}
				});
	}

	public void refreshUsersOnline(String xml) {

		// remove whitespace
		String cleanXml = xml.replaceAll("\t", "");
		cleanXml.replaceAll("\n", "");

		try {
			Document doc = XMLParser.parse(cleanXml);
			parseUsersOnlineXML(doc);
		} catch (DOMParseException e) {
			System.err
					.println("Could not parse XML file. Check XML file format.");
			return;
		}
	}

	public void parseUsersOnlineXML(Document doc) {

		NodeList UsersOnlineList = doc.getElementsByTagName("UsersOnlineEntry");

		// Get the number of users
		TotalUsersOnline = UsersOnlineList.getLength();

		UsersOnlineRecord array[] = new UsersOnlineRecord[TotalUsersOnline];
		for (int k = 0; k < TotalUsersOnline; k++) {
			Node UsersOnlineNode = UsersOnlineList.item(k);
			if (UsersOnlineNode.getNodeType() == Node.ELEMENT_NODE) {
				Element UsersOnlineElement = (Element) UsersOnlineNode;

				// 1.Username
				NodeList UsernameList = UsersOnlineElement
						.getElementsByTagName("Username");
				Element UsernameElement = (Element) UsernameList.item(0);
				NodeList textUsernameList = UsernameElement.getChildNodes();

				// 2.IP Address
				NodeList IPAddressList = UsersOnlineElement
						.getElementsByTagName("IPAddress");
				Element IPAddressElement = (Element) IPAddressList.item(0);
				NodeList textIPAddressList = IPAddressElement.getChildNodes();

				// 3.Pipeline Interface
				NodeList PipelineList = UsersOnlineElement
						.getElementsByTagName("PipelineInterface");
				Element PipelineElement = (Element) PipelineList.item(0);
				NodeList textPipelineList = PipelineElement.getChildNodes();

				// 4.Pipeline Version
				NodeList PipelineVerList = UsersOnlineElement
						.getElementsByTagName("PipelineVersion");
				Element PipelineVerElement = (Element) PipelineVerList.item(0);
				NodeList textPipelineVerList = PipelineVerElement
						.getChildNodes();

				// 5.OS Version
				NodeList OSVerList = UsersOnlineElement
						.getElementsByTagName("OSVersion");
				Element OSVerElement = (Element) OSVerList.item(0);
				NodeList textOSVerList = OSVerElement.getChildNodes();

				// 6.Connect Time
				NodeList ConnectTimeList = UsersOnlineElement
						.getElementsByTagName("ConnectTime");
				Element ConnectTimeElement = (Element) ConnectTimeList.item(0);
				NodeList textConnectTimeList = ConnectTimeElement
						.getChildNodes();

				// 7.Last Activity
				NodeList LastActivityList = UsersOnlineElement
						.getElementsByTagName("LastActivity");
				Element LastActivityElement = (Element) LastActivityList
						.item(0);
				NodeList textLastActivityList = LastActivityElement
						.getChildNodes();

				array[k] = new UsersOnlineRecord(textUsernameList.item(0)
						.getNodeValue().trim(), textIPAddressList.item(0)
						.getNodeValue().trim(), textPipelineList.item(0)
						.getNodeValue().trim(), textPipelineVerList.item(0)
						.getNodeValue().trim(), textOSVerList.item(0)
						.getNodeValue().trim(), textConnectTimeList.item(0)
						.getNodeValue().trim(), textLastActivityList.item(0)
						.getNodeValue().trim());

			}// end if
		}// end loop
		listUsersOnline.setData(array);
	}

	private void initializeListUsersOnline() {
		// this function displays the buttons in the ListGrids
		listUsersOnline = new ListGrid() {
			@Override
			protected Canvas createRecordComponent(final ListGridRecord record,
					Integer colNum) {
				String fieldName = this.getFieldName(colNum);
				// show Disconnect button
				if (fieldName.equals("Disconnect")) {
					IButton button = new IButton();
					button.setHeight(16);
					button.setWidth(70);
					button.setTitle("Disconnect");
					return button;
				} else {
					return null;
				}

			}
		}; // end of function
	}
}
