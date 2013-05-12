package edu.ucla.loni.pipeline.client.Handlers;

import org.moxieapps.gwt.uploader.client.events.UploadSuccessEvent;
import org.moxieapps.gwt.uploader.client.events.UploadSuccessHandler;

import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.Window;

import edu.ucla.loni.pipeline.client.Callbacks.LONIRequestDataCallback;
import edu.ucla.loni.pipeline.client.Requesters.LONIRequestData;

public class LONIUploadSuccessHandler implements UploadSuccessHandler {

	@Override
	public boolean onUploadSuccess(UploadSuccessEvent uploadSuccessEvent) {
		Window.alert(uploadSuccessEvent.getServerData());
		
		/**Temporary */
		String url = "/FileUploadServlet";
		String parameters = "?xmlfile=configuration";
		LONIRequestData LONIRequest = new LONIRequestData(url, parameters, new LONIRequestDataCallback());
		try {
			LONIRequest.issueRequest();
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
}
