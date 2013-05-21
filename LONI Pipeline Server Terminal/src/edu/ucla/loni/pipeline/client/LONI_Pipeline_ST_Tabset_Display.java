package edu.ucla.loni.pipeline.client;

import java.util.LinkedHashMap;
import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;

import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.NativeCheckboxItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.UploadItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.types.RowEndEditAction;
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
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
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

	// String userID;
	private ListGrid listUsersOnline, listUserUsage, listUserUsageCount;

	// this function displays the buttons in the ListGrids
	final ListGrid listWorkflows = new ListGrid() {
		@Override
		protected Canvas createRecordComponent(final ListGridRecord record,
				Integer colNum) {
			String fieldName = this.getFieldName(colNum);
			if (fieldName.equals("stop")) {
				IButton button = new IButton();
				button.setHeight(16);
				button.setWidth(60);
				button.setTitle("Stop");
				return button;
			} else if (fieldName.equals("pause")) {
				IButton button = new IButton();
				button.setHeight(16);
				button.setWidth(60);
				button.setTitle("Pause");

				return button;
			} else if (fieldName.equals("view")) {
				IButton button = new IButton();
				button.setHeight(16);
				button.setWidth(35);
				button.setTitle("View");
				return button;
			} else {
				return null;
			}
		}
	}; // end of function

	// Client-side Services
	String reqResourceXML = "RequestResourceXMLServlet";
	String reqConfigurationXML = "RequestConfigurationXMLServlet";
	String saveConfigurationXML = "SaveConfigurationXMLServlet";

	final RequestResourceXMLServiceAsync reqResourceXMLService = GWT
			.create(RequestResourceXMLService.class);
	final RequestConfigurationXMLServiceAsync reqConfigurationXMLService = GWT
			.create(RequestConfigurationXMLService.class);
	final SaveConfigurationXMLServiceAsync saveConfigurationXMLService = GWT
			.create(SaveConfigurationXMLService.class);

	final LONI_Chart memChart = new LONI_Chart("Memory");
	final LONI_Chart thrdChart = new LONI_Chart("Thread");

	public LONI_Pipeline_ST_Tabset_Display() {
		mainLayout.setMembersMargin(20);
		mainLayout.setSize("100%", "100%");

		((ServiceDefTarget) reqResourceXMLService)
				.setServiceEntryPoint(reqResourceXML);
		((ServiceDefTarget) reqConfigurationXMLService)
				.setServiceEntryPoint(reqConfigurationXML);
		((ServiceDefTarget) saveConfigurationXMLService)
				.setServiceEntryPoint(saveConfigurationXML);
	}

	private void formatForm(DynamicForm form) {
		form.setTitleWidth(200);
		for (FormItem i : form.getFields())
			i.setTitleAlign(Alignment.LEFT);
	}

	public void buildMainPage(String userID, boolean status) {
		// if (!status)
		// return;

		// Header with Heading and logout button
		HLayout headLayout = new HLayout();
		headLayout.setMembersMargin(10);
		headLayout.setSize("100%", "3%");

		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("1%", "100%");
		headLayout.addMember(padding);

		com.smartgwt.client.widgets.Label LONILabel = new com.smartgwt.client.widgets.Label(
				"<b><font size='6'>LONI Pipeline</font></b>");
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

		// Workflows tab
		tabset.addTab(setWorkFlowsTab());

		// Users Online tab
		tabset.addTab(setUsersOnlineTab());

		// Users Usage tab
		tabset.addTab(setUsersUsageTab());

		// Memory Usage tab
		tabset.addTab(setMemoryUsageTab());

		// Thread Usage tab
		tabset.addTab(setThreadUsageTab());

		// Preferences tab
		tabset.addTab(setPreferencesTab());

		// Uploads tab
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

		// Need to declare these fields here so we can edit their behavior
		final ListGridField stopfield = new ListGridField("stop", "Stop/Reset");
		stopfield.setAlign(Alignment.CENTER);

		final ListGridField pausefield = new ListGridField("pause", "Pause/Rsm");
		pausefield.setAlign(Alignment.CENTER);

		final ListGridField viewfield = new ListGridField("view", "View");
		viewfield.setAlign(Alignment.CENTER);

		fillWorkFlowsTab(null, stopfield, pausefield, viewfield);

		// reading directly from Xml file
		final DataSource workFlowsSource = WorkFlowsXmlDS.getInstance();
		workFlowsSource.setClientOnly(true);

		listWorkflows.setDataSource(workFlowsSource);
		listWorkflows.setAutoFetchData(true);
		layoutWorkflows.addMember(listWorkflows);

		tabWorkflows.addTabSelectedHandler(new TabSelectedHandler() {
			public void onTabSelected(TabSelectedEvent event) {
				fillWorkFlowsTab(workFlowsSource, stopfield, pausefield,
						viewfield);
			}
		});

		Button workflowsrefreshbutton = new Button("Refresh");
		workflowsrefreshbutton.setAlign(Alignment.CENTER);
		layoutWorkflows.addMember(workflowsrefreshbutton);
		workflowsrefreshbutton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fillWorkFlowsTab(workFlowsSource, stopfield, pausefield,
						viewfield);
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

		// reading directly from Xml file
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

		// horizontal layout
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

		Button refreshConfig = new Button("Refresh Config");
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

		// basic
		com.smartgwt.client.widgets.Label labelGeneralBasic = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Basic</font></b>");
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
		escalationCheckbox
				.setTitle("Use privilege escalation: Pipeline server will run commands as the user (sudo as user)");

		NativeCheckboxItem enableGuestCheckbox = new NativeCheckboxItem();
		enableGuestCheckbox.setTitle("Enable guests");

		formGeneralBasic.setFields(new FormItem[] { hostText, basicPort,
				tempdirText, secureCheckbox, scrdirText, logfileText,
				escalationCheckbox, enableGuestCheckbox });
		formatForm(formGeneralBasic);
		layoutGeneral.addMember(formGeneralBasic);

		// add line
		com.smartgwt.client.widgets.Label line = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutGeneral.addMember(line);
		// formGeneralBasic.moveTo(100, 20);

		// persistence
		com.smartgwt.client.widgets.Label labelGeneralPersistence = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Persistence</font></b>");
		labelGeneralPersistence.setSize("70px", "20px");
		layoutGeneral.addMember(labelGeneralPersistence);

		DynamicForm formGeneralPersistence = new DynamicForm();

		TextItem urlText = new TextItem("url", "URL");

		TextItem usernameText = new TextItem("username", "Username");

		PasswordItem passwordText = new PasswordItem("password", "Password");

		SpinnerItem spinnerItem = new SpinnerItem("si_sessionttl",
				"Session Time-to-live");
		spinnerItem.setValue(30);

		StaticTextItem staticTextItem = new StaticTextItem("sessiondays", "");
		staticTextItem.setValue("(days from the end of this session)");

		TextItem historydocText = new TextItem("historydoc",
				"History Directory");

		TextItem crawlerpurlText = new TextItem("crawlerpurl",
				"Crawler Persistence URL");

		formGeneralPersistence.setFields(new FormItem[] { urlText,
				usernameText, passwordText, spinnerItem, staticTextItem,
				historydocText, crawlerpurlText });
		formatForm(formGeneralPersistence);
		layoutGeneral.addMember(formGeneralPersistence);

		// add line
		com.smartgwt.client.widgets.Label line2 = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutGeneral.addMember(line2);

		// Server Library
		com.smartgwt.client.widgets.Label labelGeneralServerLibrary = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Server Library</font></b>");
		labelGeneralServerLibrary.setSize("200px", "20px");
		layoutGeneral.addMember(labelGeneralServerLibrary);

		DynamicForm formGeneralServerLibrary = new DynamicForm();

		TextItem locationText = new TextItem("location", "Location");

		NativeCheckboxItem librarycheckbox = new NativeCheckboxItem();
		librarycheckbox.setTitle("Monitor library update file checkbox");

		final TextItem libraryPathText = new TextItem("librarypath",
				"Monitor library update file");
		libraryPathText.setDisabled(true);

		librarycheckbox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				Boolean cstat = (Boolean) event.getValue();
				libraryPathText.setDisabled(!cstat);
			}
		});

		TextItem pipeutilitiespathText = new TextItem("pipeutilitiespath",
				"Pipeline Utilities Path");

		formGeneralServerLibrary.setFields(new FormItem[] { locationText,
				librarycheckbox, libraryPathText, pipeutilitiespathText });
		formatForm(formGeneralServerLibrary);
		layoutGeneral.addMember(formGeneralServerLibrary);

		tabGeneral.setPane(layoutGeneral);
		return tabGeneral;
	}

	private Tab setPreferencesTab_Grid() {
		Tab tabGrid = new Tab("Grid");

		VLayout layoutGrid = new VLayout();

		DynamicForm dynamicFormEnableGrid = new DynamicForm();
		CheckboxItem enableGrid = new CheckboxItem();
		enableGrid.setTitle("Enable Grid");
		enableGrid.setLabelAsTitle(true);
		dynamicFormEnableGrid.setFields(enableGrid);
		dynamicFormEnableGrid.setAlign(Alignment.LEFT);
		formatForm(dynamicFormEnableGrid);
		layoutGrid.addMember(dynamicFormEnableGrid);
		
		// add line
		com.smartgwt.client.widgets.Label line1 = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutGrid.addMember(line1);
		
		// general
		com.smartgwt.client.widgets.Label labelGeneralBasic = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>General</font></b>");
		labelGeneralBasic.setSize("70px", "20px");
		layoutGrid.addMember(labelGeneralBasic);
		
		final TextItem gridEngineNativeSpec = new TextItem(
		"gridEngineNativeSpec", "Grid engine native specification");
		gridEngineNativeSpec.setDisabled(true);
		
		final TextItem jobNamePrefix = new TextItem("jobNamePrefix",
				"Job name prefix");
		jobNamePrefix.setDisabled(true);
		
		final TextItem jobSubmissionQueue = new TextItem("jobSubmissionQueue",
				"Job submission queue");
		
		final TextItem complexResourceAttributes = new TextItem(
				"complexResourceAttributes", "Complex resource attributes");
		complexResourceAttributes.setDisabled(true);
		
		final NativeCheckboxItem sgeMem = new NativeCheckboxItem();
		sgeMem.setTitle("Report SGE memory usage");
		
		final SelectItem gridVariablesPolicy = new SelectItem();
		gridVariablesPolicy.setDisabled(true);
		gridVariablesPolicy.setTitle("Grid Variables policy");
		
		gridVariablesPolicy.setMultipleAppearance(MultipleAppearance.GRID);
		gridVariablesPolicy.setMultiple(false);
		String[] policyArray = {
				"Disallow all variables, except specified",
				"Allow all variables, except specified" };
		gridVariablesPolicy.setWidth(220);
		gridVariablesPolicy.setDefaultToFirstOption(true);
		gridVariablesPolicy.setValueMap(policyArray);
		
		final TextItem gridVariablesPolicyText = new TextItem(
				"gridVariablesPolicyText", "Specified variables");
		gridVariablesPolicyText.setDisabled(true);
		
		final TextItem prefixBeforeVar = new TextItem("prefixBeforeVar",
				"Prefix before each variable");
		prefixBeforeVar.setDisabled(true);
		
		final SpinnerItem maxParallelThreads = new SpinnerItem(
				"maxParallelThreads",
				"Max number of parallel submission threads");
		maxParallelThreads.setValue(50);
		maxParallelThreads.setMin(1);
		maxParallelThreads.setMax(999);
		maxParallelThreads.setDisabled(true);
		
		final SpinnerItem maxNumResubmissions = new SpinnerItem(
				"maxNumResubmissions",
				"Max number of resubmissions for \"error stated\" jobs");
		maxNumResubmissions.setValue(3);
		maxNumResubmissions.setMin(0);
		maxNumResubmissions.setMax(99);
		maxNumResubmissions.setDisabled(true);
		
		final RadioGroupItem totalNumSlots = new RadioGroupItem();
		totalNumSlots.setTitle("Total number of slots");
		totalNumSlots.setValueMap("Known number", "Get from command");
		totalNumSlots.setVertical(true);
		totalNumSlots.setDefaultValue("Known number");
		totalNumSlots.setDisabled(true);

		final SpinnerItem knownNumber = new SpinnerItem("knownNumber", "Num");
		knownNumber.setValue(0);
		knownNumber.setMin(0);
		knownNumber.setMax(99999);
		knownNumber.setDisabled(true);
		
		final TextItem getFromCommand = new TextItem("getFromCommand", "Cmd");
		getFromCommand.setDisabled(true);
		getFromCommand.setVisible(false);
		
		totalNumSlots.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if(totalNumSlots.getValueAsString().equals("Known number")) {
					knownNumber.hide();
					getFromCommand.show();
				}
				else if(totalNumSlots.getValueAsString().equals("Get from command")) {
					knownNumber.show();
					getFromCommand.hide();
				}
			}
		});
		
		DynamicForm dynamicFormGeneral = new DynamicForm();
		dynamicFormGeneral.setItems( new FormItem[] { gridEngineNativeSpec, jobNamePrefix, jobSubmissionQueue,
				complexResourceAttributes, sgeMem, gridVariablesPolicy,
				gridVariablesPolicyText, prefixBeforeVar, maxParallelThreads,
				maxNumResubmissions, totalNumSlots, knownNumber, getFromCommand });
		
		formatForm(dynamicFormGeneral);
		layoutGrid.addMember(dynamicFormGeneral);
		
		// add line
		com.smartgwt.client.widgets.Label line2 = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutGrid.addMember(line2);
		
		// array job
		com.smartgwt.client.widgets.Label labelArrayJob = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Array Job</font></b>");
		labelArrayJob.setSize("70px", "20px");
		layoutGrid.addMember(labelArrayJob);
		
		final CheckboxItem enableArrayJob = new CheckboxItem();
		enableArrayJob.setTitle("Enable Array Job");
		enableArrayJob.setLabelAsTitle(true);
		enableArrayJob.setDisabled(true);
		
		final IntegerItem chunks = new IntegerItem("chunks",
				"Break into chunks when number of jobs exceeds");
		chunks.setValue(200);
		chunks.setHint("(default: 200)");
		chunks.setDisabled(true);

		final SpinnerItem fileStat = new SpinnerItem("fileStat",
				"Use File Stat with Timeout");
		fileStat.setValue(0);
		fileStat.setMin(0);
		fileStat.setMax(999);
		fileStat.setHint("0 - disabled");
		fileStat.setDisabled(true);

		final IntegerItem chunkSize = new IntegerItem("chunkSize", "Chunk size");
		chunkSize.setValue(50);
		chunkSize.setHint("(default: 50)");
		chunkSize.setDisabled(true);

		final NativeCheckboxItem increaseChunkSize = new NativeCheckboxItem();
		increaseChunkSize.setTitle("Gradually increase chunk size.");
		increaseChunkSize.setDisabled(true);
		
		final IntegerItem maxChunkSize = new IntegerItem("maxChunkSize", "Maximum chunk size");
		maxChunkSize.setValue(400);
		maxChunkSize.setHint("(default: 400)");
		maxChunkSize.setDisabled(true);
		maxChunkSize.setVisible(false);
		
		increaseChunkSize.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if((Boolean) event.getValue()) {
					maxChunkSize.show();
				}
				else {
					maxChunkSize.hide();
				}
			}
		});
		
		enableArrayJob.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				Boolean cstat = (Boolean) event.getValue();

				chunks.setDisabled(!cstat);
				fileStat.setDisabled(!cstat);
				chunkSize.setDisabled(!cstat);
				increaseChunkSize.setDisabled(!cstat);
				maxChunkSize.setDisabled(!cstat);
			}
		});
		
		DynamicForm dynamicFormArrayJob = new DynamicForm();
		dynamicFormArrayJob.setItems( new FormItem[] { enableArrayJob, chunks, fileStat, chunkSize, increaseChunkSize, maxChunkSize } );
		
		formatForm(dynamicFormArrayJob);
		layoutGrid.addMember(dynamicFormArrayJob);
		
		// add line
		com.smartgwt.client.widgets.Label line3 = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutGrid.addMember(line3);
		
		// grid plugin
		com.smartgwt.client.widgets.Label labelGridPlugin = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Grid Plugin</font></b>");
		labelGridPlugin.setSize("90px", "20px");
		layoutGrid.addMember(labelGridPlugin);
		
		final SelectItem gridPlugin = new SelectItem();
		gridPlugin.setTitle("Grid Plugin");
		gridPlugin.setDisabled(true);
		gridPlugin.setMultipleAppearance(MultipleAppearance.GRID);
		gridPlugin.setMultiple(false);
		String[] pluginArray = {
				"JGDI",
				"DRMAA",
				"Customized" };
		gridPlugin.setDefaultValue("Customized");
		gridPlugin.setValueMap(pluginArray);

		final TextItem jarFiles = new TextItem("jarFiles", "Jar file(s)");
		jarFiles.setDisabled(true);

		final TextItem jarFilesClass = new TextItem("jarFilesClass", "Class");
		jarFilesClass.setDisabled(true);

		final CheckboxItem restartService = new CheckboxItem();
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
		
		restartService.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				Boolean cstat = (Boolean) event.getValue();
				
				gridPort.setDisabled(!cstat);
				memItem.setDisabled(!cstat);
				jarFile.setDisabled(!cstat);
			}
		});

		final NativeCheckboxItem enableMonitored = new NativeCheckboxItem();
		enableMonitored.setTitle("Enable monitored job verification");
		enableMonitored.setDisabled(true);

		final NativeCheckboxItem gridEngineAdmin = new NativeCheckboxItem();
		gridEngineAdmin.setTitle("Pipeline user is a grid engine admin");
		gridEngineAdmin.setDisabled(true);

		DynamicForm dynamicFormGridPlugin = new DynamicForm();
		dynamicFormGridPlugin.setItems( new FormItem[] { gridPlugin, jarFiles, restartService, gridPort, memItem, jarFile, 
					enableMonitored, gridEngineAdmin } );
		
		formatForm(dynamicFormGridPlugin);
		layoutGrid.addMember(dynamicFormGridPlugin);
		
		// add line
		com.smartgwt.client.widgets.Label line4 = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutGrid.addMember(line4);
		
		// grid accounting
		com.smartgwt.client.widgets.Label labelGridAccounting = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Grid Accounting</font></b>");
		labelGridAccounting.setSize("120px", "20px");
		layoutGrid.addMember(labelGridAccounting);
		
		final TextItem gridAcctURL = new TextItem("gridAcctURL", "URL");
		gridAcctURL.setDisabled(true);

		final TextItem gridUsername = new TextItem("gridUsername", "Username");
		gridUsername.setDisabled(true);

		final PasswordItem gridPassword = new PasswordItem("gridPassword",
				"Password");
		gridPassword.setDisabled(true);
		
		DynamicForm dynamicFormGridAccounting = new DynamicForm();
		dynamicFormGridAccounting.setItems( new FormItem[] { gridAcctURL, gridUsername, gridPassword } );
		
		formatForm(dynamicFormGridAccounting);
		layoutGrid.addMember(dynamicFormGridAccounting);
		
		enableGrid.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				Boolean cstat = (Boolean) event.getValue();
				
				// toggle fields
				gridEngineNativeSpec.setDisabled(!cstat);
				jobNamePrefix.setDisabled(!cstat);
				complexResourceAttributes.setDisabled(!cstat);
				gridVariablesPolicy.setDisabled(!cstat);
				gridVariablesPolicyText.setDisabled(!cstat);
				prefixBeforeVar.setDisabled(!cstat);
				maxParallelThreads.setDisabled(!cstat);
				maxNumResubmissions.setDisabled(!cstat);
				totalNumSlots.setDisabled(!cstat);
				knownNumber.setDisabled(!cstat);
				getFromCommand.setDisabled(!cstat);
				enableArrayJob.setDisabled(!cstat);
				gridPlugin.setDisabled(!cstat);
				jarFiles.setDisabled(!cstat);
				jarFilesClass.setDisabled(!cstat);
				restartService.setDisabled(!cstat);
				enableMonitored.setDisabled(!cstat);
				gridEngineAdmin.setDisabled(!cstat);
				gridAcctURL.setDisabled(!cstat);
				gridUsername.setDisabled(!cstat);
				gridPassword.setDisabled(!cstat);
				
				// disable array job
				if(enableArrayJob.getValueAsBoolean()) {
					chunks.setDisabled(!cstat);
					fileStat.setDisabled(!cstat);
					chunkSize.setDisabled(!cstat);
					increaseChunkSize.setDisabled(!cstat);
				}
				
				// disable restart services
				if(restartService.getValueAsBoolean()) {
					gridPort.setDisabled(!cstat);
					memItem.setDisabled(!cstat);
					jarFile.setDisabled(!cstat);
				}
			}
		});

		tabGrid.setPane(layoutGrid);
		return tabGrid;
	}

	private Tab setPreferencesTab_Access() {
		Tab tabAccess = new Tab("Access");
		VLayout layoutAccess = new VLayout();

		DynamicForm dynamicForm = new DynamicForm();
		TextItem serverAdmin = new TextItem("serverAdmin", "Server Admins");
		dynamicForm.setFields(serverAdmin);
		getClass();
		formatForm(dynamicForm);
		layoutAccess.addMember(dynamicForm);

		// add line
		com.smartgwt.client.widgets.Label line = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutAccess.addMember(line);

		com.smartgwt.client.widgets.Label label_1 = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Directory Access Control</font></b>");
		label_1.setSize("210px", "49px");
		layoutAccess.addMember(label_1);

		DynamicForm dynamicForm2 = new DynamicForm();
		dynamicForm2.setHeight("157px");
		dynamicForm2.setTitleWidth(200);

		final TextItem textItem_1 = new TextItem();
		textItem_1.setDisabled(true);
		textItem_1.setAlign(Alignment.LEFT);
		textItem_1.setTitle("Controlled users");
		textItem_1.setName("textbox_1");
		final TextItem textItem_2 = new TextItem();
		textItem_2.setDisabled(true);
		textItem_2.setAlign(Alignment.LEFT);
		textItem_2.setTitle("Controlled directories");
		textItem_2.setName("textbox_2");

		final SelectItem selectItem = new SelectItem("newSelectItem_1",
				"Access control mode");
		selectItem.setWidth(450);
		selectItem.setDefaultToFirstOption(true);
		selectItem.setAlign(Alignment.LEFT);
		selectItem.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {

				// case0
				if ((selectItem.getValueAsString())
						.equals("0 -Executables: no restrictions. Remote File Browser: no restrictions.")) {
					textItem_1.setTitle("Controlled users");
					textItem_2.setTitle("Controlled directories");
					textItem_1.setDisabled(true);
					textItem_2.setDisabled(true);
				}

				// case1
				else if ((selectItem.getValueAsString())
						.equals("1 - Executables: restricted below. Remote File Browser: no restrictions")) {
					textItem_1.setDisabled(true);
					textItem_2.setDisabled(true);
					textItem_1.setTitle("Restricted users");
					textItem_2
							.setTitle("Restricted users only have access in these directories");
					textItem_1.setDisabled(false);
					textItem_2.setDisabled(false);
				}
				// case2
				else if ((selectItem.getValueAsString())
						.equals("2 - Executables: enabled. Remote File Browser: no restrictions")) {
					textItem_1.setDisabled(true);
					textItem_2.setDisabled(true);
					textItem_1.setTitle("Special users with no restrictions");
					textItem_2
							.setTitle("Regular users only have access in these directories");
					textItem_1.setDisabled(false);
					textItem_2.setDisabled(false);
				}
				// case3
				else if ((selectItem.getValueAsString())
						.equals("3 - Executables: restricted below. Remote File Browser: restricted below")) {
					textItem_1.setDisabled(true);
					textItem_2.setDisabled(true);
					textItem_1.setTitle("Restricted users");
					textItem_2
							.setTitle("Restricted users only have access in these directories");
					textItem_1.setDisabled(false);
					textItem_2.setDisabled(false);
				}
				// case4
				else if ((selectItem.getValueAsString())
						.equals("4 - Executables: enabled below. Remote File Browser: enabled below")) {
					textItem_1.setDisabled(true);
					textItem_2.setDisabled(true);
					textItem_1.setTitle("Special users with no restrictions");
					textItem_2
							.setTitle("Regular users only have access in these directories");
					textItem_1.setDisabled(false);
					textItem_2.setDisabled(false);
				}
				// case5
				else if ((selectItem.getValueAsString())
						.equals("5 - Executables: restricted below. Remote File Browser: same as Shell permissions")) {
					textItem_1.setDisabled(true);
					textItem_2.setDisabled(true);
					textItem_1.setTitle("Restricted users");
					textItem_2
							.setTitle("Restricted users only have access in these directories");
					textItem_1.setDisabled(false);
					textItem_2.setDisabled(false);
				}
				// case6
				else if ((selectItem.getValueAsString())
						.equals("6 - Executables: enabled below. Remote File Browser: same as Shell permissions")) {
					textItem_1.setDisabled(true);
					textItem_2.setDisabled(true);
					textItem_1.setTitle("Special users with no restrictions");
					textItem_2
							.setTitle("Regular users only have access in these directories");
					textItem_1.setDisabled(false);
					textItem_2.setDisabled(false);
				}
				// case7
				else if ((selectItem.getValueAsString())
						.equals("7 - Executables: same as Shell permissions. Remote File Browser: same as Shell")) {
					textItem_1.setTitle("Controlled users");
					textItem_2.setTitle("Controlled directories");
					textItem_1.setDisabled(true);
					textItem_2.setDisabled(true);
				}
			}
		});

		selectItem.setMultipleAppearance(MultipleAppearance.GRID);
		selectItem.setMultiple(false);
		dynamicForm2.setFields(new FormItem[] { selectItem, textItem_1,
				textItem_2 });
		String[] sitesArray = {
				"0 -Executables: no restrictions. Remote File Browser: no restrictions",
				"1 - Executables: restricted below. Remote File Browser: no restrictions",
				"2 - Executables: enabled. Remote File Browser: no restrictions",
				"3 - Executables: restricted below. Remote File Browser: restricted below",
				"4 - Executables: enabled below. Remote File Browser: enabled below",
				"5 - Executables: restricted below. Remote File Browser: same as Shell permissions",
				"6 - Executables: enabled below. Remote File Browser: same as Shell permissions",
				"7 - Executables: same as Shell permissions. Remote File Browser: same as Shell" };

		selectItem.setValueMap(sitesArray);
		formatForm(dynamicForm2);
		layoutAccess.addMember(dynamicForm2);

		// add line
		com.smartgwt.client.widgets.Label line2 = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutAccess.addMember(line2);

		final SpinnerItem spinnerItem_1 = new SpinnerItem("si_usrpcfreeslots",
				"One user cannot take more than");
		spinnerItem_1.setValue(50);
		spinnerItem_1.setHint("% of free slots at submission time");
		spinnerItem_1.setShowHint(true);
		spinnerItem_1.setMin(0);
		spinnerItem_1.setMax(100);
		spinnerItem_1.setDisabled(true);

		DynamicForm dynamicForm_1 = new DynamicForm();
		CheckboxItem checkboxItem_1 = new CheckboxItem(
				"cb_enableubjm",
				"Enable (NOTE: Enabling user management may require some time to adjust user usage accuracy)");
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

		CanvasItem canvasItem_1 = new CanvasItem("ci_usrbjbmngmt",
				"User-Based Job Management");
		canvasItem_1.setCanvas(dynamicForm_1);

		DynamicForm dynamicForm3 = new DynamicForm();
		final SpinnerItem spinnerItem_2 = new SpinnerItem("si_maxwrkflpu",
				"Maximum active (running & paused) workflows per user");
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

		CanvasItem canvasItem_2 = new CanvasItem("ci_wrkflmngmt",
				"Workflow Management");
		canvasItem_2.setCanvas(dynamicForm_2);

		dynamicForm3.setFields(canvasItem_1, spinnerItem_1, canvasItem_2,
				spinnerItem_2);

		formatForm(dynamicForm3);
		layoutAccess.addMember(dynamicForm3);
		tabAccess.setPane(layoutAccess);
		return tabAccess;
	}

	private Tab setPreferencesTab_Packages() {
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

	private Tab setPreferencesTab_Executables() {
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

	private Tab setPreferencesTab_Advanced() {
		Tab tabAdvanced = new Tab("Advanced");
		return tabAdvanced;
	}

	private Tab setUploadsTab() {
		Tab tabUpload = new Tab("Upload");

		final VerticalPanel fileuploadPanel = new VerticalPanel();
		final Map<String, Image> cancelButtons = new LinkedHashMap<String, Image>();

		// Refresh Button for all Tabs
		final LONIDataRequester dataRequester = new LONIDataRequester(
				reqResourceXMLService, reqConfigurationXMLService, memChart,
				thrdChart);

		Button appRefreshButton = new Button("Refresh All Tabs");
		appRefreshButton.setAlign(Alignment.CENTER);
		appRefreshButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dataRequester.refreshTabs();
			}
		});

		final LONIFileUploader LONIfileUploader = new LONIFileUploader(
				cancelButtons, fileuploadPanel, dataRequester);

		if (Uploader.isAjaxUploadWithProgressEventsSupported()) {
			final LONIDragandDropLabel fileuploadLabel = new LONIDragandDropLabel(
					"Drop Files", LONIfileUploader, cancelButtons,
					fileuploadPanel);
			fileuploadPanel.add(fileuploadLabel);
		}
		fileuploadPanel.add(LONIfileUploader);

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.add(fileuploadPanel);

		VLayout uploadLayout = new VLayout();
		uploadLayout.addMember(horizontalPanel);

		uploadLayout.addMember(appRefreshButton);

		DynamicForm fileUploadForm = new DynamicForm();
		fileUploadForm.setFields(new FormItem[] { new TextItem("weburl",
				"XML file Web URL") });
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

	private void fillWorkFlowsTab(DataSource workFlowsSource,
			ListGridField stopfield, ListGridField pausefield,
			ListGridField viewfield) {
		if (workFlowsSource != null) {
			workFlowsSource.invalidateCache();
			workFlowsSource = WorkFlowsXmlDS.getInstance();
			listWorkflows.setDataSource(workFlowsSource);
			listWorkflows.fetchData();
		}

		listWorkflows.setFields(new ListGridField("workflowID", "Workflow ID"),
				new ListGridField("username", "Username"), new ListGridField(
						"state", "State"), new ListGridField("startTime",
						"Start Time"),
				new ListGridField("endTime", "End Time"), new ListGridField(
						"duration", "Duration"), new ListGridField("numofnode",
						"N"), new ListGridField("numofinstances", "I"),
				new ListGridField("numBacklab", "B"), new ListGridField(
						"numSubmitting", "S"), new ListGridField("numQueued",
						"Q"), new ListGridField("numRunning", "R"),
				new ListGridField("numCompleted", "C"), stopfield, pausefield,
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

	private void fillUserUsageTab(DataSource userUsageSource,
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
