package edu.ucla.loni.pipeline.client.MainPage.Upload;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.validator.UrlValidator;
import org.moxieapps.gwt.uploader.client.Uploader;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
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
		
		VLayout layoutUploads = new VLayout();
		layoutUploads.setSize("100%", "100%");
		layoutUploads.setMembersMargin(10);
		
		final VerticalPanel fileuploadPanel = new VerticalPanel();
		final Map<String, Image> cancelButtons = new LinkedHashMap<String, Image>();
		
		final LONIFileUploader LONIfileUploader = new LONIFileUploader(cancelButtons, fileuploadPanel, dataRequester);
		
		if (Uploader.isAjaxUploadWithProgressEventsSupported()) {
			final LONIDragandDropLabel fileuploadLabel = new LONIDragandDropLabel("Drop Files", LONIfileUploader, cancelButtons, fileuploadPanel);
			fileuploadPanel.add(fileuploadLabel);
			layoutUploads.addMember(fileuploadPanel);
		}
		
		LONIfileUploader.setButtonText("Browse");
		layoutUploads.addMember(LONIfileUploader);
				
		com.smartgwt.client.widgets.Label labelWebUrl = new com.smartgwt.client.widgets.Label("Web Retrieval");
		layoutUploads.addMember(labelWebUrl);

		final DynamicForm fileUploadForm = new DynamicForm();
		
		final TextItem textFieldUsername = new TextItem("ti_username", "Username");
		textFieldUsername.setRequired(true);
		
		final PasswordItem passwordField = new PasswordItem("pi_password", "Password");
		passwordField.setRequired(true);
		
		final TextItem textFieldWebUrl = new TextItem("ti_weburl", "URL");
		textFieldWebUrl.setRequired(true);
		
		fileUploadForm.setFields(new FormItem[] {textFieldUsername, passwordField, textFieldWebUrl});
		layoutUploads.addMember(fileUploadForm);
		
		Button webUrlBtn = new Button("Retrieve");
		webUrlBtn.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		if ((fileUploadForm.getValueAsString("ti_weburl")).length() == 0) {
					Window.alert("Missing Web URL");
					return;
	    		}	    		
	    		if ((fileUploadForm.getValueAsString("ti_username")).length() == 0) {
					Window.alert("Missing username");
					return;
	    		}
	    		if ((fileUploadForm.getValueAsString("pi_password")).length() == 0) {
					Window.alert("Missing Password");
					return;
	    		}
	    		
	    		dataRequester.getWebUrlXml(fileUploadForm.getValueAsString("ti_weburl"),
	    								   fileUploadForm.getValueAsString("ti_username"),
	    								   fileUploadForm.getValueAsString("pi_password"));
	        }
	    });
		layoutUploads.addMember(webUrlBtn);
		
		tabUpload.setPane(layoutUploads);
		return tabUpload;
	}
}
