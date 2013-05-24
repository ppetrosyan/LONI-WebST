package edu.ucla.loni.pipeline.client.MainPage.UsersOnline;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class UsersOnlineTab {
	
	private ListGrid listUsersOnline;
	
	public UsersOnlineTab() {
		listUsersOnline = new ListGrid();
	}
	
	public Tab setTab() {
		Tab tabUsersOnline = new Tab("Users Online");

		VLayout layoutUsersOnline = new VLayout();
		layoutUsersOnline.setSize("100%", "100%");
		layoutUsersOnline.setMembersMargin(10);

		listUsersOnline.setSize("100%", "100%");
		listUsersOnline.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersOnline.setCanPickFields(false);
		listUsersOnline.setCanFreezeFields(false);
		listUsersOnline.setAutoFitFieldWidths(true);
		
		//specific which field you want to expend
		listUsersOnline.setAutoFitExpandField("pipelineInterface");

		fillUsersOnlineTab(null, listUsersOnline);

		// reading directly from Xml file
		final DataSource usersOnlineSource = UsersOnlineXmlDS.getInstance();
		usersOnlineSource.setClientOnly(true);

		listUsersOnline.setDataSource(usersOnlineSource);
		listUsersOnline.setAutoFetchData(true);
		layoutUsersOnline.addMember(listUsersOnline);

		tabUsersOnline.addTabSelectedHandler(new TabSelectedHandler() {
			public void onTabSelected(TabSelectedEvent event) {
				fillUsersOnlineTab(usersOnlineSource, listUsersOnline);
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
				fillUsersOnlineTab(usersOnlineSource, listUsersOnline);
			}
		});

		Button exportUsersOnline = new Button("Export to CSV...");
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
	
	private void fillUsersOnlineTab(DataSource usersOnlineSource, ListGrid listUsersOnline) {
		if (usersOnlineSource != null) {
			usersOnlineSource.invalidateCache();
			usersOnlineSource = UsersOnlineXmlDS.getInstance();
			listUsersOnline.setDataSource(usersOnlineSource);
			listUsersOnline.fetchData();
		}

		listUsersOnline.setFields(new ListGridField("username", "Username"),
				new ListGridField("ipAddress", "IP Address"),
				new ListGridField("pipelineInterface", "Pipeline Interface"),
				new ListGridField("pipelineVersion", "Pipeline Version"),
				new ListGridField("osVersion", "OS Version"),
				new ListGridField("connectTime", "Connect Time"),
				new ListGridField("lastActivity", "Last Activity"),
				new ListGridField("disconnect", "Disconnect"));
	}
}
