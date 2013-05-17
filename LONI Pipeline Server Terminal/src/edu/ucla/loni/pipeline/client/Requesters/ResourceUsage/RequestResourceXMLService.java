package edu.ucla.loni.pipeline.client.Requesters.ResourceUsage;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("RequestResourceXMLServlet")
public interface RequestResourceXMLService extends RemoteService {

	public String getXMLData();
}
