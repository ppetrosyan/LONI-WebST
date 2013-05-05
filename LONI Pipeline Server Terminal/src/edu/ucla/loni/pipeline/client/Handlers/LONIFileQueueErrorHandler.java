package edu.ucla.loni.pipeline.client.Handlers;

import org.moxieapps.gwt.uploader.client.events.FileQueueErrorEvent;
import org.moxieapps.gwt.uploader.client.events.FileQueueErrorHandler;

import com.google.gwt.user.client.Window;

public class LONIFileQueueErrorHandler implements FileQueueErrorHandler {

	@Override
	public boolean onFileQueueError(FileQueueErrorEvent fileQueueErrorEvent) {
		Window.alert("Upload of file "
				+ fileQueueErrorEvent.getFile().getName()
				+ " failed due to ["
				+ fileQueueErrorEvent.getErrorCode().toString()
				+ "]: " + fileQueueErrorEvent.getMessage());
		return true;
	}

}
