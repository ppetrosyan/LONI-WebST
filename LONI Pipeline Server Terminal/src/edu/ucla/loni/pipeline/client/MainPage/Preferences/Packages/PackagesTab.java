package edu.ucla.loni.pipeline.client.MainPage.Preferences.Packages;

import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class PackagesTab {
	
	public static Tab setTab() {
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
		listUsersPackages.setFields(new ListGridField("package_name",
				"Package Name"), new ListGridField("version", "Version"),
				new ListGridField("location", "Location"), new ListGridField(
						"variables", "Variables"), new ListGridField("sources",
						"Sources")

		);
		// get data from PackagesData.java
		listUsersPackages.setData(PackagesData.getRecords());
		layoutUsersPackages.addMember(listUsersPackages);

		// Remove data
		IButton RemoveButton = new IButton("Remove");
		RemoveButton
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						listUsersPackages.removeSelectedData();
					}
				});

		layoutUsersPackages.addMember(RemoveButton);

		// add data
		IButton AddButton = new IButton("Add");
		AddButton
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						listUsersPackages.startEditingNew();
					}
				});

		layoutUsersPackages.addMember(AddButton);

		tabPackages.setPane(layoutUsersPackages);

		return tabPackages;
	}
}
