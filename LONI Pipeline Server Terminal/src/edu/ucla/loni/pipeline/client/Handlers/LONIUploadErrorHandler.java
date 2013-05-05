package edu.ucla.loni.pipeline.client.Handlers;

import java.util.Map;

import org.moxieapps.gwt.uploader.client.events.UploadErrorEvent;
import org.moxieapps.gwt.uploader.client.events.UploadErrorHandler;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;

public class LONIUploadErrorHandler implements UploadErrorHandler {

	private Map<String, Image> cancelButtons;
	
	public LONIUploadErrorHandler(Map<String, Image> cancelButtons) {
		this.cancelButtons = cancelButtons;
	}
	
	@Override
	public boolean onUploadError(UploadErrorEvent uploadErrorEvent) {
	
		cancelButtons.get(uploadErrorEvent.getFile().getId())
		.removeFromParent();
		Window.alert("Upload of file "
				+ uploadErrorEvent.getFile().getName()
				+ " failed due to ["
				+ uploadErrorEvent.getErrorCode().toString()
				+ "]: " + uploadErrorEvent.getMessage());
		return true;
	}
}
