package edu.ucla.loni.pipeline.client.MainPage.WorkFlows;

import com.smartgwt.client.data.DataSource;
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
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class WorkFlowsTab {

	private ListGrid listWorkflows;

	public WorkFlowsTab() {
		initializeListWorkflows();
	}
	
	public Tab setTab() {
		Tab tabWorkflows = new Tab("Workflows");

		VLayout layoutWorkflows = new VLayout();
		layoutWorkflows.setSize("100%", "100%");
		layoutWorkflows.setDefaultLayoutAlign(Alignment.LEFT);
		layoutWorkflows.setMembersMargin(10);

		listWorkflows.setShowRecordComponents(true);
		listWorkflows.setShowRecordComponentsByCell(true);
		listWorkflows.setShowAllRecords(true);
		listWorkflows.setSize("100%", "100%");
		listWorkflows.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listWorkflows.setCanPickFields(false);
		listWorkflows.setCanFreezeFields(false);
		listWorkflows.setAutoFitFieldWidths(true);
		
		//specific which field you want to expend
		listWorkflows.setAutoFitExpandField("workflowID");

		// Need to declare these fields here so we can edit their behavior
		final ListGridField stopfield = new ListGridField("stop", "Stop/Reset&#160;&#160;&#160;&#160;&#160;");
		stopfield.setAlign(Alignment.CENTER);

		final ListGridField pausefield = new ListGridField("pause", "Pause/Rsm&#160;&#160;&#160;&#160;&#160;");
		pausefield.setAlign(Alignment.CENTER);

		final ListGridField viewfield = new ListGridField("view", "View&#160;&#160;&#160;&#160;&#160;");
		viewfield.setAlign(Alignment.CENTER);

		fillWorkFlowsTab(null, stopfield, pausefield, viewfield, listWorkflows);

		// reading directly from Xml file
		final DataSource workFlowsSource = WorkFlowsXmlDS.getInstance();
		workFlowsSource.setClientOnly(true);

		listWorkflows.setDataSource(workFlowsSource);
		listWorkflows.setAutoFetchData(true);
		layoutWorkflows.addMember(listWorkflows);

		tabWorkflows.addTabSelectedHandler(new TabSelectedHandler() {
			public void onTabSelected(TabSelectedEvent event) {
				fillWorkFlowsTab(workFlowsSource, stopfield, pausefield,
						viewfield, listWorkflows);
			}
		});

		Button workflowsrefreshbutton = new Button("Refresh");
		workflowsrefreshbutton.setAlign(Alignment.CENTER);
		layoutWorkflows.addMember(workflowsrefreshbutton);
		workflowsrefreshbutton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fillWorkFlowsTab(workFlowsSource, stopfield, pausefield,
						viewfield, listWorkflows);
			}
		});
		tabWorkflows.setPane(layoutWorkflows);
		return tabWorkflows;
	}

	private void fillWorkFlowsTab(DataSource workFlowsSource,
			ListGridField stopfield, ListGridField pausefield,
			ListGridField viewfield, ListGrid listWorkflows) {
		if (workFlowsSource != null) {
			workFlowsSource.invalidateCache();
			workFlowsSource = WorkFlowsXmlDS.getInstance();
			listWorkflows.setDataSource(workFlowsSource);
			listWorkflows.fetchData();
		}

		listWorkflows.setFields(new ListGridField("workflowID", "Workflow ID"),
				new ListGridField("username", "Username&#160;&#160;&#160;&#160;&#160;"), new ListGridField(
						"state", "State&#160;&#160;&#160;&#160;&#160;"), new ListGridField("startTime",
								"Start Time&#160;&#160;&#160;&#160;&#160;"),
								new ListGridField("endTime", "End Time&#160;&#160;&#160;&#160;&#160;"), new ListGridField(
										"duration", "Duration&#160;&#160;&#160;&#160;&#160;"), new ListGridField("numofnode",
												"N&#160;&#160;&#160;"), new ListGridField("numofinstances", "I&#160;&#160;&#160;"),
												new ListGridField("numBacklab", "B&#160;&#160;&#160;"), new ListGridField(
														"numSubmitting", "S&#160;&#160;&#160;"), new ListGridField("numQueued",
																"Q&#160;&#160;&#160;"), new ListGridField("numRunning", "R&#160;&#160;&#160;"),
																new ListGridField("numCompleted", "C&#160;&#160;&#160;"), stopfield, pausefield,
																viewfield);
	}
	
	private void initializeListWorkflows() {
		// this function displays the buttons in the ListGrids
		listWorkflows = new ListGrid() {
			@Override
			protected Canvas createRecordComponent(final ListGridRecord record,
					Integer colNum) {
				String fieldName = this.getFieldName(colNum);
				if (fieldName.equals("stop")) {
					IButton button = new IButton();
					button.setHeight(16);
					button.setWidth(65);
					button.setTitle("Stop");
					return button;
				} else if (fieldName.equals("pause")) {
					IButton button = new IButton();
					button.setHeight(16);
					button.setWidth(65);
					button.setTitle("Pause");

					return button;
				} else if (fieldName.equals("view")) {
					IButton button = new IButton();
					button.setHeight(16);
					button.setWidth(40);
					button.setTitle("View");
					return button;
				} else {
					return null;
				}
			}
		}; // end of function
	}
}
