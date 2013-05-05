package edu.ucla.loni.pipeline.client;

import com.smartgwt.client.widgets.grid.ListGridRecord; 


public class OnlineRecord extends ListGridRecord {  

  public OnlineRecord() {  
  }  

  public OnlineRecord(String username, String ip_addr, String pipe_interface, String pipe_ver, String os_ver, String connect_time
		  , String last_activity, String disconnect) {  
      setUsername(username);  
      setIpAddress(ip_addr);  
      setPipeInterface(pipe_interface);  
      setPipeVersion(pipe_ver); 
      setOSVersion(os_ver);
      setConnectTime(connect_time);
      setLastActivity(last_activity);
      setDisconnect(disconnect);
  }  



  public void setUsername(String user_name) {  
      setAttribute("username", user_name);  
  }  

  public String getUsername() {  
      return getAttributeAsString("username");  
  }  

  public void setIpAddress(String ip_addr) {  
      setAttribute("ipAddress", ip_addr);  
  }  

  public String getCountryName() {  
      return getAttributeAsString("ipAddress");  
  }  

  public void setPipeInterface(String pipe_interface) {  
      setAttribute("pipelineInterface", pipe_interface);  
  }  

  public String getPineInterface() {  
      return getAttributeAsString("pipelineInterface");  
  }  
  
  public void setPipeVersion(String pipe_interface) {  
      setAttribute("pipelineVersion", pipe_interface);  
  }  

  public String getPineVersion() {  
      return getAttributeAsString("pipelineVersion");  
  }  

  public void setOSVersion(String os_ver) {  
      setAttribute("osVersion", os_ver);  
  }  

  public String getOSVersion() {  
      return getAttributeAsString("osVersion");  
  }  

  public void setConnectTime(String connect_time) {  
      setAttribute("connectTime", connect_time);  
  }  

  public String getConnectTime() {  
      return getAttributeAsString("connectTime");  
  }  

  public void setLastActivity(String last_activity) {  
      setAttribute("lastActivity", last_activity);  
  }  

  public String getLastActivity() {  
      return getAttributeAsString("lastActivity");  
  }  

  public void setDisconnect(String disconnect) {  
      setAttribute("disconnect", disconnect);  
  }  

  public String getDisconnect() {  
      return getAttributeAsString("disonnect");  
  }  


}  