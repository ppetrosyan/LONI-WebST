package edu.ucla.loni.pipeline.client.MainPage.UsersOnline;
import com.smartgwt.client.widgets.grid.ListGridRecord;  

public class UsersOnlineRecord extends ListGridRecord {  

  public UsersOnlineRecord() {  
  }  

  public UsersOnlineRecord(String username, String ip_addr, String pipe_interface, String pipe_ver, String os_ver, String connect_time
		  , String last_activity) {  
      setUsername(username);  
      setIpAddress(ip_addr);  
      setPipeInterface(pipe_interface);  
      setPipeVersion(pipe_ver); 
      setOSVersion(os_ver);
      setConnectTime(connect_time);
      setLastActivity(last_activity);
  }  



  public void setUsername(String user_name) {  
      setAttribute("Username", user_name);  
  }  

  public String getUsername() {  
      return getAttributeAsString("Username");  
  }  

  public void setIpAddress(String ip_addr) {  
      setAttribute("IPAddress", ip_addr);  
  }  

  public String getCountryName() {  
      return getAttributeAsString("IPAddress");  
  }  

  public void setPipeInterface(String pipe_interface) {  
      setAttribute("PipelineInterface", pipe_interface);  
  }  

  public String getPineInterface() {  
      return getAttributeAsString("PipelineInterface");  
  }  
  
  public void setPipeVersion(String pipe_interface) {  
      setAttribute("PipelineVersion", pipe_interface);  
  }  

  public String getPineVersion() {  
      return getAttributeAsString("PipelineVersion");  
  }  

  public void setOSVersion(String os_ver) {  
      setAttribute("OSVersion", os_ver);  
  }  
 
  public String getOSVersion() {  
      return getAttributeAsString("OSVersion");  
  }  

  public void setConnectTime(String connect_time) {  
      setAttribute("ConnectTime", connect_time);  
  }  

  public String getConnectTime() {  
      return getAttributeAsString("ConnectTime");  
  }  

  public void setLastActivity(String last_activity) {  
      setAttribute("LastActivity", last_activity);  
  }  

  public String getLastActivity() {  
      return getAttributeAsString("LastActivity");  
  }  



}  