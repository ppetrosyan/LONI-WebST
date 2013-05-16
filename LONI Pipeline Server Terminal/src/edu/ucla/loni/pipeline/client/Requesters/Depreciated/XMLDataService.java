package edu.ucla.loni.pipeline.client.Requesters.Depreciated;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("XMLDataServlet")
public interface XMLDataService extends RemoteService {

	public String getXMLData(String xmlDataType);
}
