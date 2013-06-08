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
import org.moxieapps.gwt.uploader.client.events.UploadCompleteEvent;
import org.moxieapps.gwt.uploader.client.events.UploadCompleteHandler;

import com.google.gwt.user.client.ui.Image;

/**
 * Upload Complete Event Handler
 */
public class LONIUploadCompleteHandler implements UploadCompleteHandler {

	private final Uploader uploader;
	private final Map<String, Image> cancelButtons;

	/**
	 * Constructor
	 * 
	 * @param uploader
	 * @param cancelButtons
	 */
	public LONIUploadCompleteHandler(Uploader uploader,
			Map<String, Image> cancelButtons) {
		this.uploader = uploader;
		this.cancelButtons = cancelButtons;
	}

	/**
	 * Handles an Upload Complete Event
	 * 
	 * @see org.moxieapps.gwt.uploader.client.events.UploadCompleteHandler#onUploadComplete(org.moxieapps.gwt.uploader.client.events.UploadCompleteEvent)
	 */
	@Override
	public boolean onUploadComplete(UploadCompleteEvent uploadCompleteEvent) {
		cancelButtons.get(uploadCompleteEvent.getFile().getId())
				.removeFromParent();
		uploader.startUpload();
		return true;
	}

}
