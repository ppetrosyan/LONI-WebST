package edu.ucla.loni.pipeline.client.Requesters.WebUrl;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.ucla.loni.pipeline.client.Utilities.WebUrlResponseBuilder;

@RemoteServiceRelativePath("RequestConfigurationXMLServlet")
public interface RequestWebUrlXMLService extends RemoteService {

	public WebUrlResponseBuilder getXML(String url);
}
