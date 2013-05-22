package edu.ucla.loni.pipeline.client.MainPage.Preferences.Grid;

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
	
	public GridTab() {
		
	}
	
	public Tab setTab() {
		Tab tabGrid = new Tab("Grid");

		VLayout layoutGrid = new VLayout();

		DynamicForm dynamicFormEnableGrid = new DynamicForm();
		CheckboxItem enableGrid = new CheckboxItem();
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
		
		final TextItem gridAcctURL = new TextItem("gridAcctURL", "URL");
		gridAcctURL.setDisabled(true);

		final TextItem gridUsername = new TextItem("gridUsername", "Username");
		gridUsername.setDisabled(true);

		final PasswordItem gridPassword = new PasswordItem("gridPassword",
				"Password");
		gridPassword.setDisabled(true);
		
		DynamicForm dynamicFormGridAccounting = new DynamicForm();
		dynamicFormGridAccounting.setItems( new FormItem[] { gridAcctURL, gridUsername, gridPassword } );
		
		MainPageUtils.formatForm(dynamicFormGridAccounting);
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
}
