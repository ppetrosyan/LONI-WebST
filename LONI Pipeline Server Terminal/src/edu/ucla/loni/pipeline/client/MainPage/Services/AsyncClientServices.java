package edu.ucla.loni.pipeline.client.MainPage.Services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import edu.ucla.loni.pipeline.client.Requesters.Configuration.RequestConfigurationXMLService;
import edu.ucla.loni.pipeline.client.Requesters.Configuration.RequestConfigurationXMLServiceAsync;
import edu.ucla.loni.pipeline.client.Requesters.ResourceUsage.RequestResourceXMLService;
import edu.ucla.loni.pipeline.client.Requesters.ResourceUsage.RequestResourceXMLServiceAsync;
import edu.ucla.loni.pipeline.client.Savers.Configuration.SaveConfigurationXMLService;
import edu.ucla.loni.pipeline.client.Savers.Configuration.SaveConfigurationXMLServiceAsync;


public class AsyncClientServices {

	// Client-side Services
	private String reqResourceXML, reqConfigurationXML, saveConfigurationXML;

	public RequestResourceXMLServiceAsync reqResourceXMLService;
	public RequestConfigurationXMLServiceAsync reqConfigurationXMLService;
	public SaveConfigurationXMLServiceAsync saveConfigurationXMLService;
	
	public AsyncClientServices() {
		reqResourceXML = "RequestResourceXMLServlet";
		reqConfigurationXML = "RequestConfigurationXMLServlet";
		saveConfigurationXML = "SaveConfigurationXMLServlet";
		
		reqResourceXMLService = GWT
				.create(RequestResourceXMLService.class);
		reqConfigurationXMLService = GWT
				.create(RequestConfigurationXMLService.class);
		saveConfigurationXMLService = GWT
				.create(SaveConfigurationXMLService.class);
		
		defEntryPoint();
	}

	public void defEntryPoint() {
		((ServiceDefTarget) reqResourceXMLService)
		.setServiceEntryPoint(reqResourceXML);
		((ServiceDefTarget) reqConfigurationXMLService)
		.setServiceEntryPoint(reqConfigurationXML);
		((ServiceDefTarget) saveConfigurationXMLService)
		.setServiceEntryPoint(saveConfigurationXML);
	}
}
