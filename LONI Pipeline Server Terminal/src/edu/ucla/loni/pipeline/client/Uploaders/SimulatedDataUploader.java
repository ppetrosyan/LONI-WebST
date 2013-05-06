package edu.ucla.loni.pipeline.client.Uploaders;

import java.util.Map;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SimulatedDataUploader extends LONIUploader {

	public SimulatedDataUploader(Map<String, Image> cancelButtons,
			VerticalPanel progressBarPanel) {
		super(cancelButtons, progressBarPanel);
		
		setUploadURL("/SimulatedDataServlet");
	}
}
