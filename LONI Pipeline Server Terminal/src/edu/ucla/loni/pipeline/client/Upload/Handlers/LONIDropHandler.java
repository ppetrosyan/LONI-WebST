package edu.ucla.loni.pipeline.client.Upload.Handlers;

import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;

import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.layout.VLayout;

public class LONIDropHandler implements DropHandler {

	private Uploader uploader;
	private Map<String, Image> cancelButtons;
	private VLayout layoutUploads;
	private Label dropFilesLabel;
	
	public LONIDropHandler(Uploader uploader, Map<String, Image> cancelButtons, VLayout layoutUploads, Label dropFilesLabel) {
		this.uploader = uploader;
		this.cancelButtons = cancelButtons;
		this.layoutUploads = layoutUploads;
		this.dropFilesLabel = dropFilesLabel;
	}
	
	@Override
	public void onDrop(DropEvent event) {
		dropFilesLabel.removeStyleName("dropFilesLabelHover");

		if (uploader.getStats().getUploadsInProgress() <= 0) {
			//progressBarPanel.clear();
			cancelButtons.clear();
		}

		uploader.addFilesToQueue(Uploader.getDroppedFiles(event
				.getNativeEvent()));
		event.preventDefault();
	}

}
