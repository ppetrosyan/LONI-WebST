package edu.ucla.loni.pipeline.client.Handlers;

import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.user.client.ui.Label;

public class LONIDragLeaveHandler implements DragLeaveHandler {

	private Label dropFilesLabel;
	
	public LONIDragLeaveHandler(Label dropFilesLabel) {
		this.dropFilesLabel = dropFilesLabel;
	}
	
	@Override
	public void onDragLeave(DragLeaveEvent event) {
		dropFilesLabel.removeStyleName("dropFilesLabelHover");
	}

}
