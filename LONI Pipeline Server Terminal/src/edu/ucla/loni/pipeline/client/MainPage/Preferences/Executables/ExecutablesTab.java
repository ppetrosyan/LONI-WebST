package edu.ucla.loni.pipeline.client.MainPage.Preferences.Executables;

import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class ExecutablesTab {
	
	public static Tab setTab() {
		Tab tabExecutables = new Tab("Executables");
		
		VLayout layoutUsersExecutables = new VLayout();
		layoutUsersExecutables.setSize("100%", "100%");
		layoutUsersExecutables.setMembersMargin(5);
		
		com.smartgwt.client.widgets.Label intro = new com.smartgwt.client.widgets.Label(
				"Please specify package name and version, the location, and optionally sources and variables. Double-click any cell to edit");
		intro.setSize("500px", "49px");
		layoutUsersExecutables.addMember(intro);
		
		final ListGrid listUsersExecutables = new ListGrid();
		listUsersExecutables.setCanEdit(true);  
		listUsersExecutables.setEditEvent(ListGridEditEvent.DOUBLECLICK);  
		listUsersExecutables.setListEndEditAction(RowEndEditAction.NEXT);
		listUsersExecutables.setSize("100%", "100%");
		listUsersExecutables.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersExecutables.setFields(
				new ListGridField("executables_name", "Executable Name"),
				new ListGridField("version", "Version"),
				new ListGridField("location", "Location")
			
		);
		//get data from ExecutablesData.java
		listUsersExecutables.setData(ExecutablesData.getRecords());  
		layoutUsersExecutables.addMember(listUsersExecutables);
		
		
		//Remove data 
	    IButton RemoveButton2 = new IButton("Remove");  
		RemoveButton2.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
		public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
		listUsersExecutables.removeSelectedData();
		}
		});
			
		        layoutUsersExecutables.addMember(RemoveButton2);
		        
		        //add data
		         IButton AddButton2 = new IButton("Add");  
		         AddButton2.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
		         	public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
		         		listUsersExecutables.startEditingNew(); 
		         	}
		         });
		         
	 
		         layoutUsersExecutables.addMember(AddButton2);  
			
		
		tabExecutables.setPane(layoutUsersExecutables);
		
		return tabExecutables;
	}
}
