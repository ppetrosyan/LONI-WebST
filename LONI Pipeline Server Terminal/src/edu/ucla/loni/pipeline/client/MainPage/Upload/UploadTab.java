package edu.ucla.loni.pipeline.client.MainPage.Upload;

import java.util.LinkedHashMap;
import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

import edu.ucla.loni.pipeline.client.Requesters.RefreshAllTabs.LONIDataRequester;
import edu.ucla.loni.pipeline.client.Upload.Features.LONIDragandDropLabel;
import edu.ucla.loni.pipeline.client.Upload.Uploaders.LONIFileUploader;

public class UploadTab {
	
	private LONIDataRequester dataRequester;
	
	public UploadTab(LONIDataRequester dataRequester) {
		this.dataRequester = dataRequester;
	}
	
	public Tab setTab() {
		Tab tabUpload = new Tab("Upload");

		final VerticalPanel fileuploadPanel = new VerticalPanel();
		final Map<String, Image> cancelButtons = new LinkedHashMap<String, Image>();

		final LONIFileUploader LONIfileUploader = new LONIFileUploader(
				cancelButtons, fileuploadPanel, dataRequester);

		if (Uploader.isAjaxUploadWithProgressEventsSupported()) {
			final LONIDragandDropLabel fileuploadLabel = new LONIDragandDropLabel(
					"Drop Files", LONIfileUploader, cancelButtons,
					fileuploadPanel);
			fileuploadPanel.add(fileuploadLabel);
		}
		fileuploadPanel.add(LONIfileUploader);

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.add(fileuploadPanel);

		VLayout uploadLayout = new VLayout();
		uploadLayout.addMember(horizontalPanel);

		DynamicForm fileUploadForm = new DynamicForm();
		fileUploadForm.setFields(new FormItem[] { new TextItem("weburl",
				"XML file Web URL") });
		fileuploadPanel.add(fileUploadForm);

		Button webUrl = new Button("Retrieve from Web");
		webUrl.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.alert("Uploads");
			}
		});
		fileuploadPanel.add(webUrl);

		tabUpload.setPane(uploadLayout);
		return tabUpload;
	}
}
