package edu.ucla.loni.pipeline.client.Requesters.WebUrl;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("RequestConfigurationXMLServlet")
public interface RequestWebUrlXMLService extends RemoteService {

	public String getXML(String url);
}
