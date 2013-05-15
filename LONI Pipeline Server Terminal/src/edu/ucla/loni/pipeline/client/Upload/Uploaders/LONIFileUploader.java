package edu.ucla.loni.pipeline.client.Upload.Uploaders;

import java.util.Map;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.ucla.loni.pipeline.client.Requesters.XMLDataServiceAsync;

public class LONIFileUploader extends LONIUploader {

	public LONIFileUploader(Map<String, Image> cancelButtons, VerticalPanel progressBarPanel, XMLDataServiceAsync xmlDataService) {
		super(cancelButtons, progressBarPanel, xmlDataService);
		
		setUploadURL("/FileUploadServlet");
	}

}
