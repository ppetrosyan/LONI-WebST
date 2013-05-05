package edu.ucla.loni.pipeline.client.Uploaders;

import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.ucla.loni.pipeline.client.Handlers.LONIFileDialogCompleteHandler;
import edu.ucla.loni.pipeline.client.Handlers.LONIFileDialogStartHandler;
import edu.ucla.loni.pipeline.client.Handlers.LONIFileQueueErrorHandler;
import edu.ucla.loni.pipeline.client.Handlers.LONIFileQueuedHandler;
import edu.ucla.loni.pipeline.client.Handlers.LONIUploadCompleteHandler;
import edu.ucla.loni.pipeline.client.Handlers.LONIUploadErrorHandler;
import edu.ucla.loni.pipeline.client.Handlers.LONIUploadSuccessHandler;

public class LONIUploader extends Uploader {

	private Map<String, Image> cancelButtons;
	private VerticalPanel progressBarPanel;
	
	public LONIUploader(Map<String, Image> cancelButtons, VerticalPanel progressBarPanel) {
		this.cancelButtons = cancelButtons;
		this.progressBarPanel = progressBarPanel;
		
		configure();
		addHandlers();
	}

	public boolean configure() {
		setButtonImageURL(
				GWT.getModuleBaseURL()
				+ "resources/images/buttons/upload_new_version_button.png");
		setButtonWidth(133);
		setButtonHeight(22);
		setFileSizeLimit("50 MB");
		setButtonCursor(Uploader.Cursor.HAND);
		setButtonAction(Uploader.ButtonAction.SELECT_FILES);
		
		return true;
	}

	public boolean addHandlers() {

		setUploadSuccessHandler(new LONIUploadSuccessHandler());

		setFileQueuedHandler(new LONIFileQueuedHandler(this, cancelButtons, progressBarPanel));

		setUploadCompleteHandler(new LONIUploadCompleteHandler(this, cancelButtons));

		setFileDialogStartHandler(new LONIFileDialogStartHandler(this, cancelButtons, progressBarPanel));

		setFileDialogCompleteHandler(new LONIFileDialogCompleteHandler(this));

		setFileQueueErrorHandler(new LONIFileQueueErrorHandler());

		setUploadErrorHandler(new LONIUploadErrorHandler(cancelButtons));
		
		return true;
	}
}
