package edu.ucla.loni.pipeline.server.Requesters.ResourceUsage;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.ucla.loni.pipeline.client.Requesters.ResourceUsage.RequestResourceXMLService;
import edu.ucla.loni.pipeline.server.Utilities.DatastoreUtils;
import edu.ucla.loni.pipeline.server.Utilities.ResponseBuilder;

public class RequestResourceXMLServlet extends RemoteServiceServlet implements RequestResourceXMLService {

	private static final long serialVersionUID = 5448261063802349760L;
	private Key xmlResourceKey;

	public RequestResourceXMLServlet() {
		xmlResourceKey = KeyFactory.createKey("XMLType", "ResourceData");
	}

	@Override
	public String getXMLData() {
		String xmlData = "";
		
		ResponseBuilder respBuilder = new ResponseBuilder();
		xmlData = DatastoreUtils.readXMLFileFromBlobStore(xmlResourceKey, respBuilder);
		
		// Debug
		System.out.println(respBuilder.getRespMessage());
		
		return xmlData;
	}
}