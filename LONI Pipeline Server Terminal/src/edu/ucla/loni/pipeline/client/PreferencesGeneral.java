package edu.ucla.loni.pipeline.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.LayoutPanel;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.NativeCheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.layout.VStack;


public class PreferencesGeneral extends ResizeComposite
{
	public PreferencesGeneral()
	{
		super();
		
		LayoutPanel p = new LayoutPanel();
		VStack layoutGeneral = new VStack();
		layoutGeneral.setSize("100%", "100%");
		setup(layoutGeneral);
		p.add(layoutGeneral);
		
		initWidget(p);
		setSize("100%", "100%");
	}
	
	// TODO eliminate duplicate code
	private void formatForm(DynamicForm form)
	{
		form.setTitleWidth(200);
		for(FormItem i : form.getFields())
			i.setTitleAlign(Alignment.LEFT);
	}
	
	private void setup(VStack layoutGeneral)
	{

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
	}
}
