package edu.ucla.loni.pipeline.client;

import java.util.LinkedHashMap;
import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;

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
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.types.Alignment; 
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;

import edu.ucla.loni.pipeline.client.Charts.LONI_Chart;
import edu.ucla.loni.pipeline.client.Requesters.Configuration.RequestConfigurationXMLService;
import edu.ucla.loni.pipeline.client.Requesters.Configuration.RequestConfigurationXMLServiceAsync;
import edu.ucla.loni.pipeline.client.Requesters.Depreciated.XMLDataService;
import edu.ucla.loni.pipeline.client.Requesters.Depreciated.XMLDataServiceAsync;
import edu.ucla.loni.pipeline.client.Requesters.RefreshAllTabs.LONIDataRequester;
import edu.ucla.loni.pipeline.client.Requesters.ResourceUsage.RequestResourceXMLService;
import edu.ucla.loni.pipeline.client.Requesters.ResourceUsage.RequestResourceXMLServiceAsync;
import edu.ucla.loni.pipeline.client.Savers.Configuration.SaveConfigurationXMLService;
import edu.ucla.loni.pipeline.client.Savers.Configuration.SaveConfigurationXMLServiceAsync;
import edu.ucla.loni.pipeline.client.Upload.Features.LONIDragandDropLabel;
import edu.ucla.loni.pipeline.client.Upload.Uploaders.LONIFileUploader;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LONI_Pipeline_ST_Tabset_Display {

	VLayout mainLayout = new VLayout();
	VLayout padding = new VLayout();
	
	//String userID;
	private ListGrid listUsersOnline, 
					 listUserUsage,
					 listUserUsageCount;
	
	//this function displays the buttons in the ListGrids
	final ListGrid listWorkflows = new ListGrid() {  
        @Override  
        protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {  
            String fieldName = this.getFieldName(colNum);  
            if (fieldName.equals("stop")) {  
                IButton button = new IButton();  
                button.setHeight(16);  
                button.setWidth(60);                        
                button.setTitle("Stop");  
                return button;  
            } else if(fieldName.equals("pause")){  
            	IButton button = new IButton();  
                button.setHeight(16);  
                button.setWidth(60);                        
                button.setTitle("Pause");  

                return button; 
            } else if(fieldName.equals("view")){  
            	IButton button = new IButton();  
                button.setHeight(16);  
                button.setWidth(35);                        
                button.setTitle("View");  
                return button; 
            } else{
            	return null;
            }  
        }  
    }; //end of function
    
	// Client-side Services
	String reqResourceXML = "RequestResourceXMLServlet";
	String reqConfigurationXML = "RequestConfigurationXMLServlet";
	String saveConfigurationXML = "SaveConfigurationXMLServlet";
    
	final RequestResourceXMLServiceAsync reqResourceXMLService = GWT.create(RequestResourceXMLService.class);
	final RequestConfigurationXMLServiceAsync reqConfigurationXMLService = GWT.create(RequestConfigurationXMLService.class);
	final SaveConfigurationXMLServiceAsync saveConfigurationXMLService = GWT.create(SaveConfigurationXMLService.class);

	final LONI_Chart memChart = new LONI_Chart("Memory");
	final LONI_Chart thrdChart = new LONI_Chart("Thread");
	
	public LONI_Pipeline_ST_Tabset_Display() {
		mainLayout.setMembersMargin(20);
		mainLayout.setSize("100%", "100%");
		
		((ServiceDefTarget) reqResourceXMLService).setServiceEntryPoint(reqResourceXML);
		((ServiceDefTarget) reqConfigurationXMLService).setServiceEntryPoint(reqConfigurationXML);
		((ServiceDefTarget) saveConfigurationXMLService).setServiceEntryPoint(saveConfigurationXML);
	}
   
	private void formatForm(DynamicForm form) {
		form.setTitleWidth(200);
		for(FormItem i : form.getFields())
			i.setTitleAlign(Alignment.LEFT);
	}
	
	public void buildMainPage(String userID, boolean status) {
		//	if (!status)
		//	return;
		
		// Header with Heading and logout button
		HLayout headLayout = new HLayout();
		headLayout.setMembersMargin(10);
		headLayout.setSize("100%", "3%");
		
		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("1%", "100%");
		headLayout.addMember(padding);
		
		com.smartgwt.client.widgets.Label LONILabel = new com.smartgwt.client.widgets.Label("<b><font size='6'>LONI Pipeline</font></b>");
		LONILabel.setSize("48%", "100%");
		LONILabel.setAlign(Alignment.LEFT);
		LONILabel.setValign(VerticalAlignment.CENTER);
		headLayout.addMember(LONILabel);
		
		Button refreshallBtn = new Button("Refresh All Tabs");
		refreshallBtn.setAlign(Alignment.CENTER);
		refreshallBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO: Refresh all tabs logic
			}
		});
		headLayout.addMember(refreshallBtn);
		
		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("1%", "100%");
		headLayout.addMember(padding);
		
		Button logoutBtn = new Button("LogOut");
		logoutBtn.setAlign(Alignment.CENTER);
		logoutBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO: Logout logic
			}
		});
		headLayout.addMember(logoutBtn);

		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("1%", "100%");
		headLayout.addMember(padding);
		
		headLayout.draw();
		mainLayout.addMember(headLayout);
		
		// Body with tabs
		HLayout tabLayout = new HLayout();
		tabLayout.setMembersMargin(5);
		tabLayout.setSize("100%", "97%");
		TabSet tabset = buildtabset();
		tabset.draw();
		tabLayout.addMember(tabset);
		tabLayout.draw();
		mainLayout.addMember(tabset);
		
		mainLayout.draw();
	}
	
	private TabSet buildtabset() {
		TabSet tabset = new TabSet();
		tabset.setSize("100%", "100%");
		tabset.setPaneMargin(30);
		
	    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void onUncaughtException(Throwable e) {
				e.printStackTrace();
			}
	    });
	    
		//Workflows tab
		tabset.addTab(setWorkFlowsTab());

		//Users Online tab
		tabset.addTab(setUsersOnlineTab());

		//Users Usage tab
		tabset.addTab(setUsersUsageTab());
		  
	    //Memory Usage tab
	    tabset.addTab(setMemoryUsageTab());
	    
	    //Thread Usage tab	    
	    tabset.addTab(setThreadUsageTab());
	   
		//Preferences tab
	    tabset.addTab(setPreferencesTab());

		//Uploads tab
	    tabset.addTab(setUploadsTab());

		return tabset;
	}
	
	private Tab setWorkFlowsTab() {
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
		
		//Need to declare these fields here so we can edit their behavior
		final ListGridField stopfield = new ListGridField("stop", "Stop/Reset");
		stopfield.setAlign(Alignment.CENTER); 
		
		final ListGridField pausefield = new ListGridField("pause","Pause/Rsm");
		pausefield.setAlign(Alignment.CENTER); 
		
		final ListGridField viewfield = new ListGridField("view", "View");
		viewfield.setAlign(Alignment.CENTER); 
		
		fillWorkFlowsTab(null, stopfield, pausefield, viewfield);
		
		//reading directly from Xml file
		final DataSource workFlowsSource = WorkFlowsXmlDS.getInstance();
		workFlowsSource.setClientOnly(true);
		
		listWorkflows.setDataSource(workFlowsSource);
		listWorkflows.setAutoFetchData(true); 
		layoutWorkflows.addMember(listWorkflows);		
		
		tabWorkflows.addTabSelectedHandler(new TabSelectedHandler() {
			public void onTabSelected(TabSelectedEvent event) {
				fillWorkFlowsTab(workFlowsSource, stopfield, pausefield, viewfield);
			}
		});
		
		Button workflowsrefreshbutton = new Button("Refresh");
		workflowsrefreshbutton.setAlign(Alignment.CENTER);
		layoutWorkflows.addMember(workflowsrefreshbutton);
		workflowsrefreshbutton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fillWorkFlowsTab(workFlowsSource, stopfield, pausefield, viewfield);
			}
		});

		tabWorkflows.setPane(layoutWorkflows);	
		return tabWorkflows;
	}
	
	private Tab setUsersOnlineTab() {
		Tab tabUsersOnline = new Tab("Users Online");

		VLayout layoutUsersOnline = new VLayout();
		layoutUsersOnline.setSize("100%", "100%");
		layoutUsersOnline.setMembersMargin(10);

		listUsersOnline = new ListGrid();
		listUsersOnline.setSize("100%", "100%");
		listUsersOnline.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersOnline.setCanPickFields(false);
		listUsersOnline.setCanFreezeFields(false);
		listUsersOnline.setAutoFitFieldWidths(true);
		
		fillUsersOnlineTab(null);
		
		//reading directly from Xml file
		final DataSource usersOnlineSource = UsersOnlineXmlDS.getInstance();
		usersOnlineSource.setClientOnly(true);
				
		listUsersOnline.setDataSource(usersOnlineSource);
		listUsersOnline.setAutoFetchData(true);
		layoutUsersOnline.addMember(listUsersOnline);
		
		tabUsersOnline.addTabSelectedHandler(new TabSelectedHandler() {
			public void onTabSelected(TabSelectedEvent event) {
				fillUsersOnlineTab(usersOnlineSource);
			}
		});
		
		//horizontal layout
		HLayout useronlinehLayout = new HLayout();
		useronlinehLayout.setMembersMargin(10);

		Button usersonlinerefreshbutton = new Button("Refresh");
		usersonlinerefreshbutton.setAlign(Alignment.CENTER);
		useronlinehLayout.addMember(usersonlinerefreshbutton);
		usersonlinerefreshbutton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fillUsersOnlineTab(usersOnlineSource);
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
	
	private Tab setUsersUsageTab() {
		Tab tabUserUsage = new Tab("User Usage");

		VLayout layoutUserUsage = new VLayout();
		layoutUserUsage.setSize("100%", "100%");
		layoutUserUsage.setDefaultLayoutAlign(Alignment.CENTER);
		layoutUserUsage.setMembersMargin(10);
		
		listUserUsage = new ListGrid();
		listUserUsage.setSize("100%", "50%");
		listUserUsage.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUserUsage.setCanPickFields(false);
		listUserUsage.setCanFreezeFields(false);
		listUserUsage.setAutoFitFieldWidths(true);
		
		listUserUsageCount = new ListGrid();
		listUserUsageCount.setSize("50%", "50%");
		listUserUsageCount.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUserUsageCount.setCanPickFields(false);
		listUserUsageCount.setCanFreezeFields(false);
		listUserUsageCount.setAutoFitFieldWidths(true);

		fillUserUsageTab(null, null);
		
		//reading directly from Xml file
		final DataSource userUsageSource = UserUsageXmlDS.getInstance();
		userUsageSource.setClientOnly(true);
		listUserUsage.setDataSource(userUsageSource);
		listUserUsage.setAutoFetchData(true);
		layoutUserUsage.addMember(listUserUsage);
		
		//reading directly from Xml file
		final DataSource userUsageCountSource = UserUsageCountXmlDS.getInstance();
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
	
	private Tab setMemoryUsageTab() {
		Tab tabMemoryUsage = new Tab("Memory Usage");

		tabMemoryUsage.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				memChart.getChart().redraw();
			}
		});
		
		tabMemoryUsage.setPane(memChart);
		return tabMemoryUsage;
	}
	
	private Tab setThreadUsageTab() {
		Tab tabThreadUsage = new Tab("Thread Usage");

		tabThreadUsage.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				thrdChart.getChart().redraw();
			}
		});
		
		tabThreadUsage.setPane(thrdChart);
		return tabThreadUsage;
	}
	
	private Tab setPreferencesTab() {
		Tab tabPreferences = new Tab("Preferences");
		
		VLayout prefLayout = new VLayout();
		
		// Header with Heading and logout button
		HLayout headLayout = new HLayout();
		headLayout.setMembersMargin(20);
		headLayout.setSize("100%", "2%");
		
		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("50%", "100%");
		headLayout.addMember(padding);

		Button refreshConfig= new Button("Refresh Config");
		refreshConfig.setAlign(Alignment.CENTER);
		refreshConfig.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO: Refresh config logic
			}
		});
		headLayout.addMember(refreshConfig);
		
		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("1%", "100%");
		headLayout.addMember(padding);
		
		Button saveConfig = new Button("Save Config");
		saveConfig.setAlign(Alignment.CENTER);
		saveConfig.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO: Save Config logic
			}
		});
		headLayout.addMember(saveConfig);

		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("1%", "100%");
		headLayout.addMember(padding);
		
		headLayout.draw();
		prefLayout.addMember(headLayout);
		
		// Body with tabs
		TabSet tabSet = new TabSet();
		tabSet.setSize("100%", "100%");
		tabSet.setPaneMargin(10);
		
		tabSet.addTab(setPreferencesTab_General());
		tabSet.addTab(setPreferencesTab_Grid());
		tabSet.addTab(setPreferencesTab_Access());
		tabSet.addTab(setPreferencesTab_Packages());
		tabSet.addTab(setPreferencesTab_Executables());
		tabSet.addTab(setPreferencesTab_Advanced());
		
		tabSet.draw();
		prefLayout.addMember(tabSet);
		prefLayout.draw();
		
		tabPreferences.setPane(prefLayout);
		return tabPreferences;
	}
	
	private Tab setPreferencesTab_General() {
		Tab tabGeneral = new Tab("General");
		
		VLayout layoutGeneral = new VLayout();

		//basic
		com.smartgwt.client.widgets.Label labelGeneralBasic = new com.smartgwt.client.widgets.Label("<b><font size=2>Basic</font></b>");
		labelGeneralBasic.setSize("70px", "20px");
		layoutGeneral.addMember(labelGeneralBasic);
		
		DynamicForm formGeneralBasic = new DynamicForm();
		
		
		TextItem hostText = new TextItem("host", "Host");
		
		IntegerItem basicPort = new IntegerItem("port", "Port");
		basicPort.setValue("8001");
		
		TextItem tempdirText = new TextItem("tempdir", "Temporary Directory");
		
		NativeCheckboxItem secureCheckbox = new NativeCheckboxItem();
		secureCheckbox.setTitle("Secure");
		
		TextItem scrdirText = new TextItem("scrdir", "Scratch Directory");
		
		TextItem logfileText = new TextItem("logfile", "Log File");
		
		NativeCheckboxItem escalationCheckbox = new NativeCheckboxItem();
		escalationCheckbox.setTitle("Use privilege escalation: Pipeline server will run commands as the user (sudo as user)");
		
		NativeCheckboxItem enableGuestCheckbox = new NativeCheckboxItem();
		enableGuestCheckbox.setTitle("Enable guests");

		formGeneralBasic.setFields(new FormItem[] { hostText,
													basicPort, 
													tempdirText, 
													secureCheckbox, 
													scrdirText, 
													logfileText, 
													escalationCheckbox,
													enableGuestCheckbox});
		formatForm(formGeneralBasic);
		layoutGeneral.addMember(formGeneralBasic);
		//formGeneralBasic.moveTo(100, 20);
		
		//persistence
		com.smartgwt.client.widgets.Label labelGeneralPersistence = new com.smartgwt.client.widgets.Label("<b><font size=2>Persistence</font></b>");
		labelGeneralPersistence.setSize("70px", "20px");
		layoutGeneral.addMember(labelGeneralPersistence);
		
		DynamicForm formGeneralPersistence = new DynamicForm();
		
		TextItem urlText = new TextItem("url", "URL");
		
		TextItem usernameText = new TextItem("username", "Username");
		
		PasswordItem passwordText = new PasswordItem("password", "Password");
		
		SpinnerItem spinnerItem = new SpinnerItem("si_sessionttl", "Session Time-to-live");
		spinnerItem.setValue(30);
		
		StaticTextItem staticTextItem = new StaticTextItem("sessiondays", "");
		staticTextItem.setValue("(days from the end of this session)");
			
		TextItem historydocText =  new TextItem("historydoc", "History Directory");
		
		TextItem crawlerpurlText = new TextItem("crawlerpurl", "Crawler Persistence URL");
		
		formGeneralPersistence.setFields(new FormItem[] { urlText, 
														  usernameText, 
														  passwordText, 
														  spinnerItem, 
														  staticTextItem, 
														  historydocText, 
														  crawlerpurlText});
		formatForm(formGeneralPersistence);
		layoutGeneral.addMember(formGeneralPersistence);
		
		
		//Server Library
		com.smartgwt.client.widgets.Label labelGeneralServerLibrary = new com.smartgwt.client.widgets.Label("<b><font size=2>Server Library</font></b>");
		labelGeneralServerLibrary.setSize("200px", "20px");
		layoutGeneral.addMember(labelGeneralServerLibrary);
		
		DynamicForm formGeneralServerLibrary = new DynamicForm();
		
		TextItem locationText = new TextItem("location", "Location");
		
		NativeCheckboxItem librarycheckbox = new NativeCheckboxItem();
		librarycheckbox.setTitle("Monitor library update file checkbox");
		
		final TextItem libraryPathText = new TextItem("librarypath", "Monitor library update file");
		libraryPathText.setDisabled(true);
		
		librarycheckbox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				Boolean cstat = (Boolean) event.getValue();
				libraryPathText.setDisabled(!cstat);
			}
		});
		
		TextItem pipeutilitiespathText =  new TextItem("pipeutilitiespath", "Pipeline Utilities Path");
		
		formGeneralServerLibrary.setFields(new FormItem[] { locationText,
															librarycheckbox,
															libraryPathText,
															pipeutilitiespathText });
		formatForm(formGeneralServerLibrary);
		layoutGeneral.addMember(formGeneralServerLibrary);
		
		tabGeneral.setPane(layoutGeneral);
		return tabGeneral;
	}
	
	private Tab setPreferencesTab_Grid() {
		Tab tabGrid = new Tab("Grid");
		
		VLayout layoutGrid = new VLayout();
		
		DynamicForm dynamicForm = new DynamicForm();
		
		NativeCheckboxItem enableGrid = new NativeCheckboxItem();
		enableGrid.setTitle("Enable Grid");
		
		final TextItem gridEngineNativeSpec = new TextItem("gridEngineNativeSpec", "Grid engine native specification");
		gridEngineNativeSpec.setDisabled(true);
		
		final TextItem jobNamePrefix = new TextItem("jobNamePrefix", "Job name prefix");
		jobNamePrefix.setDisabled(true);
		
		final TextItem jobSubmissionQueue = new TextItem("jobSubmissionQueue", "Job submission queue");
		jobSubmissionQueue.setDisabled(true);
		
		final TextItem complexResourceAttributes = new TextItem("complexResourceAttributes", "Complex resource attributes");
		complexResourceAttributes.setDisabled(true);
		
		final NativeCheckboxItem sgeMem = new NativeCheckboxItem();
		sgeMem.setDisabled(true); 
		sgeMem.setTitle("Report SGE memory usage");
		
		final SelectItem gridVariablesPolicy = new SelectItem();
		gridVariablesPolicy.setDisabled(true);
		gridVariablesPolicy.setTitle("Grid Variables policy");
		
		final TextItem gridVariablesPolicyText = new TextItem("gridVariablesPolicyText", "Policy Text");
		gridVariablesPolicyText.setDisabled(true);
		
		final TextItem prefixBeforeVar = new TextItem("prefixBeforeVar", "Prefix before each variable");
		prefixBeforeVar.setDisabled(true);
		
		final SpinnerItem maxParallelThreads = new SpinnerItem("maxParallelThreads", "Max number of parallel submission threads");
		maxParallelThreads.setValue(50);
		maxParallelThreads.setDisabled(true);
		
		final SpinnerItem maxNumResubmissions = new SpinnerItem("maxNumResubmissions", "Max number of resubmissions for \"error stated\" jobs");
		maxNumResubmissions.setValue(3);
		maxNumResubmissions.setDisabled(true);
	
		final NativeCheckboxItem arrayJobEnable = new NativeCheckboxItem();
		arrayJobEnable.setTitle("Enable");
		
		DynamicForm arrayJobForm = new DynamicForm();
		arrayJobForm.setFields(new FormItem[] { arrayJobEnable });
		
		final CanvasItem arrayJob = new CanvasItem();
		arrayJob.setTitle("Array Job");
		formatForm(arrayJobForm);
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
				Boolean cstat = (Boolean) event.getValue();
				if (!cstat)	{
					gridEngineNativeSpec.clearValue();
					jobNamePrefix.clearValue();
					jobSubmissionQueue.clearValue();
					complexResourceAttributes.clearValue();
					sgeMem.clearValue(); 
					gridVariablesPolicy.clearValue();
					gridVariablesPolicyText.clearValue();
					prefixBeforeVar.clearValue();
					maxParallelThreads.setValue(50);
					maxNumResubmissions.setValue(3);
					arrayJobEnable.clearValue();					
					chunks.setValue(200);
					fileStat.setValue(0);
					chunkSize.setValue(50);
					increaseChunkSize.clearValue();
					gridPlugin.clearValue();
					jarFiles.clearValue();
					jarFilesClass.clearValue();
					restartService.clearValue();
					gridPort.setValue(8111);
					memItem.clearValue();
					jarFile.clearValue();
					retrieveList.clearValue();
					gridEngineAdmin.clearValue();
					gridAcctLabel.clearValue();
					gridAcctURL.clearValue();
					gridUsername.clearValue();
					gridPassword.clearValue();
				}
				
				gridEngineNativeSpec.setDisabled(!cstat);
				jobNamePrefix.setDisabled(!cstat);
				jobSubmissionQueue.setDisabled(!cstat);
				complexResourceAttributes.setDisabled(!cstat);
				sgeMem.setDisabled(!cstat);
				gridVariablesPolicy.setDisabled(!cstat);
				gridVariablesPolicyText.setDisabled(!cstat);
				prefixBeforeVar.setDisabled(!cstat);
				maxParallelThreads.setDisabled(!cstat);
				maxNumResubmissions.setDisabled(!cstat);
				arrayJob.setDisabled(!cstat);
				gridPlugin.setDisabled(!cstat);
				jarFiles.setDisabled(!cstat);
				jarFilesClass.setDisabled(!cstat);
				restartService.setDisabled(!cstat);
				gridPort.setDisabled(!cstat);
				memItem.setDisabled(!cstat);
				jarFile.setDisabled(!cstat);
				retrieveList.setDisabled(!cstat);
				gridEngineAdmin.setDisabled(!cstat);
				gridAcctLabel.setDisabled(!cstat);
				gridAcctURL.setDisabled(!cstat);
				gridUsername.setDisabled(!cstat);
				gridPassword.setDisabled(!cstat);
			}
		});
		
		arrayJobEnable.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				Boolean cstat = (Boolean) event.getValue();
				if (!cstat)	{
					chunks.setValue(200);
					fileStat.setValue(0);
					chunkSize.setValue(50);
					increaseChunkSize.clearValue();
				}
				
				chunks.setDisabled(!cstat);
				fileStat.setDisabled(!cstat);
				chunkSize.setDisabled(!cstat);
				increaseChunkSize.setDisabled(!cstat);
			}
		});
		
		dynamicForm.setFields(new FormItem[] { enableGrid, 
							  				   gridEngineNativeSpec, 
							  				   jobNamePrefix, 
							  				   jobSubmissionQueue,
							  				   complexResourceAttributes, 
							  				   sgeMem, 
							  				   gridVariablesPolicy, 
							  				   gridVariablesPolicyText,
							  				   prefixBeforeVar, 
							  				   maxParallelThreads, 
							  				   maxNumResubmissions, 
							  				   arrayJob, 
							  				   chunks, 
							  				   fileStat, 
							  				   chunkSize, 
							  				   increaseChunkSize, 
							  				   gridPlugin, 
							  				   jarFiles, 
							  				   jarFilesClass,
							  				   restartService, 
							  				   gridPort, 
							  				   memItem, 
							  				   jarFile, 
							  				   retrieveList, 
							  				   gridEngineAdmin, 
							  				   gridAcctLabel,
							  				   gridAcctURL, 
							  				   gridUsername, 
							  				   gridPassword });
		formatForm(dynamicForm);
		layoutGrid.addMember(dynamicForm);
		
		tabGrid.setPane(layoutGrid);
		return tabGrid;
	}
	
	private Tab setPreferencesTab_Access() {
		Tab tabAccess = new Tab("Access");
		VLayout layoutAccess = new VLayout();
		
		DynamicForm dynamicForm = new DynamicForm();
		
		final SpinnerItem spinnerItem_1 = new SpinnerItem("si_usrpcfreeslots", "One user cannot take more than");
		spinnerItem_1.setValue(50);
		spinnerItem_1.setHint("% of free slots at submission time");
		spinnerItem_1.setShowHint(true);
		spinnerItem_1.setMin(0);
		spinnerItem_1.setMax(100);
		spinnerItem_1.setDisabled(true);

		DynamicForm dynamicForm_1 = new DynamicForm();
		CheckboxItem checkboxItem_1 = new CheckboxItem("cb_enableubjm", "Enable (NOTE: Enabling user management may require some time to adjust user usage accuracy)");
		checkboxItem_1.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				Boolean cstat = (Boolean) event.getValue();
				if (!cstat)	
					spinnerItem_1.setValue(50);
				spinnerItem_1.setDisabled(!cstat);
			}
		});
		checkboxItem_1.setValue(false);
		dynamicForm_1.setFields(new FormItem[] { checkboxItem_1 });
		
		CanvasItem canvasItem_1 = new CanvasItem("ci_usrbjbmngmt", "User-Based Job Management");
		canvasItem_1.setCanvas(dynamicForm_1);
		
		final SpinnerItem spinnerItem_2 = new SpinnerItem("si_maxwrkflpu", "Maximum active (running & paused) workflows per user");
		spinnerItem_2.setValue(20);
		spinnerItem_2.setMin(0);
		spinnerItem_2.setMax(9999);
		spinnerItem_2.setDisabled(true);
		
		DynamicForm dynamicForm_2 = new DynamicForm();
		CheckboxItem checkboxItem_2 = new CheckboxItem("cb_enablewfm", "Enable");
		checkboxItem_2.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				Boolean cstat = (Boolean) event.getValue();
				if (!cstat)	
					spinnerItem_2.setValue(20);
			  spinnerItem_2.setDisabled(!cstat);
			}
		});
		dynamicForm_2.setFields(new FormItem[] { checkboxItem_2 });
		
		CanvasItem canvasItem_2 = new CanvasItem("ci_wrkflmngmt", "Workflow Management");
		canvasItem_2.setCanvas(dynamicForm_2);
		
		dynamicForm.setFields(new FormItem[] { new TextItem("ti_srvadm", "Server Admins"), 
				   			  				   new TextItem("ti_cntrldusrs", "Controlled users"), 
				   			  				   new TextItem("ti_cntrlddirs", "Controlled directories"), 
				   			  				   canvasItem_1, 
				   			  				   spinnerItem_1, 
				   			  				   canvasItem_2, 
				   			  				   spinnerItem_2});
		formatForm(dynamicForm);
		layoutAccess.addMember(dynamicForm);

		tabAccess.setPane(layoutAccess);
		return tabAccess;
	}
	
	private Tab setPreferencesTab_Packages() {
		Tab tabPackages = new Tab("Packages");
		
		VLayout packageLayout = new VLayout();
		packageLayout.setHeight("220px");
		
		Label packetLabel = new Label("Packages: Please use the Server Terminal tool to connect to server and edit.\r\nFor more information, click the help button on lower left corner.");
		packageLayout.addMember(packetLabel);
		
		tabPackages.setPane(packageLayout);
		return tabPackages;
	}
	
	private Tab setPreferencesTab_Executables() {
		Tab tabExecutables = new Tab("Executables");	
		
		VLayout executeablesLayout = new VLayout();
		
		Label executablesLabel = new Label("Executables: Please use the Server Terminal tool to connect to server and edit.\r\nFor more information, click the help button on lower left corner.");
		executeablesLayout.addMember(executablesLabel);
		
		tabExecutables.setPane(executeablesLayout);		
		return tabExecutables;
	}
	
	private Tab setPreferencesTab_Advanced() {
		Tab tabAdvanced = new Tab("Advanced");
		return tabAdvanced;
	}
	
	private Tab setUploadsTab() {
		Tab tabUpload = new Tab("Upload");	
		
		final VerticalPanel fileuploadPanel = new VerticalPanel();
		final Map<String, Image> cancelButtons = new LinkedHashMap<String, Image>();
		
		// Refresh Button for all Tabs
		final LONIDataRequester dataRequester = new LONIDataRequester(reqResourceXMLService, reqConfigurationXMLService, memChart, thrdChart);
		
		Button appRefreshButton = new Button("Refresh All Tabs");
		appRefreshButton.setAlign(Alignment.CENTER);
		appRefreshButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dataRequester.refreshTabs();
			}
		});
		
		final LONIFileUploader LONIfileUploader = new LONIFileUploader(cancelButtons, fileuploadPanel, dataRequester);
		
		if (Uploader.isAjaxUploadWithProgressEventsSupported()) {
			final LONIDragandDropLabel fileuploadLabel = new LONIDragandDropLabel("Drop Files", LONIfileUploader, cancelButtons, fileuploadPanel);
			fileuploadPanel.add(fileuploadLabel);
		}
		fileuploadPanel.add(LONIfileUploader);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.add(fileuploadPanel);

		VLayout uploadLayout = new VLayout();
		uploadLayout.addMember(horizontalPanel);
		
		uploadLayout.addMember(appRefreshButton);
		
		
		DynamicForm fileUploadForm = new DynamicForm();
		fileUploadForm.setFields(new FormItem[] { new TextItem("weburl", "XML file Web URL")});
		fileuploadPanel.add(fileUploadForm);
		
		Button webUrl = new Button("Retrieve from Web");
		webUrl.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		Window.alert("Uploads");
	        }
	    });
		fileuploadPanel.add(webUrl);
		
		
		tabUpload.setPane(uploadLayout);
		return tabUpload;
	}
	
	private void fillWorkFlowsTab(DataSource workFlowsSource, ListGridField stopfield, ListGridField pausefield, ListGridField viewfield) {
		if (workFlowsSource != null) {
			workFlowsSource.invalidateCache();
			workFlowsSource = WorkFlowsXmlDS.getInstance();
			listWorkflows.setDataSource(workFlowsSource);
			listWorkflows.fetchData();
		}
		
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
	}
	
	private void fillUsersOnlineTab(DataSource usersOnlineSource) {
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
	
	private void fillUserUsageTab(DataSource userUsageSource, DataSource userUsageCountSource) {
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
								new ListGridField("nodeName", "NodeName"), 
								new ListGridField("instance", "Instance"));
		
		listUserUsageCount.setFields(new ListGridField("username", "Username"), 
				 					 new ListGridField("count", "Count"));
	}
}
