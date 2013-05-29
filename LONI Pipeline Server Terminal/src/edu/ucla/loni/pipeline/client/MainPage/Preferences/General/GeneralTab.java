package edu.ucla.loni.pipeline.client.MainPage.Preferences.General;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

import edu.ucla.loni.pipeline.client.MainPage.Utilities.MainPageUtils;

public class GeneralTab {
	
	private DynamicForm formGeneralBasic;
	private DynamicForm formGeneralPersistence;
	private DynamicForm formGeneralServerLibrary;
	
	private TextItem libraryPathText;
	private TextItem mappedGuestUser;
	private CheckboxItem escalationCheckbox;
	private CheckboxItem enableGuestCheckbox;
	
	public GeneralTab() {
		
	}
	
	public Tab setTab() {
		Tab tabGeneral = new Tab("General");

		VLayout layoutGeneral = new VLayout();

		// basic
		com.smartgwt.client.widgets.Label labelGeneralBasic = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Basic</font></b>");
		labelGeneralBasic.setSize("70px", "20px");
		layoutGeneral.addMember(labelGeneralBasic);

		formGeneralBasic = new DynamicForm();

		TextItem hostText = new TextItem("host", "Host");

		IntegerItem basicPort = new IntegerItem("port", "Port");
		basicPort.setDefaultValue("8001");

		TextItem tempdirText = new TextItem("tempdir", "Temporary Directory");

		CheckboxItem secureCheckbox = new CheckboxItem("secureCheckbox");
		secureCheckbox.setTitle("Secure");
		secureCheckbox.setLabelAsTitle(true);

		TextItem scrdirText = new TextItem("scrdir", "Scratch Directory");

		TextItem logfileText = new TextItem("logfile", "Log File");

		escalationCheckbox = new CheckboxItem("escalationCheckbox");
		escalationCheckbox.setTitle("Use privilege escalation - Pipeline server will run commands as the user (sudo as user)");
		escalationCheckbox.setLabelAsTitle(true);
		
		enableGuestCheckbox = new CheckboxItem("enableGuestCheckbox");
		enableGuestCheckbox.setTitle("Enable guests");
		enableGuestCheckbox.setLabelAsTitle(true);
		
		mappedGuestUser = new TextItem("mappedGuestUser", "Mapped guest user");
		mappedGuestUser.setVisible(false);

		escalationCheckbox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if((Boolean) event.getValue() && enableGuestCheckbox.getValueAsBoolean()) {
					mappedGuestUser.show();
				}
				else {
					mappedGuestUser.clearValue();
					mappedGuestUser.hide();
				}
			}
		});
		
		enableGuestCheckbox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if((Boolean) event.getValue() && escalationCheckbox.getValueAsBoolean()) {
					mappedGuestUser.show();
				}
				else {
					mappedGuestUser.clearValue();
					mappedGuestUser.hide();
				}
			}
		});
		
		formGeneralBasic.setFields(new FormItem[] { hostText, basicPort,
				tempdirText, secureCheckbox, scrdirText, logfileText,
				escalationCheckbox, enableGuestCheckbox, mappedGuestUser });
		MainPageUtils.formatForm(formGeneralBasic);
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

		formGeneralPersistence = new DynamicForm();

		TextItem urlText = new TextItem("url", "URL");

		TextItem usernameText = new TextItem("username", "Username");

		PasswordItem passwordText = new PasswordItem("password", "Password");

		SpinnerItem spinnerItem = new SpinnerItem("si_sessionttl",
				"Session Time-to-live");
		spinnerItem.setDefaultValue(30);
		spinnerItem.setMin(0);
		spinnerItem.setMax(1000);

		StaticTextItem staticTextItem = new StaticTextItem("sessiondays", "");
		staticTextItem.setDefaultValue("(days from the end of this session)");

		TextItem historydocText = new TextItem("historydoc",
				"History Directory");

		TextItem crawlerpurlText = new TextItem("crawlerpurl",
				"Crawler Persistence URL");

		formGeneralPersistence.setFields(new FormItem[] { urlText,
				usernameText, passwordText, spinnerItem, staticTextItem,
				historydocText, crawlerpurlText });
		MainPageUtils.formatForm(formGeneralPersistence);
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

		formGeneralServerLibrary = new DynamicForm();

		TextItem locationText = new TextItem("location", "Location");

		CheckboxItem librarycheckbox = new CheckboxItem("librarycheckbox");
		librarycheckbox.setTitle("Monitor library update file checkbox");
		librarycheckbox.setLabelAsTitle(true);

		libraryPathText = new TextItem("librarypath",
				"Monitor library update file");
		libraryPathText.setVisible(false);

		librarycheckbox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				Boolean cstat = (Boolean) event.getValue();
				toggleLibraryPathText(cstat);
			}
		});

		TextItem pipeutilitiespathText = new TextItem("pipeutilitiespath",
				"Pipeline Utilities Path");

		formGeneralServerLibrary.setFields(new FormItem[] { locationText,
				librarycheckbox, libraryPathText, pipeutilitiespathText });
		MainPageUtils.formatForm(formGeneralServerLibrary);
		layoutGeneral.addMember(formGeneralServerLibrary);

		tabGeneral.setPane(layoutGeneral);
		return tabGeneral;
	}
	
	public void toggleLibraryPathText(Boolean cstat) {
		if (cstat) {
			libraryPathText.show();
		}
		else {
			libraryPathText.hide();
			libraryPathText.clearValue();
		}
	}
	
	public void parseGeneralXML(Document doc) {
		resetFields();
		
		// basic section
		Node host = (Node) doc.getElementsByTagName("ServerHostname").item(0);
		if(host != null) {
			String hostVal = host.getFirstChild().getNodeValue();
			formGeneralBasic.getItem("host").setValue(hostVal);
		}
		
		Node port = (Node) doc.getElementsByTagName("ServerPort").item(0);
		if(port != null) {
			int portVal = Integer.parseInt(port.getFirstChild().getNodeValue());
			formGeneralBasic.getItem("port").setValue(portVal);
		}
		
		Node tempdir = (Node) doc.getElementsByTagName("TempDirectory").item(0);
		if(tempdir != null) {
			String tempdirVal = tempdir.getFirstChild().getNodeValue();
			formGeneralBasic.getItem("tempdir").setValue(tempdirVal);
		}
		
		Node secureCheckbox = (Node) doc.getElementsByTagName("Secure").item(0);
		if(secureCheckbox != null) {
			Boolean secureCheckboxVal = Boolean.valueOf(secureCheckbox.getFirstChild().getNodeValue());
			formGeneralBasic.getItem("secureCheckbox").setValue(secureCheckboxVal);
		}
		
		Node scrdir = (Node) doc.getElementsByTagName("ScratchDirectory").item(0);
		if(scrdir != null) {
			String scrdirVal = scrdir.getFirstChild().getNodeValue();
			formGeneralBasic.getItem("scrdir").setValue(scrdirVal);
		}
		
		Node logfile = (Node) doc.getElementsByTagName("LogFile").item(0);
		if(logfile != null) {
			String logfileVal = logfile.getFirstChild().getNodeValue();
			formGeneralBasic.getItem("logfile").setValue(logfileVal);
		}
		
		Node escalationCheckboxNode = (Node) doc.getElementsByTagName("PrivilegeEscalation").item(0);
		if(escalationCheckboxNode != null) {
			Boolean escalationVal = Boolean.valueOf(escalationCheckboxNode.getFirstChild().getNodeValue());
			formGeneralBasic.getItem("escalationCheckbox").setValue(escalationVal);
		}
		
		Node enableGuestCheckboxNode = (Node) doc.getElementsByTagName("EnableGuests").item(0);
		if(enableGuestCheckboxNode != null) {
			Boolean enableGuestVal = Boolean.valueOf(enableGuestCheckboxNode.getFirstChild().getNodeValue());
			formGeneralBasic.getItem("enableGuestCheckbox").setValue(enableGuestVal);
		}
		
		Node mappedGuestUserNode = (Node) doc.getElementsByTagName("MappedGuestUser").item(0);
		if(mappedGuestUserNode != null) {
			String mappedGuestUserVal = mappedGuestUserNode.getFirstChild().getNodeValue();
			formGeneralBasic.getItem("mappedGuestUser").setValue(mappedGuestUserVal);
			if(escalationCheckbox.getValueAsBoolean() && enableGuestCheckbox.getValueAsBoolean()) {
				mappedGuestUser.show();
			}
		}
		
		// persistence section
		Node url = (Node) doc.getElementsByTagName("PersistenceURL").item(0);
		if(url != null) {
			String urlVal = url.getFirstChild().getNodeValue();
			formGeneralPersistence.getItem("url").setValue(urlVal);
		}
		
		Node username = (Node) doc.getElementsByTagName("PersistenceUsername").item(0);
		if(username != null) {
			String usernameVal = username.getFirstChild().getNodeValue();
			formGeneralPersistence.getItem("username").setValue(usernameVal);
		}
		
		Node password = (Node) doc.getElementsByTagName("PersistencePassword").item(0);
		if(password != null) {
			String passwordVal = password.getFirstChild().getNodeValue();
			formGeneralPersistence.getItem("password").setValue(passwordVal);
		}
		
		Node si_sessionttl = (Node) doc.getElementsByTagName("SessionTTL").item(0);
		if(si_sessionttl != null) {
			int ttlVal = Integer.parseInt(si_sessionttl.getFirstChild().getNodeValue());
			formGeneralPersistence.getItem("si_sessionttl").setValue(ttlVal);
		}
		
		Node historydoc = (Node) doc.getElementsByTagName("HistoryDirectory").item(0);
		if(historydoc != null) {
			String historyVal = historydoc.getFirstChild().getNodeValue();
			formGeneralPersistence.getItem("historydoc").setValue(historyVal);
		}
		
		Node crawlerpurl = (Node) doc.getElementsByTagName("CrawlerURL").item(0);
		if(crawlerpurl != null) {
			String crawlerVal = crawlerpurl.getFirstChild().getNodeValue();
			formGeneralPersistence.getItem("crawlerpurl").setValue(crawlerVal);
		}
		
		// server library section
		Node location = (Node) doc.getElementsByTagName("ServerLibraryLocation").item(0);
		if(location != null) {
			String locationVal = location.getFirstChild().getNodeValue();
			formGeneralServerLibrary.getItem("location").setValue(locationVal);
		}
		
		Node librarycheckbox = (Node) doc.getElementsByTagName("MonitorLibraryCheckbox").item(0);
		if(librarycheckbox != null) {
			Boolean librarycheckboxVal = Boolean.valueOf(librarycheckbox.getFirstChild().getNodeValue());
			formGeneralServerLibrary.getItem("librarycheckbox").setValue(librarycheckboxVal);
			toggleLibraryPathText(librarycheckboxVal);
		}
		
		Node librarypath = (Node) doc.getElementsByTagName("MonitorLibraryPath").item(0);
		if(librarypath != null) {
			String librarypathVal = librarypath.getFirstChild().getNodeValue();
			formGeneralServerLibrary.getItem("librarypath").setValue(librarypathVal);
		}
		
		Node pipeutilitiespath = (Node) doc.getElementsByTagName("PipelineUtilitiesPath").item(0);
		if(pipeutilitiespath != null) {
			String pipeutilitiespathVal = pipeutilitiespath.getFirstChild().getNodeValue();
			formGeneralServerLibrary.getItem("pipeutilitiespath").setValue(pipeutilitiespathVal);
		}
		
	}
	
	private void resetFields() {
		formGeneralBasic.clearValues();
		formGeneralPersistence.clearValues();
		formGeneralServerLibrary.clearValues();
	}
}
