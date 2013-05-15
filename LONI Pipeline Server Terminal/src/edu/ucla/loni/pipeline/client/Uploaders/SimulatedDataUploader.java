package edu.ucla.loni.pipeline.client.Uploaders;

import java.util.Map;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.ucla.loni.pipeline.client.Requesters.XMLDataServiceAsync;

public class SimulatedDataUploader extends LONIUploader {

	public SimulatedDataUploader(Map<String, Image> cancelButtons,
			VerticalPanel progressBarPanel, XMLDataServiceAsync xmlDataService) {
		super(cancelButtons, progressBarPanel, xmlDataService);
		
		setUploadURL("/SimulatedDataServlet");
	}
}
