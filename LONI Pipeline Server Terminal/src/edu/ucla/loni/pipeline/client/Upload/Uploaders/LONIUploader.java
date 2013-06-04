/*
 * This file is part of LONI Pipeline Web-based Server Terminal.
 * 
 * LONI Pipeline Web-based Server Terminal is free software: 
 * you can redistribute it and/or modify it under the terms of the 
 * GNU Lesser General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * LONI Pipeline Web-based Server Terminal is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the 
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public License
 * along with LONI Pipeline Web-based Server Terminal.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package edu.ucla.loni.pipeline.client.Upload.Uploaders;

import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;
import com.smartgwt.client.widgets.layout.VLayout;

import edu.ucla.loni.pipeline.client.Notifications.LONINotifications;
import edu.ucla.loni.pipeline.client.Requesters.RefreshAllTabs.LONIDataRequester;
import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIFileDialogCompleteHandler;
import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIFileDialogStartHandler;
import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIFileQueueErrorHandler;
import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIFileQueuedHandler;
import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIUploadCompleteHandler;
import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIUploadErrorHandler;
import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIUploadSuccessHandler;

/**
 * Constructs an Uploader Service for web interface
 * 
 * @author Jared
 */
public class LONIUploader extends Uploader {

	private final Map<String, Image> cancelButtons;
	private final VLayout layoutUploads;
	private final LONIDataRequester dataRequester;
	private final LONINotifications notifications;

	/**
	 * Constructor
	 * 
	 * @param cancelButtons
	 * @param layoutUploads
	 * @param dataRequester
	 */
	public LONIUploader(Map<String, Image> cancelButtons,
			VLayout layoutUploads, LONIDataRequester dataRequester,
			LONINotifications notifications) {
		this.cancelButtons = cancelButtons;
		this.layoutUploads = layoutUploads;
		this.dataRequester = dataRequester;
		this.notifications = notifications;

		configure();
		addHandlers();
	}

	/**
	 * Configures the Uploader Service
	 * 
	 * @return success
	 */
	private boolean configure() {
		setButtonImageURL(GWT.getModuleBaseURL() + "../images/Browse.png");
		setButtonWidth(154);
		setButtonHeight(46);
		setFileSizeLimit("50 MB");
		setButtonCursor(Uploader.Cursor.HAND);
		setButtonAction(Uploader.ButtonAction.SELECT_FILES);

		return true;
	}

	/**
	 * Adds Event Handlers to Uploader Service
	 * 
	 * @return success
	 */
	private boolean addHandlers() {

		setUploadSuccessHandler(new LONIUploadSuccessHandler(dataRequester,
				notifications));

		setFileQueuedHandler(new LONIFileQueuedHandler(this, cancelButtons,
				layoutUploads));

		setUploadCompleteHandler(new LONIUploadCompleteHandler(this,
				cancelButtons));

		setFileDialogStartHandler(new LONIFileDialogStartHandler(this,
				cancelButtons, layoutUploads));

		setFileDialogCompleteHandler(new LONIFileDialogCompleteHandler(this));

		setFileQueueErrorHandler(new LONIFileQueueErrorHandler());

		setUploadErrorHandler(new LONIUploadErrorHandler(cancelButtons));

		return true;
	}
}
