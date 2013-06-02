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

import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.user.client.ui.Label;

/**
 * Drag Over Event Handler
 * 
 * @author Jared
 */
public class LONIDragOverHandler implements DragOverHandler {

	private final Label dropFilesLabel;

	/**
	 * Constructor
	 * 
	 * @param dropFilesLabel
	 */
	public LONIDragOverHandler(Label dropFilesLabel) {
		this.dropFilesLabel = dropFilesLabel;
	}

	/**
	 * Handles a Drag Over Event
	 * 
	 * @see com.google.gwt.event.dom.client.DragOverHandler#onDragOver(com.google.gwt.event.dom.client.DragOverEvent)
	 */
	@Override
	public void onDragOver(DragOverEvent event) {
		// if (!uploader.getButtonDisabled()) {
		dropFilesLabel.addStyleName("dropFilesLabelHover");
	}
	// }

}
