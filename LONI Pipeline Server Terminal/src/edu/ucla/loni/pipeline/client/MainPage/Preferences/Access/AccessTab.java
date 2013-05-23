package edu.ucla.loni.pipeline.client.MainPage.Preferences.Access;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

import edu.ucla.loni.pipeline.client.MainPage.Utilities.MainPageUtils;

public class AccessTab {
	
	public AccessTab() {
		
	}
	
	public Tab setTab() {
		Tab tabAccess = new Tab("Access");
		VLayout layoutAccess = new VLayout();

		DynamicForm dynamicForm = new DynamicForm();
		TextItem serverAdmin = new TextItem("serverAdmin", "Server Admins");
		dynamicForm.setFields(serverAdmin);
		//getClass();
		MainPageUtils.formatForm(dynamicForm);
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
		MainPageUtils.formatForm(dynamicForm2);
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

		MainPageUtils.formatForm(dynamicForm3);
		layoutAccess.addMember(dynamicForm3);
		tabAccess.setPane(layoutAccess);
		return tabAccess;
	}
}
