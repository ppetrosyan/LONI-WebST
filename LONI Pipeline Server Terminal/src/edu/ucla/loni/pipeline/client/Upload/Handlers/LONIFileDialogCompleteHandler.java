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

import org.moxieapps.gwt.uploader.client.Uploader;
import org.moxieapps.gwt.uploader.client.events.FileDialogCompleteEvent;
import org.moxieapps.gwt.uploader.client.events.FileDialogCompleteHandler;

/**
 * File Dialog Complete Event Handler
 */
public class LONIFileDialogCompleteHandler implements FileDialogCompleteHandler {

	private final Uploader uploader;

	/**
	 * Constructor
	 * 
	 * @param uploader
	 */
	public LONIFileDialogCompleteHandler(Uploader uploader) {
		this.uploader = uploader;
	}

	/**
	 * Handles a File Dialog Complete Event
	 * 
	 * @see org.moxieapps.gwt.uploader.client.events.FileDialogCompleteHandler#onFileDialogComplete(org.moxieapps.gwt.uploader.client.events.FileDialogCompleteEvent)
	 */
	@Override
	public boolean onFileDialogComplete(
			FileDialogCompleteEvent fileDialogCompleteEvent) {

		if (fileDialogCompleteEvent.getTotalFilesInQueue() > 0) {
			if (uploader.getStats().getUploadsInProgress() <= 0) {
				uploader.startUpload();
			}
		}
		return true;
	}
}