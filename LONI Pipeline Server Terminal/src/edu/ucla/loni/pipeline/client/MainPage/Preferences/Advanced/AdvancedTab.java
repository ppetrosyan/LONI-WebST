/*
 * This file is part of LONI Pipeline Web-based Server Terminal.
 * 
 * LONI Pipeline Web-based Server Terminal is free software: 
 * you can redistribute it and/or modify it under the terms of the 
 * GNU Lesser General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * LONI Pipeline Web-based Server Terminal is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the 
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public License
 * along with LONI Pipeline Web-based Server Terminal.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package edu.ucla.loni.pipeline.client.MainPage.Preferences.Advanced;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.BlurbItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.NativeCheckboxItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
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
		HLayout layoutAdvancedLabel = new HLayout();
		com.smartgwt.client.widgets.Label labelAdvancedFailover = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Failover</font></b>");
		labelAdvancedFailover.setSize("200px", "20px");
		layoutAdvancedLabel.addMember(labelAdvancedFailover);

		DynamicForm formAdvancedFailoverEnable = new DynamicForm();
		NativeCheckboxItem failoverEnable = new NativeCheckboxItem();
		failoverEnable.setTitle("Enable");

		formAdvancedFailoverEnable.setFields(new FormItem[] { failoverEnable });
		layoutAdvancedLabel.addMember(formAdvancedFailoverEnable);

		layoutAdvanced.addMember(layoutAdvancedLabel);

		DynamicForm formAdvancedFailover = new DynamicForm();

		final SpinnerItem slaveFreq = new SpinnerItem("slaveFreq",
				"Slave: Master Check Frequency");
		slaveFreq.setValue("5");
		slaveFreq.setDisabled(true);

		final SpinnerItem slaveRetries = new SpinnerItem("slaveRetries",
				"Slave: Number of Retries");
		slaveRetries.setValue("3");
		slaveRetries.setDisabled(true);

		final TextItem aliasInterface = new TextItem("aliasInterface",
				"Alias Interface");
		aliasInterface.setDisabled(true);

		final IntegerItem aliasSubinterface = new IntegerItem(
				"aliasSubinterface", "Alias Subinterface Number");
		aliasSubinterface.setValue("0");
		aliasSubinterface.setDisabled(true);

		final TextItem postFailover = new TextItem("postFailover",
				"Post-failover Script");
		postFailover.setDisabled(true);

		formAdvancedFailover
				.setFields(new FormItem[] { slaveFreq, slaveRetries,
						aliasInterface, aliasSubinterface, postFailover });
		MainPageUtils.formatForm(formAdvancedFailover);
		layoutAdvanced.addMember(formAdvancedFailover);

		failoverEnable.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				Boolean cstat = (Boolean) event.getValue();
				slaveFreq.setDisabled(!cstat);
				slaveRetries.setDisabled(!cstat);
				aliasInterface.setDisabled(!cstat);
				aliasSubinterface.setDisabled(!cstat);
				postFailover.setDisabled(!cstat);
			}
		});

		// add line
		com.smartgwt.client.widgets.Label line = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutAdvanced.addMember(line);

		// logEmail
		com.smartgwt.client.widgets.Label labelAdvancedLog = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Log E-Mail</font></b>");
		labelAdvancedLog.setSize("200px", "20px");
		layoutAdvanced.addMember(labelAdvancedLog);

		DynamicForm formAdvancedLog = new DynamicForm();

		TextItem recipients = new TextItem("recipients", "E-mail Recipients");
		TextItem sender = new TextItem("sender", "E-mail Sender");
		TextItem SMTPHost = new TextItem("SMTPHost", "SMTP Host");

		formAdvancedLog
				.setFields(new FormItem[] { recipients, sender, SMTPHost });
		MainPageUtils.formatForm(formAdvancedLog);
		layoutAdvanced.addMember(formAdvancedLog);

		// add line
		com.smartgwt.client.widgets.Label line2 = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutAdvanced.addMember(line2);

		// network
		com.smartgwt.client.widgets.Label labelAdvancedNetwork = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Network</font></b>");
		labelAdvancedNetwork.setSize("200px", "20px");
		layoutAdvanced.addMember(labelAdvancedNetwork);

		DynamicForm formAdvancedNetwork = new DynamicForm();

		SpinnerItem packetSize = new SpinnerItem("packetSize", "Packet Size");
		packetSize.setValue("32768");

		SpinnerItem connTimeout = new SpinnerItem("connTimeout",
				"Connection Timeout (seconds)");
		connTimeout.setValue("3");

		formAdvancedNetwork
				.setFields(new FormItem[] { packetSize, connTimeout });
		MainPageUtils.formatForm(formAdvancedNetwork);
		layoutAdvanced.addMember(formAdvancedNetwork);

		// add line
		com.smartgwt.client.widgets.Label line3 = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutAdvanced.addMember(line3);

		// HTTP query server
		HLayout layoutAdvancedQueryLabel = new HLayout();
		com.smartgwt.client.widgets.Label labelAdvancedQuery = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>HTTP Query Server</font></b>");
		labelAdvancedQuery.setSize("200px", "20px");
		layoutAdvancedQueryLabel.addMember(labelAdvancedQuery);

		DynamicForm formAdvancedQueryEnable = new DynamicForm();

		NativeCheckboxItem httpEnable = new NativeCheckboxItem();
		httpEnable.setTitle("Enable");

		formAdvancedQueryEnable.setFields(new FormItem[] { httpEnable });
		layoutAdvancedQueryLabel.addMember(formAdvancedQueryEnable);
		layoutAdvanced.addMember(layoutAdvancedQueryLabel);

		DynamicForm formAdvancedQuery = new DynamicForm();
		final IntegerItem port = new IntegerItem("port", "Port");
		port.setDisabled(true);
		formAdvancedQuery.setFields(new FormItem[] { port });
		MainPageUtils.formatForm(formAdvancedQuery);
		layoutAdvanced.addMember(formAdvancedQuery);

		httpEnable.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				Boolean cstat = (Boolean) event.getValue();
				port.setDisabled(!cstat);
			}
		});

		// add line
		com.smartgwt.client.widgets.Label line4 = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutAdvanced.addMember(line4);

		// Low disk space warning
		HLayout layoutAdvancedDiskLabel = new HLayout();
		com.smartgwt.client.widgets.Label labelAdvancedDisk = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Low Disk Space Warning</font></b>");
		labelAdvancedDisk.setSize("200px", "20px");
		layoutAdvancedDiskLabel.addMember(labelAdvancedDisk);

		DynamicForm formAdvancedDiskEnable = new DynamicForm();

		NativeCheckboxItem diskEnable = new NativeCheckboxItem();
		diskEnable.setTitle("Enable");

		formAdvancedDiskEnable.setFields(new FormItem[] { diskEnable });
		layoutAdvancedDiskLabel.addMember(formAdvancedDiskEnable);
		layoutAdvanced.addMember(layoutAdvancedDiskLabel);

		DynamicForm formAdvancedDisk = new DynamicForm();
		final SpinnerItem percent = new SpinnerItem("percent",
				"Warn When Free Disk Space is Lower Than (%)");
		percent.setDisabled(true);
		percent.setValue("10");
		formAdvancedDisk.setFields(new FormItem[] { percent });
		MainPageUtils.formatForm(formAdvancedDisk);
		layoutAdvanced.addMember(formAdvancedDisk);

		diskEnable.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				Boolean cstat = (Boolean) event.getValue();
				percent.setDisabled(!cstat);
			}
		});

		// add line
		com.smartgwt.client.widgets.Label line5 = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutAdvanced.addMember(line5);

		// other
		com.smartgwt.client.widgets.Label labelAdvancedOther = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Other</font></b>");
		labelAdvancedOther.setSize("200px", "20px");
		layoutAdvanced.addMember(labelAdvancedOther);

		DynamicForm formAdvancedOther = new DynamicForm();

		SpinnerItem maxThreads = new SpinnerItem("maxThreads",
				"Max Threads for Active Jobs");
		maxThreads.setValue("4");

		CheckboxItem autoClean = new CheckboxItem("autoClean");
		autoClean
				.setTitle("Automatically Clean Up Old Files in Temporary Directory");
		autoClean.setLabelAsTitle(true);

		SpinnerItem maxGenerator = new SpinnerItem("maxThreads",
				"Max Metadata Generator Threads");
		maxGenerator.setValue("5");

		SpinnerItem serverRefresh = new SpinnerItem("serverRefresh",
				"Server Status Update Interval (seconds)");
		serverRefresh.setValue("10");

		SpinnerItem maxMissed = new SpinnerItem("maxMissed",
				"Max Consecutive Missed Updates");
		maxMissed.setValue("3");

		SpinnerItem directoryTimeout = new SpinnerItem("directoryTimeout",
				"Recursive Directory Listing Timeout (seconds)");
		directoryTimeout.setValue("120");

		TextItem extQName = new TextItem("extQName",
				"External Network Access Queue Name");

		BlurbItem missing = new BlurbItem("missing");
		missing.setDefaultValue("Generate warning instead error upon encountering missing...");

		NativeCheckboxItem missingMod = new NativeCheckboxItem("missingMod");
		missingMod.setTitle("Module Executable");

		NativeCheckboxItem missingFile = new NativeCheckboxItem("missingFile");
		missingFile.setTitle("File Parameters");

		formAdvancedOther.setFields(new FormItem[] { maxThreads, autoClean,
				maxGenerator, serverRefresh, maxMissed, directoryTimeout,
				extQName, missing, missingMod, missingFile });
		MainPageUtils.formatForm(formAdvancedOther);
		layoutAdvanced.addMember(formAdvancedOther);
		// */

		tabAdvanced.setPane(layoutAdvanced);
		return tabAdvanced;
	}
}
