package edu.ucla.loni.pipeline.client.Handlers;

import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;
import org.moxieapps.gwt.uploader.client.events.FileDialogStartEvent;
import org.moxieapps.gwt.uploader.client.events.FileDialogStartHandler;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LONIFileDialogStartHandler implements FileDialogStartHandler {

	private Uploader uploader;
	private Map<String, Image> cancelButtons;
	private VerticalPanel progressBarPanel;
	
	public LONIFileDialogStartHandler(Uploader uploader, Map<String, Image> cancelButtons, VerticalPanel progressBarPanel) {
		this.uploader = uploader;
		this.cancelButtons = cancelButtons;
		this.progressBarPanel = progressBarPanel;
	} 
	
	@Override
	public boolean onFileDialogStartEvent(
			FileDialogStartEvent fileDialogStartEvent) {
		if (uploader.getStats().getUploadsInProgress() <= 0) {
			// Clear the uploads that have completed, if none
			// are in process
			progressBarPanel.clear();
			cancelButtons.clear();
		}
		return true;
	}

}
