package edu.ucla.loni.pipeline.server.Requesters.Configuration;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.ucla.loni.pipeline.client.Requesters.Configuration.RequestConfigurationXMLService;
import edu.ucla.loni.pipeline.server.Utilities.DatastoreUtils;
import edu.ucla.loni.pipeline.server.Utilities.ResponseBuilder;

public class RequestConfigurationXMLServlet extends RemoteServiceServlet implements RequestConfigurationXMLService {

	private static final long serialVersionUID = 5448261063802349760L;
	private Key xmlConfigurationKey;

	public RequestConfigurationXMLServlet() {
		xmlConfigurationKey = KeyFactory.createKey("XMLType", "ConfigurationData");
	}

	@Override
	public String getXMLData() {
		String xmlData = "";
		
		ResponseBuilder respBuilder = new ResponseBuilder();
		xmlData = DatastoreUtils.readXMLFileFromBlobStore(xmlConfigurationKey, respBuilder);
		
		// Debug
		System.out.println(respBuilder.getRespMessage());
		
		return xmlData;
	}
}