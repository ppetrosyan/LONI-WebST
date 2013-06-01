package edu.ucla.loni.pipeline.client.MainPage.Preferences.Advanced;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.NativeCheckboxItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.BlurbItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

import edu.ucla.loni.pipeline.client.MainPage.Utilities.MainPageUtils;

public class AdvancedTab {
	
	public AdvancedTab() {
		
	}
	
	public Tab setTab() {
		Tab tabAdvanced = new Tab("Advanced");

		VLayout layoutAdvanced = new VLayout();

		// failover
		com.smartgwt.client.widgets.Label labelAdvancedFailover = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Failover</font></b>");
		labelAdvancedFailover.setSize("70px", "20px");
		layoutAdvanced.addMember(labelAdvancedFailover);

		DynamicForm formAdvancedFailover = new DynamicForm();

		NativeCheckboxItem failoverEnable = new NativeCheckboxItem();
		failoverEnable.setTitle("Enable");
    
		SpinnerItem slaveFreq = new SpinnerItem("slaveFreq", "Slave: Master Check Frequency");
		slaveFreq.setValue("5");

		SpinnerItem slaveRetries = new SpinnerItem("slaveRetries", "Slave: Number of Retries");
		slaveRetries.setValue("3");

		TextItem aliasInterface = new TextItem("aliasInterface", "Alias Interface");
    
		IntegerItem aliasSubinterface = new IntegerItem("aliasSubinterface", "Alias Subinterface Number");
		aliasSubinterface.setValue("0");
    
		TextItem postFailover = new TextItem("postFailover", "Post-failover Script");

		formAdvancedFailover.setFields(new FormItem[] { failoverEnable, slaveFreq, 
				slaveRetries, aliasInterface, aliasSubinterface, postFailover });
		MainPageUtils.formatForm(formAdvancedFailover);
		layoutAdvanced.addMember(formAdvancedFailover);

		// add line
		com.smartgwt.client.widgets.Label line = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutAdvanced.addMember(line);

		// logEmail
		com.smartgwt.client.widgets.Label labelAdvancedLog = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Log E-Mail</font></b>");
		labelAdvancedLog.setSize("70px", "20px");
		layoutAdvanced.addMember(labelAdvancedLog);

		DynamicForm formAdvancedLog = new DynamicForm();

		TextItem recipients = new TextItem("recipients", "E-mail Recipients");
		TextItem sender = new TextItem("sender", "E-mail Sender");
		TextItem SMTPHost = new TextItem("SMTPHost", "SMTP Host");

		formAdvancedLog.setFields(new FormItem[] { recipients, sender, SMTPHost });
		MainPageUtils.formatForm(formAdvancedLog);
		layoutAdvanced.addMember(formAdvancedLog);
    
        // add line
		com.smartgwt.client.widgets.Label line2 = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutAdvanced.addMember(line2);

		// network
		com.smartgwt.client.widgets.Label labelAdvancedNetwork = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Network</font></b>");
		labelAdvancedNetwork.setSize("70px", "20px");
		layoutAdvanced.addMember(labelAdvancedNetwork);

		DynamicForm formAdvancedNetwork = new DynamicForm();

		SpinnerItem packetSize = new SpinnerItem("packetSize", "Packet Size");
		packetSize.setValue("32768");
    
		SpinnerItem connTimeout = new SpinnerItem("connTimeout", "Connection Timeout (seconds)");
		connTimeout.setValue("3");

		formAdvancedNetwork.setFields(new FormItem[] { packetSize, connTimeout });
		MainPageUtils.formatForm(formAdvancedNetwork);
		layoutAdvanced.addMember(formAdvancedNetwork);
		
		// add line
		com.smartgwt.client.widgets.Label line3 = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutAdvanced.addMember(line3);
		
		//*
		// other
		com.smartgwt.client.widgets.Label labelAdvancedOther = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Other</font></b>");
		labelAdvancedOther.setSize("70px", "20px");
		layoutAdvanced.addMember(labelAdvancedOther);
		
		DynamicForm formAdvancedOther = new DynamicForm();
		
		SpinnerItem maxThreads = new SpinnerItem("maxThreads", "Max Threads for Active Jobs");
		maxThreads.setValue("4");
		
		CheckboxItem autoClean = new CheckboxItem("autoClean");
		autoClean.setTitle("Automatically Clean Up Old Files in Temporary Directory");
		autoClean.setLabelAsTitle(true);
		
		SpinnerItem maxGenerator = new SpinnerItem("maxThreads", "Max Metadata Generator Threads");
		maxGenerator.setValue("5");
		
		SpinnerItem serverRefresh = new SpinnerItem("serverRefresh", "Server Status Update Interval (seconds)");
		serverRefresh.setValue("10");
		
		SpinnerItem maxMissed = new SpinnerItem("maxMissed", "Max Consecutive Missed Updates");
		maxMissed.setValue("3");
		
		SpinnerItem directoryTimeout = new SpinnerItem("directoryTimeout", "Recursive Directory Listing Timeout (seconds)");
		directoryTimeout.setValue("120");

		TextItem extQName = new TextItem("extQName", "External Network Access Queue Name");
		
		BlurbItem missing = new BlurbItem("missing");
		missing.setDefaultValue("Generate warning instead error upon encountering missing...");
		
		NativeCheckboxItem missingMod = new NativeCheckboxItem("missingMod");
		missingMod.setTitle("Module Executable");
		
		NativeCheckboxItem missingFile = new NativeCheckboxItem("missingFile");
		missingFile.setTitle("File Parameters");
		
		formAdvancedOther.setFields(new FormItem[] { maxThreads, autoClean, maxGenerator, serverRefresh, 
				                                     maxMissed, directoryTimeout, extQName, 
				                                     missing, missingMod, missingFile });
		MainPageUtils.formatForm(formAdvancedOther);
		layoutAdvanced.addMember(formAdvancedOther);
		// */

		tabAdvanced.setPane(layoutAdvanced);
		return tabAdvanced;
	}
}
