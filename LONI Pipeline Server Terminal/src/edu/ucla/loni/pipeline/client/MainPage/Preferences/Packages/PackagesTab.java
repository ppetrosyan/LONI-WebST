package edu.ucla.loni.pipeline.client.MainPage.Preferences.Packages;

import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class PackagesTab {
	
	public PackagesTab() {
		
	}
	
	public Tab setTab() {
		Tab tabPackages = new Tab("Packages");

		VLayout layoutUsersPackages = new VLayout();
		layoutUsersPackages.setSize("100%", "100%");
		layoutUsersPackages.setMembersMargin(5);
		
		com.smartgwt.client.widgets.Label intro = new com.smartgwt.client.widgets.Label(
				"Please specify package name and version, the location, and optionally sources and variables. Double-click any cell to edit");
		intro.setSize("500px", "49px");
		layoutUsersPackages.addMember(intro);

		
		final ListGrid listUsersPackages = new ListGrid();
		
		listUsersPackages.setCanEdit(true);  
		listUsersPackages.setEditEvent(ListGridEditEvent.DOUBLECLICK);  
		listUsersPackages.setListEndEditAction(RowEndEditAction.NEXT);
		listUsersPackages.setSize("100%", "100%");
		listUsersPackages.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersPackages.setCanPickFields(false);
		listUsersPackages.setCanFreezeFields(false);
		listUsersPackages.setAutoFitFieldWidths(true);
		listUsersPackages.setCanRemoveRecords(true); 
		
		
		//specific which field you want to expend
		listUsersPackages.setAutoFitExpandField("location");
		
		listUsersPackages.setFields(new ListGridField("package_name","Package Name&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
				new ListGridField("version", "Version&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
				new ListGridField("location", "Location"), 
				new ListGridField("variables", "Variables&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"), 
				new ListGridField("sources","Sources&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;")

		);
		// get data from PackagesData.java
		listUsersPackages.setData(PackagesData.getRecords());
		layoutUsersPackages.addMember(listUsersPackages);
		
		//Hlayout
		// horizontal layout
		HLayout hLayout1 = new HLayout();
		hLayout1.setMembersMargin(10);

		// add data
		IButton AddButton = new IButton("Add");
		AddButton.setIcon("addicon.png"); 
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
		RemoveButton.setIcon(listUsersPackages.getRemoveIcon()); 
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
}
