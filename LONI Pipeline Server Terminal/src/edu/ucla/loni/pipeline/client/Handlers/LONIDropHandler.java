package edu.ucla.loni.pipeline.client.Handlers;

import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;

import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LONIDropHandler implements DropHandler {

	private Uploader uploader;
	private Map<String, Image> cancelButtons;
	private VerticalPanel progressBarPanel;
	private Label dropFilesLabel;
	
	public LONIDropHandler(Uploader uploader, Map<String, Image> cancelButtons, VerticalPanel progressBarPanel, Label dropFilesLabel) {
		this.uploader = uploader;
		this.cancelButtons = cancelButtons;
		this.progressBarPanel = progressBarPanel;
		this.dropFilesLabel = dropFilesLabel;
	}
	
	@Override
	public void onDrop(DropEvent event) {
		dropFilesLabel.removeStyleName("dropFilesLabelHover");

		if (uploader.getStats().getUploadsInProgress() <= 0) {
			progressBarPanel.clear();
			cancelButtons.clear();
		}

		uploader.addFilesToQueue(Uploader.getDroppedFiles(event
				.getNativeEvent()));
		event.preventDefault();
	}

}
