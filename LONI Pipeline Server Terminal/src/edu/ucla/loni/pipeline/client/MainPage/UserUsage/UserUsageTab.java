package edu.ucla.loni.pipeline.client.MainPage.UserUsage;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class UserUsageTab {
	private ListGrid listUserUsage, listUserUsageCount;
	
	public UserUsageTab() {
		listUserUsage = new ListGrid();
		listUserUsageCount = new ListGrid();
	}
	
	public Tab setTab() {
		Tab tabUserUsage = new Tab("User Usage");

		VLayout layoutUserUsage = new VLayout();
		layoutUserUsage.setSize("100%", "100%");
		layoutUserUsage.setDefaultLayoutAlign(Alignment.CENTER);
		layoutUserUsage.setMembersMargin(10);

		listUserUsage.setSize("100%", "50%");
		listUserUsage.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUserUsage.setCanPickFields(false);
		listUserUsage.setCanFreezeFields(false);
		listUserUsage.setAutoFitFieldWidths(true);


		listUserUsageCount.setSize("50%", "50%");
		listUserUsageCount.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUserUsageCount.setCanPickFields(false);
		listUserUsageCount.setCanFreezeFields(false);
		listUserUsageCount.setAutoFitFieldWidths(true);

		fillUserUsageTab(null, null);

		// reading directly from Xml file
		final DataSource userUsageSource = UserUsageXmlDS.getInstance();
		userUsageSource.setClientOnly(true);
		listUserUsage.setDataSource(userUsageSource);
		listUserUsage.setAutoFetchData(true);
		layoutUserUsage.addMember(listUserUsage);

		// reading directly from Xml file
		final DataSource userUsageCountSource = UserUsageCountXmlDS
				.getInstance();
		userUsageCountSource.setClientOnly(true);
		listUserUsageCount.setDataSource(userUsageCountSource);
		listUserUsageCount.setAutoFetchData(true);
		layoutUserUsage.addMember(listUserUsageCount);

		Button userusagerefreshbutton = new Button("Refresh");
		userusagerefreshbutton.setAlign(Alignment.CENTER);
		layoutUserUsage.addMember(userusagerefreshbutton);
		userusagerefreshbutton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fillUserUsageTab(userUsageSource, userUsageCountSource);
			}
		});

		tabUserUsage.setPane(layoutUserUsage);
		
		return tabUserUsage;
	}
	
	public void fillUserUsageTab(DataSource userUsageSource,
			DataSource userUsageCountSource) {
		if (userUsageSource != null) {
			userUsageSource.invalidateCache();
			userUsageSource = UserUsageXmlDS.getInstance();
			listUserUsage.setDataSource(userUsageSource);
			listUserUsage.fetchData();
		}

		if (userUsageCountSource != null) {
			userUsageCountSource.invalidateCache();
			userUsageCountSource = UserUsageCountXmlDS.getInstance();
			listUserUsageCount.setDataSource(userUsageCountSource);
			listUserUsageCount.fetchData();
		}

		listUserUsage.setFields(new ListGridField("username", "Username"),
				new ListGridField("workflowID", "Workflow ID"),
				new ListGridField("nodeName", "NodeName"), new ListGridField(
						"instance", "Instance"));

		listUserUsageCount.setFields(new ListGridField("username", "Username"),
				new ListGridField("count", "Count"));
	}
}
