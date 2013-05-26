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

public class UserUsageTab {
	private ListGrid listUserUsage, listUserUsageCount;
	private AsyncClientServices asyncClientServices;
	private int TotalWorkflows;
	
	public UserUsageTab(AsyncClientServices asyncClientServices) {
		listUserUsage = new ListGrid();
		listUserUsageCount = new ListGrid();
		TotalWorkflows = 0;
		this.asyncClientServices = asyncClientServices;
	}
	
	public Tab setTab() {
		Tab tabUserUsage = new Tab("User Usage");		

		VLayout layoutUserUsage = new VLayout();
		layoutUserUsage.setSize("100%", "100%");
		layoutUserUsage.setDefaultLayoutAlign(Alignment.CENTER);
		layoutUserUsage.setMembersMargin(10);	
		
		VLayout layoutintro = new VLayout();
		layoutintro.setDefaultLayoutAlign(Alignment.LEFT);
		
		//Label that show statistic
		final com.smartgwt.client.widgets.Label intro = new com.smartgwt.client.widgets.Label(
				"Loading...");
		intro.setSize("500px", "49px");
		layoutintro.addMember(intro);		
		layoutUserUsage.addMember(layoutintro);

		//List UserUsage
		listUserUsage.setSize("100%", "50%");
		listUserUsage.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUserUsage.setCanPickFields(false);
		listUserUsage.setCanFreezeFields(false);
		listUserUsage.setAutoFitFieldWidths(true);
		
		//specific which field you want to expend
		listUserUsage.setAutoFitExpandField("workflowID");
		
		listUserUsage.setFields(new ListGridField("username", "Username&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
				new ListGridField("workflowID", "Workflow ID"),
				new ListGridField("nodeName", "NodeName&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"), 
				new ListGridField("instance", "Instance&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"));

		//List UserUsageCount
		listUserUsageCount.setSize("50%", "50%");
		listUserUsageCount.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUserUsageCount.setCanPickFields(false);
		listUserUsageCount.setCanFreezeFields(false);
		listUserUsageCount.setAutoFitFieldWidths(true);
		
		//specific which field you want to expend
		listUserUsageCount.setAutoFitExpandField("username");
		
		listUserUsageCount.setFields(new ListGridField("username", "Username"),
				new ListGridField("count", "Count&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"));
		
		layoutUserUsage.addMember(listUserUsage);
		layoutUserUsage.addMember(listUserUsageCount);
		
		tabUserUsage.addTabSelectedHandler(new TabSelectedHandler() {
			public void onTabSelected(TabSelectedEvent event) {				
				Update(intro);
			}
		});
		
		VLayout layoutbotton = new VLayout();

		Button userusagerefreshbutton = new Button("Refresh");
		userusagerefreshbutton.setAlign(Alignment.CENTER);
		layoutbotton.addMember(userusagerefreshbutton);
		userusagerefreshbutton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Update(intro);
			}
		});
		
		layoutUserUsage.addMember(layoutbotton);
		tabUserUsage.setPane(layoutUserUsage);
		
		return tabUserUsage;
	}
	
	public void Update(final com.smartgwt.client.widgets.Label intro){
		asyncClientServices.reqResourceXMLService.getXMLData(new AsyncCallback<String>() {
			@Override
			public void onSuccess(final String xmlData) {
				refreshUserUsage(xmlData);
				refreshUserUsageCount(xmlData);
				System.out.println("UserUsage refreshed successfully");
				
				//get current time with specific format
				Date time = new Date();
				DateTimeFormat ft = DateTimeFormat.getFormat("EEE MMM d HH:mm:ss ZZZZ yyyy");
			    
			  //Update the content of the top label
				intro.setContents("UserUsage ( " + TotalWorkflows + "&#160;) "
			    + "&#160;&#160;&#160;Updated: " + ft.format(time));
			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("UserUsage refreshed failed");
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
			System.err.println("Could not parse XML file. Check XML file format.");
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
			System.err.println("Could not parse XML file. Check XML file format.");
			return;
		}
	}
	
	public void parseUserUsageXML(Document doc) {
		NodeList UserUsageList = doc.getElementsByTagName("UserUsageEntry");
		//get this total workflows here for display
		TotalWorkflows = UserUsageList.getLength();
		
		UserUsageRecord array[] = new UserUsageRecord[TotalWorkflows];
		for (int k = 0; k < TotalWorkflows; k++) {
			Node UserUsageNode = UserUsageList.item(k);
			if (UserUsageNode.getNodeType() == Node.ELEMENT_NODE) {
				Element UserUsageElement = (Element) UserUsageNode;

				// Username
				NodeList UsernameList = UserUsageElement.getElementsByTagName("Username");
				Element UsernameElement = (Element) UsernameList.item(0);
				NodeList textUsernameList = UsernameElement.getChildNodes();

				// WorkflowID
				NodeList WorkflowIDList = UserUsageElement.getElementsByTagName("WorkflowID");
				Element WorkflowIDElement = (Element) WorkflowIDList.item(0);
				NodeList textWorkflowIDList = WorkflowIDElement.getChildNodes();

				// NodeName
				NodeList NodeNameList = UserUsageElement.getElementsByTagName("NodeName");
				Element NodeNameElement = (Element) NodeNameList.item(0);
				NodeList textNodeNameList = NodeNameElement.getChildNodes();

				// Instance
				NodeList InstanceList = UserUsageElement.getElementsByTagName("Instance");
				Element InstanceElement = (Element) InstanceList.item(0);
				NodeList textInstanceList = InstanceElement.getChildNodes();

				array[k] = new UserUsageRecord(
						((Node) textUsernameList.item(0)).getNodeValue().trim(),
						((Node) textWorkflowIDList.item(0)).getNodeValue().trim(),
						((Node) textNodeNameList.item(0)).getNodeValue().trim(),
						((Node) textInstanceList.item(0)).getNodeValue().trim());
			}// end if
		}// end loop
		listUserUsage.setData(array);
	}
	
	public void parseUserUsageCountXML(Document doc) {
		NodeList UserUsageCountList = doc.getElementsByTagName("UserUsageCountEntry");
		int TotalUserUsageCount = UserUsageCountList.getLength();
		
		UserUsageCountRecord array[] = new UserUsageCountRecord[TotalUserUsageCount];
		for (int k = 0; k < TotalUserUsageCount; k++) {
			Node UserUsageCountNode = UserUsageCountList.item(k);
			if (UserUsageCountNode.getNodeType() == Node.ELEMENT_NODE) {
				Element UserUsageCountElement = (Element) UserUsageCountNode;

				// Username
				NodeList UsernameList = UserUsageCountElement.getElementsByTagName("Username");
				Element UsernameElement = (Element) UsernameList.item(0);
				NodeList textUsernameList = UsernameElement.getChildNodes();

				// Count
				NodeList CountList = UserUsageCountElement.getElementsByTagName("Count");
				Element CountElement = (Element) CountList.item(0);
				NodeList textCountList = CountElement.getChildNodes();

				array[k] = new UserUsageCountRecord(
						((Node) textUsernameList.item(0)).getNodeValue().trim(),
						((Node) textCountList.item(0)).getNodeValue().trim());
			}// end if
		}// end loop
		listUserUsageCount.setData(array);
	}
}
