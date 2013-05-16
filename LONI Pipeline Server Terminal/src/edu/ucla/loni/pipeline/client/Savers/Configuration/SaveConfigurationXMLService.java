package edu.ucla.loni.pipeline.client.Savers.Configuration;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("SaveConfigurationXMLServlet")
public interface SaveConfigurationXMLService extends RemoteService {
	public boolean setXMLData(String xmlData);
}
