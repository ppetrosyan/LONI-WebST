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

import org.moxieapps.gwt.uploader.client.events.UploadErrorEvent;
import org.moxieapps.gwt.uploader.client.events.UploadErrorHandler;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;

/**
 * Upload Error Event Handler
 * 
 * @author Jared
 */
public class LONIUploadErrorHandler implements UploadErrorHandler {

	private final Map<String, Image> cancelButtons;

	/**
	 * Constructor
	 * 
	 * @param cancelButtons
	 */
	public LONIUploadErrorHandler(Map<String, Image> cancelButtons) {
		this.cancelButtons = cancelButtons;
	}

	/**
	 * Handles an Upload Error Event
	 * 
	 * @see org.moxieapps.gwt.uploader.client.events.UploadErrorHandler#onUploadError(org.moxieapps.gwt.uploader.client.events.UploadErrorEvent)
	 */
	@Override
	public boolean onUploadError(UploadErrorEvent uploadErrorEvent) {

		cancelButtons.get(uploadErrorEvent.getFile().getId())
				.removeFromParent();
		Window.alert("Upload of file " + uploadErrorEvent.getFile().getName()
				+ " failed due to ["
				+ uploadErrorEvent.getErrorCode().toString() + "]: "
				+ uploadErrorEvent.getMessage());
		return true;
	}
}
