package edu.ucla.loni.pipeline.client.MainPage.Preferences.Access;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

import edu.ucla.loni.pipeline.client.MainPage.Utilities.MainPageUtils;

public class AccessTab {
	
	private DynamicForm dynamicForm;
	private DynamicForm dynamicForm2;
	private DynamicForm dynamicForm3;
	private DynamicForm dynamicForm4;
	
	private SelectItem selectItem;
	private TextItem textItem_1;
	private TextItem textItem_2;
	private CheckboxItem checkboxItem_1;
	private CheckboxItem checkboxItem_2;
	private SpinnerItem spinnerItem_1;
	private SpinnerItem spinnerItem_2;
	
	public AccessTab() {
		
	}
	
	public Tab setTab() {
		Tab tabAccess = new Tab("Access");
		VLayout layoutAccess = new VLayout();
		
		com.smartgwt.client.widgets.Label label_0 = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Server Admins</font></b>");
		label_0.setSize("210px", "20px");
		layoutAccess.addMember(label_0);

		TextItem serverAdmin = new TextItem("serverAdmin", "Input (separate multiple entries by comma)");
		serverAdmin.setWidth(600);
		
		dynamicForm = new DynamicForm();
		dynamicForm.setFields(serverAdmin);
		MainPageUtils.formatForm(dynamicForm);
		layoutAccess.addMember(dynamicForm);

		// add line
		com.smartgwt.client.widgets.Label line = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutAccess.addMember(line);

		com.smartgwt.client.widgets.Label label_1 = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Directory Access Control</font></b>");
		label_1.setSize("210px", "20px");
		layoutAccess.addMember(label_1);

		
		textItem_1 = new TextItem();
		textItem_1.setDisabled(true);
		textItem_1.setAlign(Alignment.LEFT);
		textItem_1.setTitle("Controlled users");
		textItem_1.setName("textbox_1");
		textItem_2 = new TextItem();
		textItem_2.setDisabled(true);
		textItem_2.setAlign(Alignment.LEFT);
		textItem_2.setTitle("Controlled directories");
		textItem_2.setName("textbox_2");

		selectItem = new SelectItem("newSelectItem_1",
				"Access control mode");
		selectItem.setWidth(600);
		selectItem.setDefaultToFirstOption(true);
		selectItem.setAlign(Alignment.LEFT);
		selectItem.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				setControlMode();
			}
		});

		selectItem.setMultipleAppearance(MultipleAppearance.GRID);
		selectItem.setMultiple(false);
		
		dynamicForm2 = new DynamicForm();
		dynamicForm2.setHeight("120px");
		dynamicForm2.setFields(new FormItem[] { selectItem, textItem_1,
				textItem_2 });
		String[] sitesArray = {
				"0 - Executables: no restrictions. Remote File Browser: no restrictions",
				"1 - Executables: restricted below. Remote File Browser: no restrictions",
				"2 - Executables: enabled. Remote File Browser: no restrictions",
				"3 - Executables: restricted below. Remote File Browser: restricted below",
				"4 - Executables: enabled below. Remote File Browser: enabled below",
				"5 - Executables: restricted below. Remote File Browser: same as Shell permissions",
				"6 - Executables: enabled below. Remote File Browser: same as Shell permissions",
				"7 - Executables: same as Shell permissions. Remote File Browser: same as Shell" };

		selectItem.setValueMap(sitesArray);
		MainPageUtils.formatForm(dynamicForm2);
		layoutAccess.addMember(dynamicForm2);

		// add line
		com.smartgwt.client.widgets.Label line2 = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutAccess.addMember(line2);
		
		com.smartgwt.client.widgets.Label label_2 = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>User-Based Job Management</font></b>");
		label_2.setSize("250px", "20px");
		layoutAccess.addMember(label_2);
		
		checkboxItem_1 = new CheckboxItem(
				"cb_enableubjm",
				"Enable (NOTE: Enabling user management may require some time to adjust user usage accuracy)");
		checkboxItem_1.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				Boolean cstat = (Boolean) event.getValue();
				toggleUserManage(cstat);
			}
		});
		checkboxItem_1.setLabelAsTitle(true);
		checkboxItem_1.setValue(false);
		
		spinnerItem_1 = new SpinnerItem("si_usrpcfreeslots",
				"One user cannot take more than");
		spinnerItem_1.setValue(50);
		//spinnerItem_1.setHint("% of free slots at submission time");
		//spinnerItem_1.setShowHint(true);
		spinnerItem_1.setMin(0);
		spinnerItem_1.setMax(100);
		spinnerItem_1.setDisabled(true);
		
		StaticTextItem staticTextItem = new StaticTextItem("percentfreeslots", "");
		staticTextItem.setDefaultValue("% of free slots at submission time");

		dynamicForm3 = new DynamicForm();
		dynamicForm3.setFields(new FormItem[] { checkboxItem_1, spinnerItem_1, staticTextItem });
		MainPageUtils.formatForm(dynamicForm3);
		layoutAccess.addMember(dynamicForm3);
		
		checkboxItem_2 = new CheckboxItem("cb_enablewfm", "Enable");
		checkboxItem_2.setLabelAsTitle(true);
		checkboxItem_2.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				Boolean cstat = (Boolean) event.getValue();
				toggleWorkflowManage(cstat);
			}
		});
		
		spinnerItem_2 = new SpinnerItem("si_maxwrkflpu",
				"Maximum active (running & paused) workflows per user");
		spinnerItem_2.setValue(20);
		spinnerItem_2.setMin(0);
		spinnerItem_2.setMax(9999);
		spinnerItem_2.setDisabled(true);
		
		// add line
		com.smartgwt.client.widgets.Label line3 = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutAccess.addMember(line3);
		
		com.smartgwt.client.widgets.Label label_3 = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Workflow Management</font></b>");
		label_3.setSize("210px", "20px");
		layoutAccess.addMember(label_3);
		
		dynamicForm4 = new DynamicForm();
		dynamicForm4.setFields(new FormItem[] { checkboxItem_2, spinnerItem_2 });
		MainPageUtils.formatForm(dynamicForm4);		
		
		layoutAccess.addMember(dynamicForm4);
		tabAccess.setPane(layoutAccess);
		return tabAccess;
	}
	
	public void toggleUserManage(Boolean cstat) {
		if (!cstat)
			spinnerItem_1.setValue(50);
		spinnerItem_1.setDisabled(!cstat);
	}
	
	public void toggleWorkflowManage(Boolean cstat) {
		if (!cstat)
			spinnerItem_2.setValue(20);
		spinnerItem_2.setDisabled(!cstat);
	}
	
	public void parseAccessXML(Document doc) {
		
		// server admins section
		Node serverAdmin = (Node) doc.getElementsByTagName("ServerAdmins").item(0);
		if(serverAdmin != null) {
			String serverAdminVal = serverAdmin.getFirstChild().getNodeValue();
			dynamicForm.getItem("serverAdmin").setValue(serverAdminVal);
		}
		
		// directory access control section
		Node accessControlMode = (Node) doc.getElementsByTagName("AccessControlMode").item(0);
		if(accessControlMode != null) {
			String accessControlModeVal = accessControlMode.getFirstChild().getNodeValue();
			dynamicForm2.getItem("newSelectItem_1").setValue(accessControlModeVal);
			setControlMode();
		}
		
		Node accessUsers = (Node) doc.getElementsByTagName("AccessUsers").item(0);
		if(accessUsers != null) {
			String accessUserVal = accessUsers.getFirstChild().getNodeValue();
			dynamicForm2.getItem("textbox_1").setValue(accessUserVal);
		}
		
		Node accessDirectories = (Node) doc.getElementsByTagName("AccessDirectories").item(0);
		if(accessDirectories != null) {
			String accessDirectoriesVal = accessDirectories.getFirstChild().getNodeValue();
			dynamicForm2.getItem("textbox_2").setValue(accessDirectoriesVal);
		}
		
		// user-based job management section
		Node jobManageEnable = (Node) doc.getElementsByTagName("JobManageEnable").item(0);
		if(jobManageEnable != null) {
			Boolean jobManageEnableVal = Boolean.valueOf(jobManageEnable.getFirstChild().getNodeValue());
			dynamicForm3.getItem("cb_enableubjm").setValue(jobManageEnableVal);
			toggleUserManage(jobManageEnableVal);
		}
		
		Node freeSlots = (Node) doc.getElementsByTagName("FreeSlots").item(0);
		if(freeSlots != null) {
			int freeSlotsVal = Integer.parseInt(freeSlots.getFirstChild().getNodeValue());
			dynamicForm3.getItem("si_usrpcfreeslots").setValue(freeSlotsVal);
		}
		
		// workflow management section
		Node workflowManageEnable = (Node) doc.getElementsByTagName("WorkflowManageEnable").item(0);
		if(workflowManageEnable != null) {
			Boolean workflowVal = Boolean.valueOf(workflowManageEnable.getFirstChild().getNodeValue());
			dynamicForm4.getItem("cb_enablewfm").setValue(workflowVal);
			toggleWorkflowManage(workflowVal);
		}
		
		Node maxActiveWorkflows = (Node) doc.getElementsByTagName("MaxActiveWorkflows").item(0);
		if(maxActiveWorkflows != null) {
			int maxWorkflowsVal = Integer.parseInt(maxActiveWorkflows.getFirstChild().getNodeValue());
			dynamicForm4.getItem("si_maxwrkflpu").setValue(maxWorkflowsVal);
		}
	}
	
	private void setControlMode() {
		
		// case0
		if ((selectItem.getValueAsString())
				.equals("0 - Executables: no restrictions. Remote File Browser: no restrictions")) {
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
}
