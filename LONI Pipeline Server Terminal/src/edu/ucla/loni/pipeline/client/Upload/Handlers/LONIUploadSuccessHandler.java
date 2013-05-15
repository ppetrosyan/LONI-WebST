package edu.ucla.loni.pipeline.client.Upload.Handlers;

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
		
		return true;
	}
}
