package edu.ucla.loni.pipeline.client.Requesters;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("XMLDataServlet")
public interface XMLDataService extends RemoteService {

	public String getXMLData(String xmlDataType);
}
