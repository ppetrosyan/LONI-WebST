package edu.ucla.loni.pipeline.client.MainPage.Preferences.Packages;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

import edu.ucla.loni.pipeline.client.MainPage.UsersOnline.UsersOnlineXmlDS;
 
public class PackagesTab {
	private ListGrid listUsersPackages = new ListGrid();
	VLayout layoutUsersPackages = new VLayout();
	
	public ListGrid getGridEnable() {
		return listUsersPackages;
	}
	
	
	public PackagesTab() {
		
	}
	
	
	public Tab setTab() {
		Tab tabPackages = new Tab("Packages");

		
		layoutUsersPackages.setSize("100%", "100%");
		layoutUsersPackages.setMembersMargin(5);
		
		com.smartgwt.client.widgets.Label intro = new com.smartgwt.client.widgets.Label(
				"Please specify package name and version, the location, and optionally sources and variables. Double-click any cell to edit");
		intro.setSize("500px", "49px");
		layoutUsersPackages.addMember(intro);

		
		
		listUsersPackages.setCanEdit(true);  
		listUsersPackages.setEditEvent(ListGridEditEvent.DOUBLECLICK);  
		listUsersPackages.setListEndEditAction(RowEndEditAction.NEXT);
		listUsersPackages.setSize("100%", "100%");
		listUsersPackages.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersPackages.setFields(new ListGridField("package_name",
				"Package Name"), new ListGridField("version", "Version"),
				new ListGridField("location", "Location"), new ListGridField(
						"variables", "Variables"), new ListGridField("sources",
						"Sources")
		);
	
		
		
		final DataSource PackagesSource = UsersOnlineXmlDS.getInstance();
		listUsersPackages.setDataSource(PackagesSource);
	   
		
		
		
		//get data from PackagesData.java
		listUsersPackages.setData(PackagesData.getRecords());
	    layoutUsersPackages.addMember(listUsersPackages);
		
		//Hlayout
		// horizontal layout
		HLayout hLayout1 = new HLayout();
		hLayout1.setMembersMargin(10);

		// add data
		IButton AddButton = new IButton("Add");
		hLayout1.addMember(AddButton);
		AddButton
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						listUsersPackages.startEditingNew();
					}
				});
		
		// Remove data
		IButton RemoveButton = new IButton("Remove");
		hLayout1.addMember(RemoveButton);
		RemoveButton
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						listUsersPackages.removeSelectedData();
					}
				});

		
		
		layoutUsersPackages.addMember(hLayout1);
		tabPackages.setPane(layoutUsersPackages);

		return tabPackages;
	}

	public void parsePackageXML(Document doc){
			NodeList PackagesList = doc.getElementsByTagName("Packages");
	        int totalPackages = PackagesList.getLength();
	        PackagesRecord array[] = new PackagesRecord[totalPackages];
	        for(int k = 0; k < totalPackages ; k++){
	        	Node PackageNode = PackagesList.item(k);
	            if(PackageNode.getNodeType() == Node.ELEMENT_NODE){
	            	Element PackagesElement = (Element)PackageNode;
	            	
	            	//PackageName
	            	NodeList PkgList = PackagesElement.getElementsByTagName("PackageName");
	            	Element PkgElement = (Element)PkgList.item(0);	                   
	            	NodeList textPkgList = PkgElement.getChildNodes();
	                
	            	//Version
	            	NodeList VerList =PackagesElement.getElementsByTagName("Version");
	            	Element VerElement = (Element)VerList.item(0);
	            	NodeList textVerList = VerElement.getChildNodes();
	                 
	            	//Location
	            	NodeList LocList =PackagesElement.getElementsByTagName("Location");
	            	Element ageElement = (Element)LocList.item(0);	          
	            	NodeList textLocList = ageElement.getChildNodes();

	            	//Variables
	            	NodeList VarList =PackagesElement.getElementsByTagName("Variables");
	            	Element VarElement = (Element)VarList.item(0);	             
	            	NodeList textVarList = VarElement.getChildNodes();

	            	//Location
	            	NodeList SrcList =PackagesElement.getElementsByTagName("Sources");
	            	Element SrcElement = (Element)SrcList.item(0);
	            	NodeList textSrcList = SrcElement.getChildNodes();
	            	
	            	array[k]= new PackagesRecord( ((Node)textPkgList.item(0)).getNodeValue().trim(),
	                    		 ((Node)textVerList.item(0)).getNodeValue().trim(), 
	                    		 ((Node)textLocList.item(0)).getNodeValue().trim(), 
	                    		 ((Node)textVarList.item(0)).getNodeValue().trim(), 
	                    		 ((Node)textSrcList.item(0)).getNodeValue().trim());
	            }//end if
	        }//end loop
	        listUsersPackages.setData(array);	
	        layoutUsersPackages.addMember(listUsersPackages);
	}

}
