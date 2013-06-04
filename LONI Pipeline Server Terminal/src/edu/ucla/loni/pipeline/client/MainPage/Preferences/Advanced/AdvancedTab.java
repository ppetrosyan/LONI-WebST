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

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
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
	
	private NativeCheckboxItem failoverEnable;
	private SpinnerItem slaveFreq;
	private SpinnerItem slaveRetries;
	private TextItem aliasInterface;
	private IntegerItem aliasSubinterface;
	private TextItem postFailover;
	
	private TextItem logRecipients;
	private TextItem logSender;
	private TextItem logSMTPHost;
	
	private SpinnerItem packetSize;
	private SpinnerItem connTimeout;
	
	private NativeCheckboxItem httpEnable;
	private IntegerItem port;
	
	private NativeCheckboxItem diskEnable;
	private SpinnerItem percent;
	
	private SpinnerItem maxThreads;
	private CheckboxItem autoClean;
	private SpinnerItem maxGenerator;
	private SpinnerItem serverRefresh;
	private SpinnerItem maxMissed;
	private SpinnerItem directoryTimeout;
	private TextItem extQName;
	private NativeCheckboxItem missingMod;
	private NativeCheckboxItem missingFile;
	
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
		failoverEnable = new NativeCheckboxItem();
		failoverEnable.setTitle("Enable");

		formAdvancedFailoverEnable.setFields(new FormItem[] { failoverEnable });
		layoutAdvancedLabel.addMember(formAdvancedFailoverEnable);

		layoutAdvanced.addMember(layoutAdvancedLabel);

		DynamicForm formAdvancedFailover = new DynamicForm();

		slaveFreq = new SpinnerItem("slaveFreq",
				"Slave: Master Check Interval (seconds)");
		slaveFreq.setValue("5");
		slaveFreq.setMin(1);
		slaveFreq.setDisabled(true);

		slaveRetries = new SpinnerItem("slaveRetries",
				"Slave: Number of Retries");
		slaveRetries.setValue("3");
		slaveRetries.setMin(0);
		slaveRetries.setDisabled(true);

		aliasInterface = new TextItem("aliasInterface",
				"Alias Interface");
		aliasInterface.setDisabled(true);

		aliasSubinterface = new IntegerItem(
				"aliasSubinterface", "Alias Subinterface Number");
		aliasSubinterface.setValue("0");
		aliasSubinterface.setDisabled(true);

		postFailover = new TextItem("postFailover",
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

		logRecipients = new TextItem("recipients", "E-mail Recipients");
		logSender = new TextItem("sender", "E-mail Sender");
		logSMTPHost = new TextItem("SMTPHost", "SMTP Host");

		formAdvancedLog
				.setFields(new FormItem[] { logRecipients, logSender, logSMTPHost });
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

		packetSize = new SpinnerItem("packetSize", "Packet Size");
		packetSize.setValue("32768");
		packetSize.setMin(64);

		connTimeout = new SpinnerItem("connTimeout",
				"Connection Timeout (seconds)");
		connTimeout.setValue("3");
		connTimeout.setMin(1);

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

		httpEnable = new NativeCheckboxItem();
		httpEnable.setTitle("Enable");

		formAdvancedQueryEnable.setFields(new FormItem[] { httpEnable });
		layoutAdvancedQueryLabel.addMember(formAdvancedQueryEnable);
		layoutAdvanced.addMember(layoutAdvancedQueryLabel);

		DynamicForm formAdvancedQuery = new DynamicForm();
		port = new IntegerItem("port", "Port");
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

		diskEnable = new NativeCheckboxItem();
		diskEnable.setTitle("Enable");

		formAdvancedDiskEnable.setFields(new FormItem[] { diskEnable });
		layoutAdvancedDiskLabel.addMember(formAdvancedDiskEnable);
		layoutAdvanced.addMember(layoutAdvancedDiskLabel);

		DynamicForm formAdvancedDisk = new DynamicForm();
		percent = new SpinnerItem("percent",
				"Warn When Free Disk Space is Lower Than (%)");
		percent.setDisabled(true);
		percent.setValue("10");
		percent.setMin(0);
		percent.setMax(100);
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

		maxThreads = new SpinnerItem("maxThreads",
				"Max Threads for Active Jobs");
		maxThreads.setValue("4");
		maxThreads.setMin(1);

		autoClean = new CheckboxItem("autoClean");
		autoClean
				.setTitle("Automatically Clean Up Old Files in Temporary Directory");
		autoClean.setLabelAsTitle(true);

		maxGenerator = new SpinnerItem("maxThreads",
				"Max Metadata Generator Threads");
		maxGenerator.setValue("5");
		maxGenerator.setMin(1);

		serverRefresh = new SpinnerItem("serverRefresh",
				"Server Status Update Interval (seconds)");
		serverRefresh.setValue("10");
		serverRefresh.setMin(1);

		maxMissed = new SpinnerItem("maxMissed",
				"Max Consecutive Missed Updates");
		maxMissed.setValue("3");
		maxMissed.setMin(1);

		directoryTimeout = new SpinnerItem("directoryTimeout",
				"Recursive Directory Listing Timeout (seconds)");
		directoryTimeout.setValue("120");
		directoryTimeout.setMin(60);

		extQName = new TextItem("extQName",
				"External Network Access Queue Name");

		BlurbItem missing = new BlurbItem("missing");
		missing.setDefaultValue("Generate warning instead error upon encountering missing...");

		missingMod = new NativeCheckboxItem("missingMod");
		missingMod.setTitle("Module Executable");

		missingFile = new NativeCheckboxItem("missingFile");
		missingFile.setTitle("File Parameters");

		formAdvancedOther.setFields(new FormItem[] { maxThreads, autoClean,
				maxGenerator, serverRefresh, maxMissed, directoryTimeout,
				extQName, missing, missingMod, missingFile });
		MainPageUtils.formatForm(formAdvancedOther);
		layoutAdvanced.addMember(formAdvancedOther);

		tabAdvanced.setPane(layoutAdvanced);
		return tabAdvanced;
	}
	
	public void parseAdvancedXML(Document doc) {
		// enable failover section
		Node enableFailover = doc.getElementsByTagName("FailoverEnabled").item(0);
		if (enableFailover != null) {
			Boolean enableFailoverVal = Boolean.valueOf(enableFailover.getFirstChild().getNodeValue());
			failoverEnable.setValue(enableFailoverVal);
		}
		
		Node failoverCheckInterval = doc.getElementsByTagName("FailoverCheckInterval").item(0);
		if (failoverCheckInterval != null) {
			String failoverCheckIntervalVal = failoverCheckInterval.getFirstChild().getNodeValue();
			slaveFreq.setValue(failoverCheckIntervalVal);
		}
		
		Node failoverRetries = doc.getElementsByTagName("FailoverRetries").item(0);
		if (failoverRetries != null) {
			String failoverRetriesVal = failoverRetries.getFirstChild().getNodeValue();
			slaveRetries.setValue(failoverRetriesVal);
		}
		
		Node failoverAliasInterface = doc.getElementsByTagName("FailoverAliasInterface").item(0);
		if (failoverAliasInterface != null) {
			String failoverAliasInterfaceVal = failoverAliasInterface.getFirstChild().getNodeValue();
			aliasInterface.setValue(failoverAliasInterfaceVal);
		}
		
		Node failoverAliasSubInterfaceNum = doc.getElementsByTagName("FailoverAliasSubInterfaceNum").item(0);
		if (failoverAliasSubInterfaceNum != null) {
			String failoverAliasSubInterfaceNumVal = failoverAliasSubInterfaceNum.getFirstChild().getNodeValue();
			aliasSubinterface.setValue(failoverAliasSubInterfaceNumVal);
		}
		
		Node failoverPostScript = doc.getElementsByTagName("FailoverPostScript").item(0);
		if (failoverPostScript != null) {
			String failoverPostScriptVal = failoverPostScript.getFirstChild().getNodeValue();
			postFailover.setValue(failoverPostScriptVal);
		}

		Node emailerRecipients = doc.getElementsByTagName("EmailerRecipients").item(0);
		if (emailerRecipients != null) {
			String emailerRecipientsVal = emailerRecipients.getFirstChild().getNodeValue();
			logRecipients.setValue(emailerRecipientsVal);
		}

		Node emailerSender = doc.getElementsByTagName("EmailerSender").item(0);
		if (emailerSender != null) {
			String emailerSenderVal = emailerSender.getFirstChild().getNodeValue();
			logSender.setValue(emailerSenderVal);
		}

		Node emailerSMTPHost = doc.getElementsByTagName("EmailerSMTPHost").item(0);
		if (emailerSMTPHost != null) {
			String emailerSMTPHostVal = emailerSMTPHost.getFirstChild().getNodeValue();
			logSMTPHost.setValue(emailerSMTPHostVal);
		}

		Node networkPacketSize = doc.getElementsByTagName("NetworkPacketSize").item(0);
		if (networkPacketSize != null) {
			String networkPacketSizeVal = networkPacketSize.getFirstChild().getNodeValue();
			packetSize.setValue(networkPacketSizeVal);
		}

		Node connectTimeoutSec = doc.getElementsByTagName("ConnectTimeoutSec").item(0);
		if (networkPacketSize != null) {
			String connectTimeoutSecVal = connectTimeoutSec.getFirstChild().getNodeValue();
			connTimeout.setValue(connectTimeoutSecVal);
		}
		
		NodeList HTTPServerPortList = doc.getElementsByTagName("HTTPServerPort");
		if(HTTPServerPortList.getLength() != 0)
		{
			Node HTTPServerPort = HTTPServerPortList.item(0);
			if (HTTPServerPort != null) {
				String HTTPServerPortVal = HTTPServerPort.getFirstChild().getNodeValue();
				port.setValue(HTTPServerPortVal);
			}
			httpEnable.setValue(true);
		}
		else
		{
			httpEnable.setValue(false);
		}
		
		NodeList warnLowDiskSpacePercentList = doc.getElementsByTagName("WarnLowDiskSpacePercent");
		if(warnLowDiskSpacePercentList.getLength() != 0)
		{
			Node warnLowDiskSpacePercent = warnLowDiskSpacePercentList.item(0);
			if (warnLowDiskSpacePercent != null) {
				String warnLowDiskSpacePercentVal = warnLowDiskSpacePercent.getFirstChild().getNodeValue();
				percent.setValue(warnLowDiskSpacePercentVal);
			}
			diskEnable.setValue(true);
		}
		else
		{
			diskEnable.setValue(false);
		}

		Node maximumThreadPoolSize = doc.getElementsByTagName("MaximumThreadPoolSize").item(0);
		if (maximumThreadPoolSize != null) {
			String maximumThreadPoolSizeVal = maximumThreadPoolSize.getFirstChild().getNodeValue();
			maxThreads.setValue(maximumThreadPoolSizeVal);
		}

		Node clearOldTempFilesEnabled = doc.getElementsByTagName("ClearOldTempFilesEnabled").item(0);
		if (clearOldTempFilesEnabled != null) {
			Boolean clearOldTempFilesEnabledVal = Boolean.valueOf(clearOldTempFilesEnabled.getFirstChild().getNodeValue());
			autoClean.setValue(clearOldTempFilesEnabledVal);
		}

		Node maxConcurrentMetadataGenerator = doc.getElementsByTagName("MaxConcurrentMetadataGenerator").item(0);
		if (maxConcurrentMetadataGenerator != null) {
			String maxConcurrentMetadataGeneratorVal = maxConcurrentMetadataGenerator.getFirstChild().getNodeValue();
			maxGenerator.setValue(maxConcurrentMetadataGeneratorVal);
		}

		Node serverStatIntervalSec = doc.getElementsByTagName("ServerStatIntervalSec").item(0);
		if (serverStatIntervalSec != null) {
			String serverStatIntervalSecVal = serverStatIntervalSec.getFirstChild().getNodeValue();
			serverRefresh.setValue(serverStatIntervalSecVal);
		}

		Node connectionTimeoutAfterNumInterval = doc.getElementsByTagName("ConnectionTimeoutAfterNumInterval").item(0);
		if (connectionTimeoutAfterNumInterval != null) {
			String connectionTimeoutAfterNumIntervalVal = connectionTimeoutAfterNumInterval.getFirstChild().getNodeValue();
			maxMissed.setValue(connectionTimeoutAfterNumIntervalVal);
		}

		Node dirListRecTimeoutSec = doc.getElementsByTagName("DirListRecTimeoutSec").item(0);
		if (dirListRecTimeoutSec != null) {
			String dirListRecTimeoutSecVal = dirListRecTimeoutSec.getFirstChild().getNodeValue();
			directoryTimeout.setValue(dirListRecTimeoutSecVal);
		}

		Node networkAccessQueue = doc.getElementsByTagName("NetworkAccessQueue").item(0);
		if (networkAccessQueue != null) {
			String networkAccessQueueVal = networkAccessQueue.getFirstChild().getNodeValue();
			extQName.setValue(networkAccessQueueVal);
		}

		Node warningForExecutableNotFound = doc.getElementsByTagName("WarningForExecutableNotFound").item(0);
		if (warningForExecutableNotFound != null) {
			Boolean warningForExecutableNotFoundVal = Boolean.valueOf(warningForExecutableNotFound.getFirstChild().getNodeValue());
			missingMod.setValue(warningForExecutableNotFoundVal);
		}

		Node warningForParameterFileNotFound = doc.getElementsByTagName("WarningForParameterFileNotFound").item(0);
		if (warningForParameterFileNotFound != null) {
			Boolean warningForParameterFileNotFoundVal = Boolean.valueOf(warningForParameterFileNotFound.getFirstChild().getNodeValue());
			missingFile.setValue(warningForParameterFileNotFoundVal);
		}
	}
}
