package edu.ucla.loni.pipeline.client.MainPage.UsersOnline;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
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
	private AsyncClientServices asyncClientServices;
	private int TotalUsersOnline;
	private LONINotifications notifications;
	
	public UsersOnlineTab(AsyncClientServices asyncClientServices, LONINotifications notifications) {
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
		
		//Label that show statistic
		final com.smartgwt.client.widgets.Label intro = new com.smartgwt.client.widgets.Label(
						"Loading...");
		intro.setSize("500px", "49px");		
		layoutUsersOnline.addMember(intro);

		//List UsersOnline
		listUsersOnline.setShowRecordComponents(true);
		listUsersOnline.setShowRecordComponentsByCell(true);
		listUsersOnline.setShowAllRecords(true);
		listUsersOnline.setSize("100%", "100%");
		listUsersOnline.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersOnline.setCanPickFields(false);
		listUsersOnline.setCanFreezeFields(false);
		listUsersOnline.setAutoFitFieldWidths(true);
		
		//specific which field you want to expend
		listUsersOnline.setAutoFitExpandField("pipelineInterface");
		
		// Need to declare these fields here so we can edit their behavior
		final ListGridField disconnectfield = new ListGridField("disconnect",
						"Disconnect&#160;&#160;&#160;&#160;&#160;");
		disconnectfield.setAlign(Alignment.CENTER);

		listUsersOnline.setFields(new ListGridField("username", "Username&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
				new ListGridField("ipAddress", "IP Address&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
				new ListGridField("pipelineInterface", "Pipeline Interface"),
				new ListGridField("pipelineVersion", "Pipeline Version&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
				new ListGridField("osVersion", "OS Version&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
				new ListGridField("connectTime", "Connect Time&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
				new ListGridField("lastActivity", "Last Activity&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
				disconnectfield);

		layoutUsersOnline.addMember(listUsersOnline);

		tabUsersOnline.addTabSelectedHandler(new TabSelectedHandler() {
			public void onTabSelected(TabSelectedEvent event) {
				Update(intro);
			}
		});

		// horizontal layout
		HLayout useronlinehLayout = new HLayout();
		useronlinehLayout.setMembersMargin(10);

		Button usersonlinerefreshbutton = new Button("Refresh");
		usersonlinerefreshbutton.setAlign(Alignment.CENTER);
		useronlinehLayout.addMember(usersonlinerefreshbutton);
		usersonlinerefreshbutton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Update(intro);
			}
		});
		
		//Button exportUsersOnline = new Button("<a download='somedata.csv' href='data:application/csv;charset=utf-8,Col1%2C%2C%2CCol2%2CCol3%0AVal1%2CVal2%2CVal3%0AVal11%2CVal22%2CVal33%0AVal111%2CVal222%2CVal333'>Example</a>");
		Button exportUsersOnline = new Button("Export to CSV");
		exportUsersOnline.setAlign(Alignment.CENTER);
		useronlinehLayout.addMember(exportUsersOnline);
		exportUsersOnline.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO: Export to CSV logic
			}
		});
		layoutUsersOnline.addMember(useronlinehLayout);

		tabUsersOnline.setPane(layoutUsersOnline);
		return tabUsersOnline;
	}
	
	public void Update(final com.smartgwt.client.widgets.Label intro){
		asyncClientServices.reqResourceXMLService.getXMLData(new AsyncCallback<String>() {
			@Override
			public void onSuccess(final String xmlData) {
				refreshUsersOnline(xmlData);
				System.out.println("Users Online refreshed successfully");
				
				//get current time with specific format
				Date time = new Date();
				DateTimeFormat ft = DateTimeFormat.getFormat("EEE MMM d HH:mm:ss ZZZZ yyyy");
			    
			  //Update the content of the top label
				intro.setContents("Users Online ( " + TotalUsersOnline + "&#160;) "
			    + "&#160;&#160;&#160;Updated: " + ft.format(time));
				
				notifications.showMessage("Users Online Tab refreshed successfully.", true);
			}

			@Override
			public void onFailure(Throwable caught) {
				notifications.showMessage("Users Online Tab did not refresh successfully.", true);
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
			System.err.println("Could not parse XML file. Check XML file format.");
			return;
		}
	}
	
	public void parseUsersOnlineXML(Document doc) {

		NodeList UsersOnlineList = doc.getElementsByTagName("UsersOnlineEntry");
		
		//Get the number of users
		TotalUsersOnline = UsersOnlineList.getLength();
		
		UsersOnlineRecord array[] = new UsersOnlineRecord[TotalUsersOnline];
		for (int k = 0; k < TotalUsersOnline; k++) {
			Node UsersOnlineNode = UsersOnlineList.item(k);
			if (UsersOnlineNode.getNodeType() == Node.ELEMENT_NODE) {
				Element UsersOnlineElement = (Element) UsersOnlineNode;

				// 1.Username
				NodeList UsernameList = UsersOnlineElement.getElementsByTagName("Username");
				Element UsernameElement = (Element) UsernameList.item(0);
				NodeList textUsernameList = UsernameElement.getChildNodes();

				// 2.IP Address
				NodeList IPAddressList = UsersOnlineElement.getElementsByTagName("IPAddress");
				Element IPAddressElement = (Element) IPAddressList.item(0);
				NodeList textIPAddressList = IPAddressElement.getChildNodes();

				// 3.Pipeline Interface
				NodeList PipelineList = UsersOnlineElement.getElementsByTagName("PipelineInterface");
				Element PipelineElement = (Element) PipelineList.item(0);
				NodeList textPipelineList = PipelineElement.getChildNodes();

				// 4.Pipeline Version
				NodeList PipelineVerList = UsersOnlineElement.getElementsByTagName("PipelineVersion");
				Element PipelineVerElement = (Element) PipelineVerList.item(0);
				NodeList textPipelineVerList = PipelineVerElement.getChildNodes();

				// 5.OS Version
				NodeList OSVerList = UsersOnlineElement.getElementsByTagName("OSVersion");
				Element OSVerElement = (Element) OSVerList.item(0);
				NodeList textOSVerList = OSVerElement.getChildNodes();

				// 6.Connect Time
				NodeList ConnectTimeList = UsersOnlineElement.getElementsByTagName("ConnectTime");
				Element ConnectTimeElement = (Element) ConnectTimeList.item(0);
				NodeList textConnectTimeList = ConnectTimeElement.getChildNodes();

				// 7.Last Activity
				NodeList LastActivityList = UsersOnlineElement.getElementsByTagName("LastActivity");
				Element LastActivityElement = (Element) LastActivityList.item(0);
				NodeList textLastActivityList = LastActivityElement.getChildNodes();
				
				
				array[k] = new UsersOnlineRecord(
						((Node) textUsernameList.item(0)).getNodeValue().trim(),
						((Node) textIPAddressList.item(0)).getNodeValue().trim(),
						((Node) textPipelineList.item(0)).getNodeValue().trim(),
						((Node) textPipelineVerList.item(0)).getNodeValue().trim(),
						((Node) textOSVerList.item(0)).getNodeValue().trim(),
						((Node) textConnectTimeList.item(0)).getNodeValue().trim(),
						((Node) textLastActivityList.item(0)).getNodeValue().trim());

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
				if (fieldName.equals("disconnect")) {
					System.out.println("yes");
					IButton button = new IButton();
					button.setHeight(16);
					button.setWidth(70);
					button.setTitle("Disconnect");
					return button;
				}
				else{
					System.out.println("no");
					return null;
				}

			}
		}; // end of function
	}
}
