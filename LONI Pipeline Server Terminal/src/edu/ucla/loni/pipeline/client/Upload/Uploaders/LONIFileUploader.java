package edu.ucla.loni.pipeline.client.Upload.Uploaders;

import java.util.Map;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.ucla.loni.pipeline.client.Requesters.LONIDataRequester;

public class LONIFileUploader extends LONIUploader {
	
	public LONIFileUploader(Map<String, Image> cancelButtons, VerticalPanel progressBarPanel, LONIDataRequester dataRequester) {
		super(cancelButtons, progressBarPanel, dataRequester);
		
		setUploadURL("/FileUploadServlet");
	}

}
