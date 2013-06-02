/*
 * This file is part of LONI Pipeline Web-based Serverimport com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.user.client.ui.Label;
er General Public License as published by the Free Software 
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

import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.user.client.ui.Label;

public class LONIDragLeaveHandler implements DragLeaveHandler {

	private final Label dropFilesLabel;

	public LONIDragLeaveHandler(Label dropFilesLabel) {
		this.dropFilesLabel = dropFilesLabel;
	}

	@Override
	public void onDragLeave(DragLeaveEvent event) {
		dropFilesLabel.removeStyleName("dropFilesLabelHover");
	}

}
