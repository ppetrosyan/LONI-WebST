package edu.ucla.loni.pipeline.client.Handlers;

import org.moxieapps.gwt.uploader.client.events.UploadSuccessEvent;
import org.moxieapps.gwt.uploader.client.events.UploadSuccessHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.ucla.loni.pipeline.client.Requesters.XMLDataServiceAsync;

public class LONIUploadSuccessHandler implements UploadSuccessHandler {

	private XMLDataServiceAsync xmlDataService;
	
	public LONIUploadSuccessHandler(XMLDataServiceAsync xmlDataService) {
		this.xmlDataService = xmlDataService;
	}
	
	@Override
	public boolean onUploadSuccess(UploadSuccessEvent uploadSuccessEvent) {
		Window.alert(uploadSuccessEvent.getServerData());
		
		Window.alert(GWT.getModuleBaseURL() + "XMLDataServlet");
		
		xmlDataService.getXMLData("ConfigurationData", new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Technical failure: "
                        + caught.getMessage() + " ["
                        + caught.getClass().getName() + "]");
            }
        });
		
		return true;
	}
}
