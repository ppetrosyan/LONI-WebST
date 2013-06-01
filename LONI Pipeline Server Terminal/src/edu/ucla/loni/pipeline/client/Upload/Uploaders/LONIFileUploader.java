package edu.ucla.loni.pipeline.client.Upload.Uploaders;

import java.util.Map;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.layout.VLayout;

import edu.ucla.loni.pipeline.client.Requesters.RefreshAllTabs.LONIDataRequester;

public class LONIFileUploader extends LONIUploader {
	
	public LONIFileUploader(Map<String, Image> cancelButtons, VLayout layoutUploads, LONIDataRequester dataRequester) {
		super(cancelButtons, layoutUploads, dataRequester);
		
		setUploadURL("/FileUploadServlet");
	}

}
