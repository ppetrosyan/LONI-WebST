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

import com.google.gwt.user.client.ui.Image;
import com.smartgwt.client.widgets.layout.VLayout;

import edu.ucla.loni.pipeline.client.Notifications.LONINotifications;
import edu.ucla.loni.pipeline.client.Requesters.RefreshAllTabs.LONIDataRequester;

/**
 * Constructs an Uploader for the web interface that is tied to the
 * FileUploadServlet
 */
public class LONIFileUploader extends LONIUploader {

	/**
	 * Constructor
	 * 
	 * @param cancelButtons
	 * @param layoutUploads
	 * @param dataRequester
	 */
	public LONIFileUploader(Map<String, Image> cancelButtons,
			VLayout layoutUploads, LONIDataRequester dataRequester,
			LONINotifications notifications) {
		super(cancelButtons, layoutUploads, dataRequester, notifications);

		setUploadURL("/FileUploadServlet");
	}

}
