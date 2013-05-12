package edu.ucla.loni.pipeline.client.Callbacks;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;

public class LONIRequestDataCallback implements RequestCallback {

	@Override
	public void onResponseReceived(Request request, Response response) {
		Window.alert(response.getText());
	}

	@Override
	public void onError(Request request, Throwable exception) {
		
	}

}
