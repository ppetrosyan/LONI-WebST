package edu.ucla.loni.pipeline.client.MainPage.Preferences.Packages;

import com.smartgwt.client.widgets.grid.ListGridRecord; 

public class PackagesRecord extends ListGridRecord {  

  public PackagesRecord() {  
  }  

  public PackagesRecord(String package_name, String version, String location, String variables, String sources)
  {  
      setPackageName(package_name);  
      setVersion(version);  
      setLocation(location);  
      setVariables(variables); 
      setSources(sources);
    
  }  

  public void setPackageName(String user_name) {  
      setAttribute("package_name", user_name);  
  }  

  public String getPackageName() {  
      return getAttributeAsString("package_name");  
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
  
  public void setVariables(String variables) {  
      setAttribute("variables", variables);  
  }  

  public String getVariables() {  
      return getAttributeAsString("Version");  
  }  

  public void setSources(String sources) {  
      setAttribute("souces", sources);  
  }  

  public String getSources() {  
      return getAttributeAsString("sources");  
  }  
}  