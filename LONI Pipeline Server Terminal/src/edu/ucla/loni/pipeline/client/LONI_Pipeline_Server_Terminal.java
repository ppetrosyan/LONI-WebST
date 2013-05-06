package edu.ucla.loni.pipeline.client;

import java.util.LinkedHashMap;
import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.NativeCheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.UploadItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.types.Alignment; 

import edu.ucla.loni.pipeline.client.Charts.LONI_Chart;
import edu.ucla.loni.pipeline.client.UploadFeatures.LONIDragandDropLabel;
import edu.ucla.loni.pipeline.client.Uploaders.ConfigurationUploader;
import edu.ucla.loni.pipeline.client.Uploaders.SimulatedDataUploader;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LONI_Pipeline_Server_Terminal implements EntryPoint {
	private ListGrid listWorkflows, listUsersOnline, listUsersUsage,
			listUsersUsageCount;
	
	private void formatForm(DynamicForm form)
	{
		form.setTitleWidth(200);
		for(FormItem i : form.getFields())
			i.setTitleAlign(Alignment.LEFT);
	}
	
	/**
	 * This is the entry point method. This is generated and managed by the
	 * visual designer.
	 */
	public void onModuleLoad() {
		
		//The following function causes an error on gwt designer.
		//Please uncomment it when not using gwt designer.
		//And also comment out the following line(around line 125):
		//        listWorkflows = new ListGrid();
		
		/*
		//Start of function
		//this function displays the buttons in the ListGrids
		final ListGrid listWorkflows = new ListGrid() {  
            @Override  
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {  
  
                String fieldName = this.getFieldName(colNum);  
  
                if (fieldName.equals("stop")) {  
                    IButton button = new IButton();  
                    button.setHeight(18);  
                    button.setWidth(75);                        
                    button.setTitle("Stop/Reset");  
                    //insert a click handler here
                    //we are using gwt click handler here
                    return button;  
                } else if(fieldName.equals("pause")){  
                	IButton button = new IButton();  
                    button.setHeight(18);  
                    button.setWidth(65);                        
                    button.setTitle("Pause");  
                    //insert a click handler here
                    //we are using gwt click handler here
                    return button; 
                } else if(fieldName.equals("view")){  
                	IButton button = new IButton();  
                    button.setHeight(18);  
                    button.setWidth(65);                        
                    button.setTitle("View");  
                    //insert a click handler here
                    //we are using gwt click handler here
                    return button; 
                } else{
                	return null;
                }  
            }  
        };  //end of function
		*/
		
		TabSet tabset = new TabSet();
		tabset.setSize("100%", "100%");
		tabset.setPaneMargin(30);

		Tab tabWorkflows = new Tab("Workflows");

		VLayout layoutWorkflows = new VLayout();
		layoutWorkflows.setSize("100%", "100%");
		layoutWorkflows.setDefaultLayoutAlign(Alignment.CENTER);
		layoutWorkflows.setMembersMargin(20);

		
		//work flows tab
		listWorkflows = new ListGrid();
		listWorkflows.setShowRecordComponents(true); 
		listWorkflows.setShowRecordComponentsByCell(true);
		listWorkflows.setShowAllRecords(true); 
		listWorkflows.setSize("100%", "100%");
		listWorkflows.setCellPadding(2);
		listWorkflows.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		
		//Need to declare these fields here so we can edit their behavior
		ListGridField stopfield = new ListGridField("stop", "Stop/Reset");
		stopfield.setAlign(Alignment.CENTER); 
		
		ListGridField pausefield = new ListGridField("pause","Pause/Rsm");
		pausefield.setAlign(Alignment.CENTER); 
		
		ListGridField viewfield = new ListGridField("view", "View");
		viewfield.setAlign(Alignment.CENTER); 
		
		listWorkflows.setFields(new ListGridField("workflowID", "Workflow ID"),
								new ListGridField("username", "Username"), 
								new ListGridField("state", "State"), 
								new ListGridField("startTime", "Start Time"),
								new ListGridField("endTime", "End Time"),
								new ListGridField("duration", "Duration"),
								new ListGridField("numofnode","N"), 
								new ListGridField("numofinstances", "I"),
								new ListGridField("numBacklab", "B"),
								new ListGridField("numSubmitting", "S"), 
								new ListGridField("numQueued",	"Q"), 
								new ListGridField("numRunning", "R"),
								new ListGridField("numCompleted", "C"), 
								stopfield,
								pausefield, 
								viewfield);
		
		
		
		// input data into the list.
		//method 1 - reading strings
		//listWorkflows.setData(WorkFlowsData.getRecords());
		
		//method 2 - reading directly from Xml file
		listWorkflows.setDataSource(WorkFlowsXmlDS.getInstance());
		listWorkflows.setAutoFetchData(true); 
		
		layoutWorkflows.addMember(listWorkflows);		
		listWorkflows.moveTo(30, 0);

		tabWorkflows.setPane(layoutWorkflows);
		tabset.addTab(tabWorkflows);
		//end work flows tab

		//Users online tab
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
				new ListGridField("disconnect", "Disconnect"));
		
		//get data from OnlineData.java
		listUsersOnline.setData(OnlineData.getRecords());  
		
		layoutUsersOnline.addMember(listUsersOnline);

		Button exportUsersOnline = new Button("Export to CSV...");
		layoutUsersOnline.addMember(exportUsersOnline);
		tabUsersOnline.setPane(layoutUsersOnline);
		tabset.addTab(tabUsersOnline);
		//end Users online tab
		
		Tab tabUsersUsage = new Tab("User Usage");

		VLayout layoutUsersUsage = new VLayout();
		layoutUsersUsage.setSize("100%", "100%");
		layoutUsersUsage.setDefaultLayoutAlign(Alignment.CENTER); // Horizontal
																	// centering
		layoutUsersUsage.setMembersMargin(5);

		listUsersUsage = new ListGrid();
		listUsersUsage.setSize("100%", "50%");
		listUsersUsage.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersUsage.setFields(
				new ListGridField("username", "Username"),
				new ListGridField("workflowID", "Workflow ID"),
				new ListGridField("nodeName", "NodeName"), 
				new ListGridField("instance", "Instance"));
		layoutUsersUsage.addMember(listUsersUsage);

		listUsersUsageCount = new ListGrid();
		listUsersUsageCount.setSize("50%", "50%");
		listUsersUsageCount.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersUsageCount.setFields(
				new ListGridField("username", "Username"), new ListGridField(
						"count", "Count"));
		layoutUsersUsage.addMember(listUsersUsageCount);

		tabUsersUsage.setPane(layoutUsersUsage);
		tabset.addTab(tabUsersUsage);

	    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable e) {
				// TODO Auto-generated method stub
				
			}
	    });
	    
		Tab tabMemoryUsage = new Tab("Memory Usage");

		final LONI_Chart memChart = new LONI_Chart("Memory");
		tabMemoryUsage.addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {
				memChart.getChart().redraw();
			}
		});

		tabMemoryUsage.setPane(memChart);

		tabset.addTab(tabMemoryUsage);

		Tab tabThreadUsage = new Tab("Thread Usage");

		final LONI_Chart thrdChart = new LONI_Chart("Thread");
		tabThreadUsage.addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {
				thrdChart.getChart().redraw();
			}
		});

		tabThreadUsage.setPane(thrdChart);

		tabset.addTab(tabThreadUsage);

		//preferences tab
		Tab tabPreferences = new Tab("Preferences");
		
		TabSet tabSet = new TabSet();
		
		Tab tabGeneral = new Tab("General");
		
		VLayout layoutGeneral = new VLayout();
		
		com.smartgwt.client.widgets.Label labelGeneralBasic = new com.smartgwt.client.widgets.Label("Basic");
		labelGeneralBasic.setSize("69px", "17px");
		layoutGeneral.addMember(labelGeneralBasic);
		
		DynamicForm formGeneralBasic = new DynamicForm();
		NativeCheckboxItem nativeCheckboxItem = new NativeCheckboxItem();
		nativeCheckboxItem.setTitle("Use privilege escalation: Pipeline server will run commands as the user (sudo as user)");
		NativeCheckboxItem nativeCheckboxItem_1 = new NativeCheckboxItem();
		nativeCheckboxItem_1.setTitle("Enable guests");
		NativeCheckboxItem nativeCheckboxItem_2 = new NativeCheckboxItem();
		nativeCheckboxItem_2.setTitle("Secure");
		IntegerItem basicPort = new IntegerItem("newTextItem_4", "Port");
		basicPort.setValue("8001");
		formGeneralBasic.setFields(new FormItem[] { new TextItem("newTextItem_1", "Host"), basicPort, new UploadItem("newUploadItem_5", "Temporary Directory"), nativeCheckboxItem_2, new UploadItem("newUploadItem_7", "Scratch Directory"), new UploadItem("newUploadItem_4", "Log File"), nativeCheckboxItem, nativeCheckboxItem_1});
		formatForm(formGeneralBasic);
		layoutGeneral.addMember(formGeneralBasic);
		formGeneralBasic.moveTo(100, 17);
		
		com.smartgwt.client.widgets.Label labelGeneralPersistence = new com.smartgwt.client.widgets.Label("Persistence");
		labelGeneralPersistence.setSize("69px", "17px");
		layoutGeneral.addMember(labelGeneralPersistence);
		
		DynamicForm formGeneralPersistence = new DynamicForm();
		SpinnerItem spinnerItem = new SpinnerItem("newSpinnerItem_4", "Session Time-to-live");
		spinnerItem.setValue(30);
		StaticTextItem staticTextItem = new StaticTextItem("newStaticTextItem_5", "");
		staticTextItem.setValue("(days from the end of this session)");
		formGeneralPersistence.setFields(new FormItem[] { new TextItem("newTextItem_10", "URL"), new TextItem("newTextItem_11", "Username"), new PasswordItem("newTextItem_12", "Password"), spinnerItem, staticTextItem, new UploadItem("newUploadItem_15", "History Directory"), new TextItem("newTextItem_13", "Crawler Persistence URL")});
		formatForm(formGeneralPersistence);
		layoutGeneral.addMember(formGeneralPersistence);
		tabGeneral.setPane(layoutGeneral);
		tabSet.addTab(tabGeneral);
		
		Tab tabGrid = new Tab("Grid");
		tabSet.addTab(tabGrid);
		
		Tab tabAccess = new Tab("Access");
		tabSet.addTab(tabAccess);
		
		Tab tabPackages = new Tab("Packages");
		tabSet.addTab(tabPackages);
		
		Tab tabExecutables = new Tab("Executables");
		tabSet.addTab(tabExecutables);
		
		Tab tabAdvanced = new Tab("Advanced");
		tabSet.addTab(tabAdvanced);
		tabPreferences.setPane(tabSet);
		tabset.addTab(tabPreferences);
		//end preferences tab

		Tab tabUpload = new Tab("Upload");
		createUploadTab(tabUpload);
		tabset.addTab(tabUpload);

		tabset.draw();
	}

	public void createUploadTab(Tab tabUpload) {
		final VerticalPanel configurationPanel = new VerticalPanel();
		final VerticalPanel simulatedDataPanel = new VerticalPanel();
		final Map<String, Image> cancelButtons = new LinkedHashMap<String, Image>();
		final ConfigurationUploader configurationUploader = new ConfigurationUploader(cancelButtons, configurationPanel);
		final SimulatedDataUploader simulatedDataUploader = new SimulatedDataUploader(cancelButtons, simulatedDataPanel);
		
		
		if (Uploader.isAjaxUploadWithProgressEventsSupported()) {
			final LONIDragandDropLabel configurationLabel = new LONIDragandDropLabel("Drop Configuration File", configurationUploader, cancelButtons, configurationPanel);
			configurationPanel.add(configurationLabel);
		}
		configurationPanel.add(configurationUploader);

		if (Uploader.isAjaxUploadWithProgressEventsSupported()) {
			final LONIDragandDropLabel simulatedDataLabel = new LONIDragandDropLabel("Drop Simulated Data", simulatedDataUploader, cancelButtons, simulatedDataPanel);
			simulatedDataPanel.add(simulatedDataLabel);
		}
		simulatedDataPanel.add(simulatedDataUploader);


		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.add(configurationPanel);
		horizontalPanel.add(simulatedDataPanel);

		// horizontalPanel.setCellHorizontalAlignment(configurationPanel,
		// 		HorizontalPanel.ALIGN_CENTER);
		// horizontalPanel.setCellHorizontalAlignment(configurationUploader,
		// 		HorizontalPanel.ALIGN_CENTER);
		// horizontalPanel.setCellHorizontalAlignment(simulatedDataPanel,
		// 		HorizontalPanel.ALIGN_CENTER);
		// horizontalPanel.setCellHorizontalAlignment(simulatedDataUploader,
		// 		HorizontalPanel.ALIGN_CENTER);
		// horizontalPanel.setCellHorizontalAlignment(progressBarPanel,
		// 		HorizontalPanel.ALIGN_RIGHT);

		VLayout uploadLayout = new VLayout();
		uploadLayout.addMember(horizontalPanel);
		
		tabUpload.setPane(uploadLayout);
	}
}
