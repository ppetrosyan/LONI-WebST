package edu.ucla.loni.pipeline.client.Savers.Configuration;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("SaveConfigurationXMLServlet")
public interface SaveResourceCSVService extends RemoteService {
	public boolean setCSVData(String csvData);
}
