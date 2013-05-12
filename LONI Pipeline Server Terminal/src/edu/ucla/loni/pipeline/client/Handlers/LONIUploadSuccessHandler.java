package edu.ucla.loni.pipeline.client.Handlers;

import org.moxieapps.gwt.uploader.client.events.UploadSuccessEvent;
import org.moxieapps.gwt.uploader.client.events.UploadSuccessHandler;

import com.google.gwt.user.client.Window;

public class LONIUploadSuccessHandler implements UploadSuccessHandler {

	@Override
	public boolean onUploadSuccess(UploadSuccessEvent uploadSuccessEvent) {
		Window.alert(uploadSuccessEvent.getServerData());
		return true;
	}
}
