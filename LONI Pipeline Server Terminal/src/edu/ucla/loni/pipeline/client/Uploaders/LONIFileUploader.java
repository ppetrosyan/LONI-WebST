package edu.ucla.loni.pipeline.client.Uploaders;

import java.util.Map;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LONIFileUploader extends LONIUploader {

	public LONIFileUploader(Map<String, Image> cancelButtons, VerticalPanel progressBarPanel) {
		super(cancelButtons, progressBarPanel);
		
		setUploadURL("/FileUploadServlet");
	}

}
