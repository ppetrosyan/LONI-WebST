package edu.ucla.loni.pipeline.server.Requesters.WebUrl;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.ucla.loni.pipeline.client.Requesters.WebUrl.RequestWebUrlXMLService;
import edu.ucla.loni.pipeline.server.Utilities.DatastoreUtils;
import edu.ucla.loni.pipeline.server.Utilities.ResponseBuilder;

public class RequestWebUrlXMLServlet extends RemoteServiceServlet implements RequestWebUrlXMLService {

	private static final long serialVersionUID = 5448261063802349760L;
	//private Key xmlConfigurationKey;

	public RequestWebUrlXMLServlet() {
		//xmlConfigurationKey = KeyFactory.createKey("XMLType", "ConfigurationData");
	}

	@Override
	public String getXML(String url) {
		String xmlData = "test data returned";
		
		//ResponseBuilder respBuilder = new ResponseBuilder();
		//xmlData = DatastoreUtils.readXMLFileFromBlobStore(xmlConfigurationKey, respBuilder);
		
		// Debug
		//System.out.println(respBuilder.getRespMessage());
		
		return xmlData;
	}
}