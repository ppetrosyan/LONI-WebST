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
	
	private IButton createStopResetButton(){
		IButton button = new IButton();
		button.setHeight(16);
		button.setWidth(65);
		//change title after created for reset button
		button.setTitle("Stop");
		return button;
	}
	
	private IButton createPauseResumeButton(){
		IButton button = new IButton();
		button.setHeight(16);
		button.setWidth(65);
		//change title after created for resume button
		button.setTitle("Pause");
		return button;
	}
	
	private IButton createViewButton(){
		IButton button = new IButton();
		button.setHeight(16);
		button.setWidth(40);
		button.setTitle("View");
		return button;
	}
	
	private void initializeListWorkflows() {
		// this function displays the buttons in the ListGrids
		listWorkflows = new ListGrid() {
			@Override
			protected Canvas createRecordComponent(final ListGridRecord record,
					Integer colNum) {
				String fieldName = this.getFieldName(colNum);
				String state = record.getAttribute("state");
				
				//State: Running
				//show Stop,Pause and View buttons
				if(state.equals("Running")){
					if (fieldName.equals("stop")) {
						IButton button = createStopResetButton();
						return button;
						
					} else if (fieldName.equals("pause")) {
						IButton button = createPauseResumeButton();
						return button;
						
					} else if (fieldName.equals("view")) {
						IButton button = createViewButton();
						return button;
						
					} else {
						return null;
						
					}
				}
				//State: Stop
				//Show Reset,Pause and View button, disable Pause button.
				else if(state.equals("Stop")){
					if (fieldName.equals("stop")) {
						IButton button = createStopResetButton();
						button.setTitle("Reset");
						return button;
							
					} else if (fieldName.equals("pause")) {
						IButton button = createPauseResumeButton();
						button.setDisabled(true);
						return button;
							
					} else if (fieldName.equals("view")) {
						IButton button = createViewButton();
						return button;
							
					} else {
						return null;
							
					}
				}
				//State: Backlog
				//Show Stop, Pause and View buttons, disable pause button.
				else if(state.equals("Backlog")){
					if (fieldName.equals("stop")) {
						IButton button = createStopResetButton();
						return button;
							
					} else if (fieldName.equals("pause")) {
						IButton button = createPauseResumeButton();
						button.setDisabled(true);
						return button;
							
					} else if (fieldName.equals("view")) {
						IButton button = createViewButton();
						return button;
							
					} else {
						return null;
							
					}
				}
				//State: Pause
				//Show Reset, Resume and View buttons
				else if(state.equals("Pause")){
					if (fieldName.equals("stop")) {
						IButton button = createStopResetButton();
						button.setTitle("Reset");
						return button;
							
					} else if (fieldName.equals("pause")) {
						IButton button = createPauseResumeButton();
						button.setTitle("Resume");
						return button;
							
					} else if (fieldName.equals("view")) {
						IButton button = createViewButton();
						return button;
							
					} else {
						return null;
							
					}
				}
				else{
					return null;
				}
				
			}
		}; // end of function
	}
}
