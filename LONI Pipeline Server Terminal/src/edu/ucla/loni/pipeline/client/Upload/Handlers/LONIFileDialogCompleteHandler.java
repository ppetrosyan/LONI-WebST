package edu.ucla.loni.pipeline.client.Upload.Handlers;

import org.moxieapps.gwt.uploader.client.Uploader;
import org.moxieapps.gwt.uploader.client.events.FileDialogCompleteEvent;
import org.moxieapps.gwt.uploader.client.events.FileDialogCompleteHandler;

public class LONIFileDialogCompleteHandler implements FileDialogCompleteHandler {

	private Uploader uploader;

	public LONIFileDialogCompleteHandler(Uploader uploader) {
		this.uploader = uploader;
	}

	@Override
	public boolean onFileDialogComplete(
			FileDialogCompleteEvent fileDialogCompleteEvent) {

		if (fileDialogCompleteEvent.getTotalFilesInQueue() > 0) {
			if (uploader.getStats().getUploadsInProgress() <= 0) {
				uploader.startUpload();
			}
		}
		return true;
	}
}