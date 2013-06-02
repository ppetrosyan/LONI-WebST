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

import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class LONIDropHandler implements DropHandler {

	private final Uploader uploader;
	private final Map<String, Image> cancelButtons;
	private final Label dropFilesLabel;

	public LONIDropHandler(Uploader uploader, Map<String, Image> cancelButtons,
			VLayout layoutUploads, Label dropFilesLabel) {
		this.uploader = uploader;
		this.cancelButtons = cancelButtons;
		this.dropFilesLabel = dropFilesLabel;
	}

	@Override
	public void onDrop(DropEvent event) {
		dropFilesLabel.removeStyleName("dropFilesLabelHover");

		if (uploader.getStats().getUploadsInProgress() <= 0) {
			// progressBarPanel.clear();
			cancelButtons.clear();
		}

		uploader.addFilesToQueue(Uploader.getDroppedFiles(event
				.getNativeEvent()));
		event.preventDefault();
	}

}
