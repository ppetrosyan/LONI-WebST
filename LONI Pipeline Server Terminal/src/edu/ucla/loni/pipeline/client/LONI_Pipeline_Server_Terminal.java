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
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.UploadItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.types.Alignment; 

import edu.ucla.loni.pipeline.client.Charts.LONI_Chart;
import edu.ucla.loni.pipeline.client.UploadFeatures.LONIDragandDropLabel;
import edu.ucla.loni.pipeline.client.Uploaders.ConfigurationUploader;
import edu.ucla.loni.pipeline.client.Uploaders.LONIFileUploader;
import edu.ucla.loni.pipeline.client.Uploaders.SimulatedDataUploader;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.CanvasItem;

import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.Label;

import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;




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
		listWorkflows.setCanPickFields(false);
		listWorkflows.setCanFreezeFields(false);
		listWorkflows.setAutoFitFieldWidths(true);
		
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
		DataSource workFlowsSource = WorkFlowsXmlDS.getInstance();
		listWorkflows.setDataSource(workFlowsSource);
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
		listUsersOnline.setCanPickFields(false);
		listUsersOnline.setCanFreezeFields(false);
		listUsersOnline.setAutoFitFieldWidths(true);
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
		listUsersUsage.setCanPickFields(false);
		listUsersUsage.setCanFreezeFields(false);
		listUsersUsage.setAutoFitFieldWidths(true);
		listUsersUsage.setFields(
				new ListGridField("username", "Username"),
				new ListGridField("workflowID", "Workflow ID"),
				new ListGridField("nodeName", "NodeName"), 
				new ListGridField("instance", "Instance"));
		layoutUsersUsage.addMember(listUsersUsage);

		listUsersUsageCount = new ListGrid();
		listUsersUsageCount.setSize("50%", "50%");
		listUsersUsageCount.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersUsageCount.setCanPickFields(false);
		listUsersUsageCount.setCanFreezeFields(false);
		listUsersUsageCount.setAutoFitFieldWidths(true);
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

		tabSet.setSize("439px", "272px");

		tabSet.setPaneMargin(20);

		
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
		DynamicForm gridForm = new DynamicForm();
		NativeCheckboxItem enableGrid = new NativeCheckboxItem();
		enableGrid.setTitle("Enable Grid");
		final TextItem gridEngineNativeSpec = new TextItem("gridEngineNativeSpec", "Grid engine native specification");
		gridEngineNativeSpec.setDisabled(true);
		final TextItem jobNamePrefix = new TextItem("jobNamePrefix", "Job name prefix");
		jobNamePrefix.setDisabled(true);
		TextItem jobSubmissionQueue = new TextItem("jobSubmissionQueue", "Job submission queue");
		final TextItem complexResourceAttributes = new TextItem("complexResourceAttributes", "Complex resource attributes");
		complexResourceAttributes.setDisabled(true);
		NativeCheckboxItem sgeMem = new NativeCheckboxItem();
		sgeMem.setTitle("Report SGE memory usage");
		final SelectItem gridVariablesPolicy = new SelectItem();
		gridVariablesPolicy.setDisabled(true);
		gridVariablesPolicy.setTitle("Grid Variables policy");
		final TextItem gridVariablesPolicyText = new TextItem("gridVariablesPolicyText", "");
		gridVariablesPolicyText.setDisabled(true);
		final TextItem prefixBeforeVar = new TextItem("prefixBeforeVar", "Prefix before each variable");
		prefixBeforeVar.setDisabled(true);
		final SpinnerItem maxParallelThreads = new SpinnerItem("maxParallelThreads", "Max number of parallel submission threads");
		maxParallelThreads.setValue(50);
		maxParallelThreads.setDisabled(true);
		final SpinnerItem maxNumResubmissions = new SpinnerItem("maxNumResubmissions", "Max number of resubmissions for \"error stated\" jobs");
		maxNumResubmissions.setValue(3);
		maxNumResubmissions.setDisabled(true);
		final CanvasItem arrayJob = new CanvasItem();
		arrayJob.setTitle("Array Job");
		DynamicForm arrayJobForm = new DynamicForm();
		final NativeCheckboxItem arrayJobEnable = new NativeCheckboxItem();
		arrayJobEnable.setTitle("Enable");
		arrayJobForm.setFields(new FormItem[] { arrayJobEnable });
		arrayJob.setCanvas(arrayJobForm);
		arrayJob.setDisabled(true);
		final IntegerItem chunks = new IntegerItem("chunks", "Break into chunks when number of jobs exceeds");
		chunks.setValue(200);
		chunks.setHint("(default: 200)");
		chunks.setDisabled(true);
		final SpinnerItem fileStat = new SpinnerItem("fileStat", "Use File Stat with Timeout");
		fileStat.setValue(0);
		// TODO: implement total number of slots... thing
		fileStat.setHint("0 - [insert total number of slots here]");
		fileStat.setDisabled(true);
		final IntegerItem chunkSize = new IntegerItem("chunkSize", "Chunk size");
		chunkSize.setValue(50);
		chunkSize.setHint("(default: 50)");
		chunkSize.setDisabled(true);
		final NativeCheckboxItem increaseChunkSize = new NativeCheckboxItem();
		increaseChunkSize.setTitle("Gradually increase chunk size.");
		increaseChunkSize.setDisabled(true);
		final SelectItem gridPlugin = new SelectItem();
		gridPlugin.setTitle("Grid Plugin");
		gridPlugin.setDisabled(true);
		final TextItem jarFiles = new TextItem("jarFiles", "Jar file(s)");
		jarFiles.setDisabled(true);
		final TextItem jarFilesClass = new TextItem("jarFilesClass", "Class");
		jarFilesClass.setDisabled(true);
		final NativeCheckboxItem restartService = new NativeCheckboxItem();
		restartService.setTitle("Use Restartable Service");
		restartService.setDisabled(true);
		final IntegerItem gridPort = new IntegerItem("gridPort", "Port");
		gridPort.setValue(8111);
		gridPort.setHint("(default: 8111)");
		gridPort.setDisabled(true);
		final IntegerItem memItem = new IntegerItem("memItem", "Memory (MB)");
		memItem.setDisabled(true);
		final TextItem jarFile = new TextItem("jarFile", "JAR File");
		jarFile.setHint("(default: <SERVER_PATH>/dist/gridplugins/PipelineGridService.jar)");
		jarFile.setDisabled(true);
		final NativeCheckboxItem retrieveList = new NativeCheckboxItem();
		retrieveList.setTitle("Retrieve lists for finished job checkings");
		retrieveList.setDisabled(true);
		final NativeCheckboxItem gridEngineAdmin = new NativeCheckboxItem();
		gridEngineAdmin.setTitle("Pipeline user is a grid engine admin");
		gridEngineAdmin.setDisabled(true);
		final StaticTextItem gridAcctLabel = new StaticTextItem("gridAcctLabel", "Grid Accounting");
		gridAcctLabel.setDisabled(true);
		final TextItem gridAcctURL = new TextItem("gridAcctURL", "URL");
		gridAcctURL.setDisabled(true);
		final TextItem gridUsername = new TextItem("gridUsername", "Username");
		gridUsername.setDisabled(true);
		final PasswordItem gridPassword = new PasswordItem("gridPassword", "Password");
		gridPassword.setDisabled(true);
		
		// TODO: add the rest of the fields
				
		enableGrid.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				gridEngineNativeSpec.setDisabled(!((Boolean) event.getValue()));
				jobNamePrefix.setDisabled(!((Boolean) event.getValue()));
				complexResourceAttributes.setDisabled(!((Boolean) event.getValue()));
				gridVariablesPolicy.setDisabled(!((Boolean) event.getValue()));
				gridVariablesPolicyText.setDisabled(!((Boolean) event.getValue()));
				prefixBeforeVar.setDisabled(!((Boolean) event.getValue()));
				maxParallelThreads.setDisabled(!((Boolean) event.getValue()));
				maxNumResubmissions.setDisabled(!((Boolean) event.getValue()));
				arrayJob.setDisabled(!((Boolean) event.getValue()));
				gridPlugin.setDisabled(!((Boolean) event.getValue()));
				jarFiles.setDisabled(!((Boolean) event.getValue()));
				jarFilesClass.setDisabled(!((Boolean) event.getValue()));
				restartService.setDisabled(!((Boolean) event.getValue()));
				gridPort.setDisabled(!((Boolean) event.getValue()));
				memItem.setDisabled(!((Boolean) event.getValue()));
				jarFile.setDisabled(!((Boolean) event.getValue()));
				retrieveList.setDisabled(!((Boolean) event.getValue()));
				gridEngineAdmin.setDisabled(!((Boolean) event.getValue()));
				gridAcctLabel.setDisabled(!((Boolean) event.getValue()));
				gridAcctURL.setDisabled(!((Boolean) event.getValue()));
				gridUsername.setDisabled(!((Boolean) event.getValue()));
				gridPassword.setDisabled(!((Boolean) event.getValue()));
			}
		});
		
		arrayJobEnable.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				chunks.setDisabled(!((Boolean) event.getValue()));
				fileStat.setDisabled(!((Boolean) event.getValue()));
				chunkSize.setDisabled(!((Boolean) event.getValue()));
				increaseChunkSize.setDisabled(!((Boolean) event.getValue()));
			}
		});
		
		gridForm.setFields(new FormItem[] { enableGrid, gridEngineNativeSpec, jobNamePrefix, jobSubmissionQueue,
											complexResourceAttributes, sgeMem, gridVariablesPolicy, gridVariablesPolicyText,
											prefixBeforeVar, maxParallelThreads, maxNumResubmissions, arrayJob, 
											chunks, fileStat, chunkSize, increaseChunkSize, gridPlugin, jarFiles, jarFilesClass,
											restartService, gridPort, memItem, jarFile, retrieveList, gridEngineAdmin, gridAcctLabel,
											gridAcctURL, gridUsername, gridPassword });
		formatForm(gridForm);
		VLayout gridLayout = new VLayout();
		gridLayout.addMember(gridForm);
		tabGrid.setPane(gridLayout);
		tabSet.addTab(tabGrid);
		
		Tab tabAccess = new Tab("Access");
		
		VLayout layout = new VLayout();
		
		DynamicForm dynamicForm = new DynamicForm();
		dynamicForm.setHeight("191px");
		
		final SpinnerItem spinnerItem_1 = new SpinnerItem("spinnerItem_1", "One user cannot take more than");
		spinnerItem_1.setValue(50);
		spinnerItem_1.setHint("% of free slots at submission time");
		spinnerItem_1.setShowHint(true);
		spinnerItem_1.setMin(0);
		spinnerItem_1.setMax(100);
		spinnerItem_1.setDisabled(true);
		final SpinnerItem spinnerItem_2 = new SpinnerItem("spinnerItem_2", "Maximum active (running & paused) workflows per user");
		spinnerItem_2.setMin(0);
		spinnerItem_2.setMax(9999);
		spinnerItem_2.setValue(20);
		spinnerItem_2.setDisabled(true);
		CanvasItem canvasItem = new CanvasItem("newCanvasItem_8", "Workflow Management");
		
		DynamicForm dynamicForm_1 = new DynamicForm();
		CheckboxItem checkboxItem_1 = new CheckboxItem("newCheckboxItem_1", "Enable                                                                                      ");
		checkboxItem_1.setTextAlign(Alignment.LEFT);
		checkboxItem_1.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
			  spinnerItem_2.setDisabled(!((Boolean) event.getValue()));
			}
		});
		dynamicForm_1.setFields(new FormItem[] { checkboxItem_1});
		canvasItem.setCanvas(dynamicForm_1);
		CanvasItem canvasItem_1 = new CanvasItem("newCanvasItem_8", "User-Based Job Management");
		
		DynamicForm dynamicForm_2 = new DynamicForm();
		CheckboxItem checkboxItem = new CheckboxItem("CheckboxItem1", "Enable (NOTE: Enabling user management may require some time to adjust user usage accuracy)");
		checkboxItem.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				spinnerItem_1.setDisabled(!((Boolean) event.getValue()));
			}
		});
		checkboxItem.setValue(false);
		dynamicForm_2.setFields(new FormItem[] { checkboxItem});
		canvasItem_1.setCanvas(dynamicForm_2);
		dynamicForm.setFields(new FormItem[] { new TextItem("newTextItem_1", "Server Admins"), new TextItem("newTextItem_2", "Controlled users"), new TextItem("newTextItem_3", "Controlled directories"), canvasItem_1, spinnerItem_1, canvasItem, spinnerItem_2});
		layout.addMember(dynamicForm);
		tabAccess.setPane(layout);
		tabSet.addTab(tabAccess);
		
		Tab tabPackages = new Tab("Packages");
		
		VLayout packageLayout = new VLayout();
		packageLayout.setHeight("218px");
		
		Label packetLabel = new Label("Packages: Please use the Server Terminal tool to connect to server and edit.\r\nFor more information, click the help button on lower left corner.");
		packageLayout.addMember(packetLabel);
		tabPackages.setPane(packageLayout);
		tabSet.addTab(tabPackages);
		
		Tab tabExecutables = new Tab("Executables");
		
		VLayout executeablesLayout = new VLayout();
		
		Label executablesLabel = new Label("Executables: Please use the Server Terminal tool to connect to server and edit.\r\nFor more information, click the help button on lower left corner.");
		executeablesLayout.addMember(executablesLabel);
		tabExecutables.setPane(executeablesLayout);
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
		final VerticalPanel fileuploadPanel = new VerticalPanel();
		final Map<String, Image> cancelButtons = new LinkedHashMap<String, Image>();
		final ConfigurationUploader configurationUploader = new ConfigurationUploader(cancelButtons, configurationPanel);
		final SimulatedDataUploader simulatedDataUploader = new SimulatedDataUploader(cancelButtons, simulatedDataPanel);
		final LONIFileUploader LONIfileUploader = new LONIFileUploader(cancelButtons, fileuploadPanel);
		
		
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

		if (Uploader.isAjaxUploadWithProgressEventsSupported()) {
			final LONIDragandDropLabel fileuploadLabel = new LONIDragandDropLabel("Drop Files", LONIfileUploader, cancelButtons, configurationPanel);
			fileuploadPanel.add(fileuploadLabel);
		}
		fileuploadPanel.add(LONIfileUploader);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.add(configurationPanel);
		horizontalPanel.add(simulatedDataPanel);
		horizontalPanel.add(fileuploadPanel);

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
