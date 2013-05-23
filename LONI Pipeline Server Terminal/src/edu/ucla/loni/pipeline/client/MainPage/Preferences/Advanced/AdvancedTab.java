package edu.ucla.loni.pipeline.client.MainPage.Preferences.Advanced;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.NativeCheckboxItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
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
    HLayout hlayout = new HLayout();
		com.smartgwt.client.widgets.Label labelAdvancedFailover = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Failover</font></b>");
		labelAdvancedFailover.setSize("70px", "20px");
		layoutAdvanced.addMember(labelAdvancedFailover);

		DynamicForm formAdvancedFailover = new DynamicForm();

		NativeCheckboxItem failoverEnable = new NativeCheckboxItem();
		failoverEnable.setTitle("Enable");
    
		SpinnerItem slaveFreq = new SpinnerItem("slaveFreq", "Slave Update Frequency");
		slaveFreq.setValue("5");

		SpinnerItem slaveRetries = new SpinnerItem("slaveRetries", "Number of Retries");
		slaveRetries.setValue("3");

		TextItem aliasInterface = new TextItem("aliasInterface", "Alias Interface");
    
    /*
		NativeCheckboxItem secureCheckbox = new NativeCheckboxItem();
		secureCheckbox.setTitle("Secure");

		TextItem scrdirText = new TextItem("scrdir", "Scratch Directory");

		TextItem logfileText = new TextItem("logfile", "Log File");

		NativeCheckboxItem escalationCheckbox = new NativeCheckboxItem();
		escalationCheckbox
				.setTitle("Use privilege escalation: Pipeline server will run commands as the user (sudo as user)");

		NativeCheckboxItem enableGuestCheckbox = new NativeCheckboxItem();
		enableGuestCheckbox.setTitle("Enable guests");
    // */

		formAdvancedFailover.setFields(new FormItem[] { failoverEnable, slaveFreq, 
				slaveRetries, aliasInterface });
		MainPageUtils.formatForm(formAdvancedFailover);
		layoutAdvanced.addMember(formAdvancedFailover);

		// add line
		com.smartgwt.client.widgets.Label line = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutAdvanced.addMember(line);

		tabAdvanced.setPane(layoutAdvanced);
		return tabAdvanced;
	}
}
