package edu.ucla.loni.pipeline.client.MainPage.Upload;

import java.util.LinkedHashMap;
import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
import edu.ucla.loni.pipeline.client.Requesters.WebUrl.RequestWebUrlXMLServiceAsync;
import edu.ucla.loni.pipeline.client.Upload.Features.LONIDragandDropLabel;
import edu.ucla.loni.pipeline.client.Upload.Uploaders.LONIFileUploader;

//import org.apache.commons.validator.routines.UrlValidator;

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
		
		HorizontalPanel webUrlPanel = new HorizontalPanel();
		
		final DynamicForm fileUploadForm = new DynamicForm();
		fileUploadForm.setFields(new FormItem[] { new TextItem("ti_weburl", "URL")});
		webUrlPanel.add(fileUploadForm);
		
		Button webUrlBtn = new Button("Retrieve");
		webUrlBtn.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		//UrlValidator urlValidator = new UrlValidator();
	    		//if (urlValidator.isValid(fileUploadForm.getValueAsString("ti_weburl")))
	    			dataRequester.getWebUrlXml(fileUploadForm.getValueAsString("ti_weburl"));
	    		//else
	    			//Window.alert("Invalid URL, try again");
	        }
	    });
		webUrlPanel.add(webUrlBtn);
		
		layoutUploads.addMember(webUrlPanel);
		
		tabUpload.setPane(layoutUploads);
		return tabUpload;
	}
}
