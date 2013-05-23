package edu.ucla.loni.pipeline.client.MainPage.Utilities;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;

public class MainPageUtils {
	public static void formatForm(DynamicForm form) {
		form.setTitleWidth(200);
		for (FormItem i : form.getFields())
			i.setTitleAlign(Alignment.LEFT);
	}
}
