package edu.ucla.loni.pipeline.client.MainPage.UserUsage;

import com.smartgwt.client.widgets.grid.ListGridRecord;  
  
public class UserUsageRecord extends ListGridRecord {  
  
    public UserUsageRecord() {  
    }  
  
    public UserUsageRecord(String username, String workflowID, String nodeName,String instance) {  
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