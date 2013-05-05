package edu.ucla.loni.pipeline.client.UploadFeatures;

import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;

import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.ucla.loni.pipeline.client.Handlers.LONIDragLeaveHandler;
import edu.ucla.loni.pipeline.client.Handlers.LONIDragOverHandler;
import edu.ucla.loni.pipeline.client.Handlers.LONIDropHandler;

public class LONIDragandDropLabel extends Label {

	private String labelString;
	private Uploader uploader;
	private Map<String, Image> cancelButtons;
	private VerticalPanel progressBarPanel;
	
	public LONIDragandDropLabel(String labelString, Uploader uploader, Map<String, Image> cancelButtons, VerticalPanel progressBarPanel) {
		super(labelString);
		
		this.labelString = labelString;
		this.uploader = uploader;
		this.cancelButtons = cancelButtons;
		this.progressBarPanel = progressBarPanel;
		
		configure();
		addHandlers();
	}
	
	public boolean configure() {
		setStyleName("dropFilesLabel");
		
		return true;
	}
	
	public boolean addHandlers() {
		addDragOverHandler(new LONIDragOverHandler(this));
		
		addDragLeaveHandler(new LONIDragLeaveHandler(this));
		
		addDropHandler(new LONIDropHandler(uploader, cancelButtons, progressBarPanel, this));
		
		return true;
	}
}
