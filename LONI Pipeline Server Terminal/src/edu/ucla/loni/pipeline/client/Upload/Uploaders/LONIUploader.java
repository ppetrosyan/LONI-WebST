package edu.ucla.loni.pipeline.client.Upload.Uploaders;

import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.ucla.loni.pipeline.client.Requesters.Depreciated.LONIDataRequester;
import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIFileDialogCompleteHandler;
import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIFileDialogStartHandler;
import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIFileQueueErrorHandler;
import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIFileQueuedHandler;
import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIUploadCompleteHandler;
import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIUploadErrorHandler;
import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIUploadSuccessHandler;

public class LONIUploader extends Uploader {

	private Map<String, Image> cancelButtons;
	private VerticalPanel progressBarPanel;
	private LONIDataRequester dataRequester;
	
	public LONIUploader(Map<String, Image> cancelButtons, VerticalPanel progressBarPanel, LONIDataRequester dataRequester) {
		this.cancelButtons = cancelButtons;
		this.progressBarPanel = progressBarPanel;
		this.dataRequester = dataRequester;
		
		configure();
		addHandlers();
	}

	public boolean configure() {
		setButtonImageURL(GWT.getModuleBaseURL() + "resources/upload.png");
		setButtonWidth(75);
		setButtonHeight(27);
		setFileSizeLimit("50 MB");
		setButtonCursor(Uploader.Cursor.HAND);
		setButtonAction(Uploader.ButtonAction.SELECT_FILES);
		
		return true;
	}

	public boolean addHandlers() {

		setUploadSuccessHandler(new LONIUploadSuccessHandler(dataRequester));

		setFileQueuedHandler(new LONIFileQueuedHandler(this, cancelButtons, progressBarPanel));

		setUploadCompleteHandler(new LONIUploadCompleteHandler(this, cancelButtons));

		setFileDialogStartHandler(new LONIFileDialogStartHandler(this, cancelButtons, progressBarPanel));

		setFileDialogCompleteHandler(new LONIFileDialogCompleteHandler(this));

		setFileQueueErrorHandler(new LONIFileQueueErrorHandler());

		setUploadErrorHandler(new LONIUploadErrorHandler(cancelButtons));
		
		return true;
	}
}
