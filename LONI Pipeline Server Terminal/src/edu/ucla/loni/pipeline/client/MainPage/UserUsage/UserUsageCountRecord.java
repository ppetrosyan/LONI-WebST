package edu.ucla.loni.pipeline.client.MainPage.UserUsage;

import com.smartgwt.client.widgets.grid.ListGridRecord;  
  
public class UserUsageCountRecord extends ListGridRecord {  
  
    public UserUsageCountRecord() {  
    }  
  
    public UserUsageCountRecord(String username, String count) {  
    	setUsername(username); 
        setCount(count); 
    }  
    
    public void setUsername(String username) {  
        setAttribute("username", username);  
    }  
  
    public String getUsername() {  
        return getAttributeAsString("username");  
    }  

	public void setCount(String count) {  
        setAttribute("count", count);  
    }  
  
    public String getCount() {  
        return getAttributeAsString("count");  
    }  
}  