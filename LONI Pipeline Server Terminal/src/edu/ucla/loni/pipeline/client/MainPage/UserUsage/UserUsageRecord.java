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

package edu.ucla.loni.pipeline.client.MainPage.UserUsage;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class UserUsageRecord extends ListGridRecord {

	public UserUsageRecord() {
	}

	public UserUsageRecord(String username, String workflowID, String nodeName,
			String instance) {
		setUsername(username);
		setWorkflowID(workflowID);
		setNodeName(nodeName);
		setInstance(instance);
	}

	public void setUsername(String username) {
		setAttribute("username", username);
	}

	public String getUsername() {
		return getAttributeAsString("username");
	}

	public void setWorkflowID(String workflowID) {
		setAttribute("workflowID", workflowID);
	}

	public String getWorkflowID() {
		return getAttributeAsString("workflowID");
	}

	public void setNodeName(String nodeName) {
		setAttribute("nodeName", nodeName);
	}

	public String getNodeName() {
		return getAttributeAsString("nodeName");
	}

	public void setInstance(String instance) {
		setAttribute("instance", instance);
	}

	public String getInstance() {
		return getAttributeAsString("instance");
	}
}