package edu.ucla.loni.pipeline.client.MainPage.Preferences.Executables;

import com.smartgwt.client.widgets.grid.ListGridRecord; 

public class ExecutablesRecord extends ListGridRecord {  

  public ExecutablesRecord() {  
  }  

  public ExecutablesRecord(String executables_name, String version, String location)
  {  
      setExecutablesName(executables_name);  
      setVersion(version);  
      setLocation(location);  
    
  }  

  public void setExecutablesName(String executables_name) {  
      setAttribute("executables_name", executables_name);  
  }  

  public String getExecutablesName() {  
      return getAttributeAsString("executables_name");  
  }  

  public void setVersion(String version) {  
      setAttribute("version", version);  
  }  

  public String getVersion() {  
      return getAttributeAsString("verison");  
  }  

  public void setLocation(String location) {  
      setAttribute("location", location);  
  }  

  public String getLocation() {  
      return getAttributeAsString("location");  
  }  
}  