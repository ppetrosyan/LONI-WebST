package edu.ucla.loni.pipeline.client.MainPage.Preferences.Grid;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.NativeCheckboxItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

import edu.ucla.loni.pipeline.client.MainPage.Utilities.MainPageUtils;

public class GridTab {
	private DynamicForm dynamicFormEnableGrid;
	private DynamicForm dynamicFormGeneral;
	private DynamicForm dynamicFormArrayJob;
	private DynamicForm dynamicFormGridPlugin;
	private DynamicForm dynamicFormGridAccounting;
	
	private TextItem gridEngineNativeSpec;
	private TextItem jobNamePrefix;
	private TextItem jobSubmissionQueue;
	private TextItem complexResourceAttributes;
	private NativeCheckboxItem sgeMem;
	private SelectItem gridVariablesPolicy;
	private TextItem gridVariablesPolicyText;
	private TextItem prefixBeforeVar;
	private SpinnerItem maxParallelThreads;
	private SpinnerItem maxNumResubmissions;
	private RadioGroupItem totalNumSlots;
	private SpinnerItem knownNumber;
	private TextItem getFromCommand;
	private CheckboxItem enableArrayJob;
	private IntegerItem chunks;
	private SpinnerItem fileStat;
	private IntegerItem chunkSize;
	private NativeCheckboxItem increaseChunkSize;
	private IntegerItem maxChunkSize;
	private SelectItem gridPlugin;
	private TextItem jarFiles;
	private TextItem jarFilesClass;
	private CheckboxItem restartService;
	private IntegerItem gridPort;
	private IntegerItem memItem;
	private TextItem jarFile;
	private NativeCheckboxItem enableMonitored;
	private NativeCheckboxItem gridEngineAdmin;
	private TextItem gridAcctURL;
	private TextItem gridUsername;
	private PasswordItem gridPassword;
	
	public DynamicForm getGridEnable() {
		return dynamicFormEnableGrid;
	}
	
	public DynamicForm getGridGeneral() {
		return dynamicFormGeneral;
	}
	
	public GridTab() {
		
	}
	
	public Tab setTab() {
		Tab tabGrid = new Tab("Grid");

		VLayout layoutGrid = new VLayout();

		dynamicFormEnableGrid = new DynamicForm();
		CheckboxItem enableGrid = new CheckboxItem("enableGrid");
		enableGrid.setTitle("Enable Grid");
		enableGrid.setLabelAsTitle(true);
		dynamicFormEnableGrid.setFields(enableGrid);
		dynamicFormEnableGrid.setAlign(Alignment.LEFT);
		MainPageUtils.formatForm(dynamicFormEnableGrid);
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
		
		gridEngineNativeSpec = new TextItem(
		"gridEngineNativeSpec", "Grid engine native specification");
		gridEngineNativeSpec.setDisabled(true);
		
		jobNamePrefix = new TextItem("jobNamePrefix",
				"Job name prefix");
		jobNamePrefix.setDisabled(true);
		
		jobSubmissionQueue = new TextItem("jobSubmissionQueue",
				"Job submission queue");
		
		complexResourceAttributes = new TextItem(
				"complexResourceAttributes", "Complex resource attributes");
		complexResourceAttributes.setDisabled(true);
		
		sgeMem = new NativeCheckboxItem("sgeMem");
		sgeMem.setTitle("Report SGE memory usage");
		
		gridVariablesPolicy = new SelectItem("gridVariablesPolicy");
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
		
		gridVariablesPolicyText = new TextItem(
				"gridVariablesPolicyText", "Specified variables");
		gridVariablesPolicyText.setDisabled(true);
		
		prefixBeforeVar = new TextItem("prefixBeforeVar",
				"Prefix before each variable");
		prefixBeforeVar.setDisabled(true);
		
		maxParallelThreads = new SpinnerItem(
				"maxParallelThreads",
				"Max number of parallel submission threads");
		maxParallelThreads.setValue(50);
		maxParallelThreads.setMin(1);
		maxParallelThreads.setMax(999);
		maxParallelThreads.setDisabled(true);
		
		maxNumResubmissions = new SpinnerItem(
				"maxNumResubmissions",
				"Max number of resubmissions for \"error stated\" jobs");
		maxNumResubmissions.setValue(3);
		maxNumResubmissions.setMin(0);
		maxNumResubmissions.setMax(99);
		maxNumResubmissions.setDisabled(true);
		
		totalNumSlots = new RadioGroupItem("totalNumSlots");
		totalNumSlots.setTitle("Total number of slots");
		totalNumSlots.setValueMap("Known number", "Get from command");
		totalNumSlots.setVertical(true);
		totalNumSlots.setDefaultValue("Known number");
		totalNumSlots.setDisabled(true);

		knownNumber = new SpinnerItem("knownNumber", "Num");
		knownNumber.setValue(0);
		knownNumber.setMin(0);
		knownNumber.setMax(99999);
		knownNumber.setDisabled(true);
		
		getFromCommand = new TextItem("getFromCommand", "Cmd");
		getFromCommand.setDisabled(true);
		getFromCommand.setVisible(false);
		
		totalNumSlots.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				toggleTotalNumSlotsOnEvent();
			}
		});
		
		dynamicFormGeneral = new DynamicForm();
		dynamicFormGeneral.setItems( new FormItem[] { gridEngineNativeSpec, jobNamePrefix, jobSubmissionQueue,
				complexResourceAttributes, sgeMem, gridVariablesPolicy,
				gridVariablesPolicyText, prefixBeforeVar, maxParallelThreads,
				maxNumResubmissions, totalNumSlots, knownNumber, getFromCommand });
		
		MainPageUtils.formatForm(dynamicFormGeneral);
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
		
		enableArrayJob = new CheckboxItem("enableArrayJob");
		enableArrayJob.setTitle("Enable Array Job");
		enableArrayJob.setLabelAsTitle(true);
		enableArrayJob.setDisabled(true);
		
		chunks = new IntegerItem("chunks",
				"Break into chunks when number of jobs exceeds");
		chunks.setValue(200);
		chunks.setHint("(default: 200)");
		chunks.setDisabled(true);

		fileStat = new SpinnerItem("fileStat",
				"Use File Stat with Timeout");
		fileStat.setValue(0);
		fileStat.setMin(0);
		fileStat.setMax(999);
		fileStat.setHint("0 - disabled");
		fileStat.setDisabled(true);

		chunkSize = new IntegerItem("chunkSize", "Chunk size");
		chunkSize.setValue(50);
		chunkSize.setHint("(default: 50)");
		chunkSize.setDisabled(true);

		increaseChunkSize = new NativeCheckboxItem("increaseChunkSize");
		increaseChunkSize.setTitle("Gradually increase chunk size.");
		increaseChunkSize.setDisabled(true);
		
		maxChunkSize = new IntegerItem("maxChunkSize", "Maximum chunk size");
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
		
		dynamicFormArrayJob = new DynamicForm();
		dynamicFormArrayJob.setItems( new FormItem[] { enableArrayJob, chunks, fileStat, chunkSize, increaseChunkSize, maxChunkSize } );
		
		MainPageUtils.formatForm(dynamicFormArrayJob);
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
		
		gridPlugin = new SelectItem("gridPlugin");
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

		jarFiles = new TextItem("jarFiles", "Jar file(s)");
		jarFiles.setDisabled(true);

		jarFilesClass = new TextItem("jarFilesClass", "Class");
		jarFilesClass.setDisabled(true);

		restartService = new CheckboxItem("restartService");
		restartService.setTitle("Use Restartable Service");
		restartService.setDisabled(true);

		gridPort = new IntegerItem("gridPort", "Port");
		gridPort.setValue(8111);
		gridPort.setHint("(default: 8111)");
		gridPort.setDisabled(true);

		memItem = new IntegerItem("memItem", "Memory (MB)");
		memItem.setDisabled(true);

		jarFile = new TextItem("jarFile", "JAR File");
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

		enableMonitored = new NativeCheckboxItem("enableMonitored");
		enableMonitored.setTitle("Enable monitored job verification");
		enableMonitored.setDisabled(true);

		gridEngineAdmin = new NativeCheckboxItem("gridEngineAdmin");
		gridEngineAdmin.setTitle("Pipeline user is a grid engine admin");
		gridEngineAdmin.setDisabled(true);

		dynamicFormGridPlugin = new DynamicForm();
		dynamicFormGridPlugin.setItems( new FormItem[] { gridPlugin, jarFiles, restartService, gridPort, memItem, jarFile, 
					enableMonitored, gridEngineAdmin } );
		
		MainPageUtils.formatForm(dynamicFormGridPlugin);
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
		
		gridAcctURL = new TextItem("gridAcctURL", "URL");
		gridAcctURL.setDisabled(true);

		gridUsername = new TextItem("gridUsername", "Username");
		gridUsername.setDisabled(true);

		gridPassword = new PasswordItem("gridPassword",
				"Password");
		gridPassword.setDisabled(true);
		
		dynamicFormGridAccounting = new DynamicForm();
		dynamicFormGridAccounting.setItems( new FormItem[] { gridAcctURL, gridUsername, gridPassword } );
		
		MainPageUtils.formatForm(dynamicFormGridAccounting);
		layoutGrid.addMember(dynamicFormGridAccounting);
		
		enableGrid.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				toggleAllFieldsOnEvent(event);
			}
		});

		tabGrid.setPane(layoutGrid);
		return tabGrid;
	}
	
	// xml toggle
	public void toggleTotalNumSlots() {
		if(dynamicFormGeneral.getItem("totalNumSlots").getValue().equals("Known number")) {
			dynamicFormGeneral.getItem("knownNumber").show();
			dynamicFormGeneral.getItem("getFromCommand").hide();
		}
		else if(dynamicFormGeneral.getItem("totalNumSlots").getValue().equals("Get from command")) {
			dynamicFormGeneral.getItem("knownNumber").hide();
			dynamicFormGeneral.getItem("getFromCommand").show();
		}
	}
	
	// change handler must toggle radio buttons in reverse
	public void toggleTotalNumSlotsOnEvent() {
		if(dynamicFormGeneral.getItem("totalNumSlots").getValue().equals("Known number")) {
			dynamicFormGeneral.getItem("knownNumber").hide();
			dynamicFormGeneral.getItem("getFromCommand").show();
		}
		else if(dynamicFormGeneral.getItem("totalNumSlots").getValue().equals("Get from command")) {
			dynamicFormGeneral.getItem("knownNumber").show();
			dynamicFormGeneral.getItem("getFromCommand").hide();
		}
	}
	
	// xml toggle
	public void toggleAllFields(Boolean cstat) {
		
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
			maxChunkSize.setDisabled(!cstat);
		}
		
		// disable restart services
		if(restartService.getValueAsBoolean()) {
			gridPort.setDisabled(!cstat);
			memItem.setDisabled(!cstat);
			jarFile.setDisabled(!cstat);
		}
	}
	
	// change handler toggle
	public void toggleAllFieldsOnEvent(ChangeEvent event) {
		Boolean cstat = (Boolean) event.getValue();
		
		toggleAllFields(cstat);
	}
	
	public void parseGridXML(Document doc) {
		
		// enable grid section
		Node enableGrid = (Node) doc.getElementsByTagName("EnableGrid").item(0);
		if(enableGrid != null) {
			Boolean enableGridVal = Boolean.valueOf(enableGrid.getFirstChild().getNodeValue());
			dynamicFormEnableGrid.getItem("enableGrid").setValue(enableGridVal);
			toggleAllFields(enableGridVal);
		}
		
		// general section
		Node gridEngineNativeSpec = (Node) doc.getElementsByTagName("GridEngineNativeSpec").item(0);
		if(gridEngineNativeSpec != null) {
			String gridEngineNativeSpecVal = gridEngineNativeSpec.getFirstChild().getNodeValue();
			dynamicFormGeneral.getItem("gridEngineNativeSpec").setValue(gridEngineNativeSpecVal);
		}
		
		Node jobNamePrefix = (Node) doc.getElementsByTagName("JobNamePrefix").item(0);
		if(jobNamePrefix != null) {
			String jobNamePrefixVal = jobNamePrefix.getFirstChild().getNodeValue();
			dynamicFormGeneral.getItem("jobNamePrefix").setValue(jobNamePrefixVal);
		}
		
		Node jobSubmissionQueue = (Node) doc.getElementsByTagName("JobSubmissionQueue").item(0);
		if(jobSubmissionQueue != null) {
			String jobSubQueueVal = jobSubmissionQueue.getFirstChild().getNodeValue();
			dynamicFormGeneral.getItem("jobSubmissionQueue").setValue(jobSubQueueVal);
		}
		
		Node complexResourceAttributes = (Node) doc.getElementsByTagName("ComplexResourceAttributes").item(0);
		if(complexResourceAttributes != null) {
			String complexResAttrVal = complexResourceAttributes.getFirstChild().getNodeValue();
			dynamicFormGeneral.getItem("complexResourceAttributes").setValue(complexResAttrVal);
		}
		
		Node sgeMem = (Node) doc.getElementsByTagName("ReportSGE").item(0);
		if(sgeMem != null) {
			Boolean sgeMemVal = Boolean.valueOf(sgeMem.getFirstChild().getNodeValue());
			dynamicFormGeneral.getItem("sgeMem").setValue(sgeMemVal);
		}
		
		Node gridVariablesPolicy = (Node) doc.getElementsByTagName("GridVariablesPolicy").item(0);
		if(gridVariablesPolicy != null) {
			String gridVarPolicyVal = gridVariablesPolicy.getFirstChild().getNodeValue();
			dynamicFormGeneral.getItem("gridVariablesPolicy").setValue(gridVarPolicyVal);
		}
		
		Node gridVariablesPolicyText = (Node) doc.getElementsByTagName("SpecifiedVariables").item(0);
		if(gridVariablesPolicyText != null) {
			String gridVarPolicyTextVal = gridVariablesPolicyText.getFirstChild().getNodeValue();
			dynamicFormGeneral.getItem("gridVariablesPolicyText").setValue(gridVarPolicyTextVal);
		}
		
		Node prefixBeforeVar = (Node) doc.getElementsByTagName("PrefixBeforeVar").item(0);
		if(prefixBeforeVar != null) {
			String prefixBeforeVarVal = prefixBeforeVar.getFirstChild().getNodeValue();
			dynamicFormGeneral.getItem("prefixBeforeVar").setValue(prefixBeforeVarVal);
		}
		
		Node maxParallelThreads = (Node) doc.getElementsByTagName("MaxNumParallelSub").item(0);
		if(maxParallelThreads != null) {
			int maxParThreadsVal = Integer.parseInt(maxParallelThreads.getFirstChild().getNodeValue());
			dynamicFormGeneral.getItem("maxParallelThreads").setValue(maxParThreadsVal);
		}
		
		Node maxNumResubmissions = (Node) doc.getElementsByTagName("MaxNumResubmissions").item(0);
		if(maxNumResubmissions != null) {
			int maxNumResubVal = Integer.parseInt(maxNumResubmissions.getFirstChild().getNodeValue());
			dynamicFormGeneral.getItem("maxNumResubmissions").setValue(maxNumResubVal);
		}
		
		Node totalNumSlots = (Node) doc.getElementsByTagName("TotalNumSlots").item(0);
		if(totalNumSlots != null) {
			String totalNumSlotsVal = totalNumSlots.getFirstChild().getNodeValue();
			dynamicFormGeneral.getItem("totalNumSlots").setValue(totalNumSlotsVal);
			toggleTotalNumSlots();
			
			Node totalNumSlotsTextVal = (Node) doc.getElementsByTagName("TotalNumSlotsVal").item(0);
			if(totalNumSlotsVal != null) {
				if(totalNumSlotsVal.equals("Known number")) {
					int knownNumberVal = Integer.parseInt(totalNumSlotsTextVal.getFirstChild().getNodeValue());
					dynamicFormGeneral.getItem("knownNumber").setValue(knownNumberVal);
				}
				else if(totalNumSlotsVal.equals("Get from command")) {
					String getFromCommandVal = totalNumSlotsTextVal.getFirstChild().getNodeValue();
					dynamicFormGeneral.getItem("getFromCommand").setValue(getFromCommandVal);
				}
			}
		}
		
		// TODO: fill in the sections below

		// array job section
		
		// grid plugin section
		
		// grid accounting section
		
	}
}
