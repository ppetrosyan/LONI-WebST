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

package edu.ucla.loni.pipeline.client.Upload.Features;

import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.smartgwt.client.widgets.layout.VLayout;

import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIDragLeaveHandler;
import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIDragOverHandler;
import edu.ucla.loni.pipeline.client.Upload.Handlers.LONIDropHandler;

/**
 * Constructs a Drag and Drop Label for the Web interface
 * 
 * @author Jared
 */
public class LONIDragandDropLabel extends Label {

	private final Uploader uploader;
	private final Map<String, Image> cancelButtons;
	private final VLayout layoutUploads;

	/**
	 * Constructor
	 * 
	 * @param labelString
	 * @param uploader
	 * @param cancelButtons
	 * @param layoutUploads
	 */
	public LONIDragandDropLabel(String labelString, Uploader uploader,
			Map<String, Image> cancelButtons, VLayout layoutUploads) {
		super(labelString);

		this.uploader = uploader;
		this.cancelButtons = cancelButtons;
		this.layoutUploads = layoutUploads;

		configure();
		addHandlers();
	}

	/**
	 * Configures the Drag and Drop Label
	 * 
	 * @return success
	 */
	private boolean configure() {
		setStyleName("dropFilesLabel");

		return true;
	}

	/**
	 * Adds Event Handlers to the Drag and Drop Label
	 * 
	 * @return success
	 */
	private boolean addHandlers() {
		addDragOverHandler(new LONIDragOverHandler(this));

		addDragLeaveHandler(new LONIDragLeaveHandler(this));

		addDropHandler(new LONIDropHandler(uploader, cancelButtons,
				layoutUploads, this));

		return true;
	}
}
