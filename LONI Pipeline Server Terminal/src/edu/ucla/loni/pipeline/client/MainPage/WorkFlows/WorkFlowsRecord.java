package edu.ucla.loni.pipeline.client.MainPage.WorkFlows;

import com.smartgwt.client.widgets.grid.ListGridRecord;  
  
public class WorkFlowsRecord extends ListGridRecord {  
  
    public WorkFlowsRecord() {  
    }  
  
    public WorkFlowsRecord(String workflowID, String username, String state, String startTime,
    		String endTime, String duration, String numofnode, String numofinstances,
    		String numBacklab, String numSubmitting, String numQueued, String numRunning, String numCompleted) {  
    	setWorkflowID(workflowID);  
        setUsername(username);  
        setState(state);  
        setStartTime(startTime); 
        setEndTime(endTime);
        setDuration(duration);
        setNumofnode(numofnode);
        setNumofinstances(numofinstances);
        setNumBacklab(numBacklab);
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
  
    public void setNumBacklab(String numBacklab) {  
        setAttribute("numBacklab", numBacklab);  
    }  
  
    public String getNumBacklab() {  
        return getAttributeAsString("numBacklab");  
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