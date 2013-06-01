package edu.ucla.loni.pipeline.client.Upload.Handlers;

import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;
import org.moxieapps.gwt.uploader.client.events.FileQueuedEvent;
import org.moxieapps.gwt.uploader.client.events.FileQueuedHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class LONIFileQueuedHandler implements FileQueuedHandler {

	private Uploader uploader;
	private Map<String, Image> cancelButtons;
	private VLayout layoutUploads;
	
	public LONIFileQueuedHandler(Uploader uploader, Map<String, Image> cancelButtons, VLayout layoutUploads) {
		this.uploader = uploader;
		this.cancelButtons = cancelButtons;
		this.layoutUploads = layoutUploads;
	}
	
	@Override
	public boolean onFileQueued(final FileQueuedEvent fileQueuedEvent) {
		// Add Cancel Button Image
		final Image cancelButton = new Image(GWT
				.getModuleBaseURL()
				+ "resources/images/icons/cancel.png");
		cancelButton.setStyleName("cancelButton");
		
		cancelButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				uploader.cancelUpload(fileQueuedEvent.getFile()
						.getId(), false);
				cancelButton.removeFromParent();
			}
		});
		
		cancelButtons.put(fileQueuedEvent.getFile().getId(),
				cancelButton);

		// Add the Bar and Button to the interface
		HorizontalPanel progressBarAndButtonPanel = new HorizontalPanel();
		progressBarAndButtonPanel.add(cancelButton);
		layoutUploads.addMember(progressBarAndButtonPanel);

		return true;

	}

}
