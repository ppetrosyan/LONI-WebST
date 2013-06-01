package edu.ucla.loni.pipeline.client.Upload.Features;

import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.layout.VLayout;

import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIDragLeaveHandler;
import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIDragOverHandler;
import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIDropHandler;

public class LONIDragandDropLabel extends Label {

	private String labelString;
	private Uploader uploader;
	private Map<String, Image> cancelButtons;
	private VLayout layoutUploads;
	
	public LONIDragandDropLabel(String labelString, Uploader uploader, Map<String, Image> cancelButtons, VLayout layoutUploads) {
		super(labelString);
		
		this.labelString = labelString;
		this.uploader = uploader;
		this.cancelButtons = cancelButtons;
		this.layoutUploads = layoutUploads;
		
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
		
		addDropHandler(new LONIDropHandler(uploader, cancelButtons, layoutUploads, this));
		
		return true;
	}
}
