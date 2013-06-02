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

package edu.ucla.loni.pipeline.client.MainPage.WorkFlows;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class WorkFlowsRecord extends ListGridRecord {

	public WorkFlowsRecord() {
	}

	public WorkFlowsRecord(String workflowID, String username, String state,
			String startTime, String endTime, String duration,
			String numofnode, String numofinstances, String numBacklog,
			String numSubmitting, String numQueued, String numRunning,
			String numCompleted) {
		setWorkflowID(workflowID);
		setUsername(username);
		setState(state);
		setStartTime(startTime);
		setEndTime(endTime);
		setDuration(duration);
		setNumofnode(numofnode);
		setNumofinstances(numofinstances);
		setNumBacklog(numBacklog);
		setNumSubmitting(numSubmitting);
		setNumQueued(numQueued);
		setNumRunning(numRunning);
		setNumCompleted(numCompleted);
	}

	public void setWorkflowID(String workflowID) {
		setAttribute("workflowID", workflowID);
	}

	public String getWorkflowID() {
		return getAttributeAsString("workflowID");
	}

	public void setUsername(String username) {
		setAttribute("username", username);
	}

	public String getUsername() {
		return getAttributeAsString("username");
	}

	public void setState(String state) {
		setAttribute("state", state);
	}

	public String getState() {
		return getAttributeAsString("state");
	}

	public void setStartTime(String startTime) {
		setAttribute("startTime", startTime);
	}

	public String getStartTime() {
		return getAttributeAsString("startTime");
	}

	public void setEndTime(String endTime) {
		setAttribute("endTime", endTime);
	}

	public String getEndTime() {
		return getAttributeAsString("endTime");
	}

	public void setDuration(String duration) {
		setAttribute("duration", duration);
	}

	public String getDuration() {
		return getAttributeAsString("duration");
	}

	public void setNumofnode(String numofnode) {
		setAttribute("numofnode", numofnode);
	}

	public String getNumofnode() {
		return getAttributeAsString("numofnode");
	}

	public void setNumofinstances(String numofinstances) {
		setAttribute("numofinstances", numofinstances);
	}

	public String getNumofinstances() {
		return getAttributeAsString("numofinstances");
	}

	public void setNumBacklog(String numBacklog) {
		setAttribute("numBacklog", numBacklog);
	}

	public String getNumBacklog() {
		return getAttributeAsString("numBacklog");
	}

	public void setNumSubmitting(String numSubmitting) {
		setAttribute("numSubmitting", numSubmitting);
	}

	public String getNumSubmitting() {
		return getAttributeAsString("numSubmitting");
	}

	public void setNumQueued(String numQueued) {
		setAttribute("numQueued", numQueued);
	}

	public String getNumQueued() {
		return getAttributeAsString("numQueued");
	}

	public void setNumRunning(String numRunning) {
		setAttribute("numRunning", numRunning);
	}

	public String getNumRunning() {
		return getAttributeAsString("numRunning");
	}

	public void setNumCompleted(String numCompleted) {
		setAttribute("numCompleted", numCompleted);
	}

	public String getNumCompleted() {
		return getAttributeAsString("numCompleted");
	}
}