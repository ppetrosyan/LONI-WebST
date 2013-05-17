package edu.ucla.loni.pipeline.client.Requesters.Configuration;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("RequestConfigurationXMLServlet")
public interface RequestConfigurationXMLService extends RemoteService {

	public String getXMLData();
}
