package edu.ucla.loni.pipeline.client.Handlers;

import org.moxieapps.gwt.uploader.client.Uploader;

import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.user.client.ui.Label;

public class LONIDragOverHandler implements DragOverHandler {

	private Uploader uploader;
	private Label dropFilesLabel;
	
	public LONIDragOverHandler(Label dropFilesLabel) {
		this.dropFilesLabel = dropFilesLabel;
	}
	
	@Override
	public void onDragOver(DragOverEvent event) {
		if (!uploader.getButtonDisabled()) {
			dropFilesLabel.addStyleName("dropFilesLabelHover");
		}
	}
	
}
