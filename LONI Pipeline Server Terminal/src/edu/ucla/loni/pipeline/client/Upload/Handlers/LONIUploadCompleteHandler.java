package edu.ucla.loni.pipeline.client.Upload.Handlers;

import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;
import org.moxieapps.gwt.uploader.client.events.UploadCompleteEvent;
import org.moxieapps.gwt.uploader.client.events.UploadCompleteHandler;

import com.google.gwt.user.client.ui.Image;

public class LONIUploadCompleteHandler implements UploadCompleteHandler {

	private Uploader uploader;
	private Map<String, Image> cancelButtons;
	
	public LONIUploadCompleteHandler(Uploader uploader, Map<String, Image> cancelButtons) {
		this.uploader = uploader;
		this.cancelButtons = cancelButtons;
	}
	
	@Override
	public boolean onUploadComplete(UploadCompleteEvent uploadCompleteEvent) {
		cancelButtons
		.get(uploadCompleteEvent.getFile().getId())
		.removeFromParent();
		uploader.startUpload();
		return true;
	}

}
