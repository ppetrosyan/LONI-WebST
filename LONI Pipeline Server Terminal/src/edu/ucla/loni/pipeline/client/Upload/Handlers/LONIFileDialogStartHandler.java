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
import org.moxieapps.gwt.uploader.client.events.FileDialogStartEvent;
import org.moxieapps.gwt.uploader.client.events.FileDialogStartHandler;

import com.google.gwt.user.client.ui.Image;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * File Dialog Start Event Handler
 */
public class LONIFileDialogStartHandler implements FileDialogStartHandler {

	private final Uploader uploader;
	private final Map<String, Image> cancelButtons;

	/**
	 * Constructor
	 * 
	 * @param uploader
	 * @param cancelButtons
	 * @param layoutUploads
	 */
	public LONIFileDialogStartHandler(Uploader uploader,
			Map<String, Image> cancelButtons, VLayout layoutUploads) {
		this.uploader = uploader;
		this.cancelButtons = cancelButtons;
	}

	/**
	 * Handles a File Dialog Start Event
	 * 
	 * @see org.moxieapps.gwt.uploader.client.events.FileDialogStartHandler#onFileDialogStartEvent(org.moxieapps.gwt.uploader.client.events.FileDialogStartEvent)
	 */
	@Override
	public boolean onFileDialogStartEvent(
			FileDialogStartEvent fileDialogStartEvent) {
		if (uploader.getStats().getUploadsInProgress() <= 0) {
			// Clear the uploads that have completed, if none
			// are in process
			// progressBarPanel.clear();
			cancelButtons.clear();
		}
		return true;
	}

}
