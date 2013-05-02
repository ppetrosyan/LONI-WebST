package edu.ucla.loni.pipeline.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LONI_Pipeline_Server_Terminal implements EntryPoint {
	private ListGrid listWorkflows, listUsersOnline, listUsersUsage, listUsersUsageCount;
	
	/**
	 * This is the entry point method. 
	 * This is generated and managed by the visual designer.
	 */
	public void onModuleLoad() {
		final RootPanel rootPanel = RootPanel.get("rootPanel");
		rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);

		TabSet tabset = new TabSet();
		tabset.setSize("100%", "100%");
		
		Tab tabWorkflows = new Tab("Workflows");
		VLayout layoutWorkflows = new VLayout();
		layoutWorkflows.setSize("100%", "100%");
		layoutWorkflows.setMembersMargin(5);
		
		listWorkflows = new ListGrid();
		listWorkflows.setSize("100%", "100%");
		listWorkflows.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listWorkflows.setFields(
				new ListGridField("workflowID", "Workflow ID"),
				new ListGridField("username", "Username"),
				new ListGridField("state", "State"),
				new ListGridField("startTime", "Start Time"),
				new ListGridField("endTime", "End Time"),
				new ListGridField("duration", "Duration"),
				// TODO (Jeff): find out what some of fields mean
				new ListGridField("jobsN", "N"),
				new ListGridField("jobsI", "I"),
				new ListGridField("jobsB", "B"),
				new ListGridField("jobsS", "S"),
				new ListGridField("jobsQueued", "Q"),
				new ListGridField("jobsRunning", "R"),
				new ListGridField("jobsCompleted", "C"),
				new ListGridField("stop", "Stop/Reset"),
				new ListGridField("pause", "Pause/Rsm"),
				new ListGridField("view", "View")
		);
		
		layoutWorkflows.addMember(listWorkflows);
		
		Button exportWorkflows = new Button("Export to CSV...");
		layoutWorkflows.addMember(exportWorkflows);
		
		tabWorkflows.setPane(layoutWorkflows);
		tabset.addTab(tabWorkflows);
		
		Tab tabUsersOnline = new Tab("Users Online");
		
		VLayout layoutUsersOnline = new VLayout();
		layoutUsersOnline.setSize("100%", "100%");
		layoutUsersOnline.setMembersMargin(5);
		
		listUsersOnline = new ListGrid();
		listUsersOnline.setSize("100%", "100%");
		listUsersOnline.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersOnline.setFields(
				new ListGridField("username", "Username"),
				new ListGridField("ipAddress", "IP Address"),
				new ListGridField("pipelineInterface", "Pipeline Interface"),
				new ListGridField("pipelineVersion", "Pipeline Version"),
				new ListGridField("osVersion", "OS Version"),
				new ListGridField("connectTime", "Connect Time"),
				new ListGridField("lastActivity", "Last Activity"),
				new ListGridField("disconnect", "Disconnect")
		);
		layoutUsersOnline.addMember(listUsersOnline);
		
		Button exportUsersOnline = new Button("Export to CSV...");
		layoutUsersOnline.addMember(exportUsersOnline);
		tabUsersOnline.setPane(layoutUsersOnline);
		tabset.addTab(tabUsersOnline);
		
		Tab tabUsersUsage = new Tab("User Usage");
		
		VLayout layoutUsersUsage = new VLayout();
		layoutUsersUsage.setSize("100%", "100%");
		layoutUsersUsage.setMembersMargin(5);
		
		listUsersUsage = new ListGrid();
		listUsersUsage.setSize("100%", "50%");
		listUsersUsage.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersUsage.setFields(
				new ListGridField("username", "Username"),
				new ListGridField("workflowID", "Workflow ID"),
				new ListGridField("nodeName", "NodeName"),
				new ListGridField("instance", "Instance")
		);
		layoutUsersUsage.addMember(listUsersUsage);
		
		listUsersUsageCount = new ListGrid();
		listUsersUsageCount.setSize("50%", "50%");
		listUsersUsageCount.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersUsageCount.setFields(
				new ListGridField("username", "Username"),
				new ListGridField("count", "Count")
		);
		layoutUsersUsage.addMember(listUsersUsageCount);
		
		tabUsersUsage.setPane(layoutUsersUsage);
		tabset.addTab(tabUsersUsage);
		
		Tab tabMemoryUsage = new Tab("Memory Usage");
		tabset.addTab(tabMemoryUsage);
		
		Tab tabThreadUsage = new Tab("Thread Usage");
		tabset.addTab(tabThreadUsage);
		
		Tab tabPreferences = new Tab("Preferences");
		tabset.addTab(tabPreferences);
		rootPanel.add(tabset, 0, 0);
	}
}
