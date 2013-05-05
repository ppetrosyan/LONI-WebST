package edu.ucla.loni.pipeline.client.Uploaders;

import java.util.Map;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ConfigurationUploader extends LONIUploader {

	public ConfigurationUploader(Map<String, Image> cancelButtons, VerticalPanel progressBarPanel) {
		super(cancelButtons, progressBarPanel);
		
		setUploadURL("/ConfigurationServlet");
	}

}
