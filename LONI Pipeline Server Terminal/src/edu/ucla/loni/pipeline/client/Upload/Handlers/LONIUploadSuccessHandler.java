package edu.ucla.loni.pipeline.client.Upload.Handlers;

import org.moxieapps.gwt.uploader.client.events.UploadSuccessEvent;
import org.moxieapps.gwt.uploader.client.events.UploadSuccessHandler;

import com.google.gwt.user.client.Window;

import edu.ucla.loni.pipeline.client.Requesters.Depreciated.LONIDataRequester;

public class LONIUploadSuccessHandler implements UploadSuccessHandler {

	private LONIDataRequester dataRequester;
	
	public LONIUploadSuccessHandler(LONIDataRequester dataRequester) {
		this.dataRequester = dataRequester;
	}
	
	@Override
	public boolean onUploadSuccess(UploadSuccessEvent uploadSuccessEvent) {
		Window.alert(uploadSuccessEvent.getServerData());
		
		// Refresh all tabs
		dataRequester.refreshTabs();
		
		return true;
	}
}
