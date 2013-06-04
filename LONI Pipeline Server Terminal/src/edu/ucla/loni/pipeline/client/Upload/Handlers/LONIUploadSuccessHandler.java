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

package edu.ucla.loni.pipeline.client.Upload.Handlers;

import org.moxieapps.gwt.uploader.client.events.UploadSuccessEvent;
import org.moxieapps.gwt.uploader.client.events.UploadSuccessHandler;

import edu.ucla.loni.pipeline.client.Notifications.LONINotifications;
import edu.ucla.loni.pipeline.client.Requesters.RefreshAllTabs.LONIDataRequester;

/**
 * Upload Success Event Handler
 * 
 * @author Jared
 */
public class LONIUploadSuccessHandler implements UploadSuccessHandler {

	/**
	 * Constructor
	 * 
	 * @param dataRequester
	 */
	private final LONINotifications notifications;

	public LONIUploadSuccessHandler(LONIDataRequester dataRequester,
			LONINotifications notifications) {
		this.notifications = notifications;
	}

	/**
	 * Handles an Upload Success Event
	 * 
	 * @see org.moxieapps.gwt.uploader.client.events.UploadSuccessHandler#onUploadSuccess(org.moxieapps.gwt.uploader.client.events.UploadSuccessEvent)
	 */
	@Override
	public boolean onUploadSuccess(UploadSuccessEvent uploadSuccessEvent) {
		notifications.showMessage(uploadSuccessEvent.getServerData(), true);

		/*
		 * Window.setTitle("File Upload");
		 * Window.alert(uploadSuccessEvent.getServerData());
		 */

		// Refresh all tabs
		// dataRequester.refreshTabs();

		return true;
	}
}
