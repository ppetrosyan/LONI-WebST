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

import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;
import org.moxieapps.gwt.uploader.client.events.FileQueuedEvent;
import org.moxieapps.gwt.uploader.client.events.FileQueuedHandler;

import com.google.gwt.user.client.ui.Image;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * File Queued Event Handler
 * 
 * @author Jared
 */
public class LONIFileQueuedHandler implements FileQueuedHandler {

	/**
	 * Constructor
	 * 
	 * @param uploader
	 * @param cancelButtons
	 * @param layoutUploads
	 */
	public LONIFileQueuedHandler(Uploader uploader,
			Map<String, Image> cancelButtons, VLayout layoutUploads) {
	}

	/**
	 * Handles a File Queued Event
	 * 
	 * @see org.moxieapps.gwt.uploader.client.events.FileQueuedHandler#onFileQueued(org.moxieapps.gwt.uploader.client.events.FileQueuedEvent)
	 */
	@Override
	public boolean onFileQueued(final FileQueuedEvent fileQueuedEvent) {
		// Add Cancel Button Image
		/*
		 * final Image cancelButton = new Image(GWT .getModuleBaseURL() +
		 * "resources/images/icons/cancel.png");
		 * cancelButton.setStyleName("cancelButton");
		 * 
		 * cancelButton.addClickHandler(new ClickHandler() {
		 * 
		 * public void onClick(ClickEvent event) {
		 * uploader.cancelUpload(fileQueuedEvent.getFile() .getId(), false);
		 * cancelButton.removeFromParent(); } });
		 * 
		 * cancelButtons.put(fileQueuedEvent.getFile().getId(), cancelButton);
		 * 
		 * // Add the Bar and Button to the interface HorizontalPanel
		 * progressBarAndButtonPanel = new HorizontalPanel();
		 * progressBarAndButtonPanel.add(cancelButton);
		 * layoutUploads.addMember(progressBarAndButtonPanel);
		 */

		return true;

	}

}
