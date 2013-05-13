package edu.ucla.loni.pipeline.client;

import com.smartgwt.client.data.DataSource;  
import com.smartgwt.client.data.fields.*;  
   
public class UserUsageCountXmlDS extends DataSource {  
  
    private static UserUsageCountXmlDS instance = null;  
  
    public static UserUsageCountXmlDS getInstance() {  
        if (instance == null) {  
            instance = new UserUsageCountXmlDS("userusagecountDS");  
        }  
        return instance;  
    }  
  
    public UserUsageCountXmlDS(String id) {  
  
        setID(id);  
        setRecordXPath("/List/userusagecount");   
  
        DataSourceTextField usernameField = new DataSourceTextField("username", "Username");  
        usernameField.setRequired(true);  
        
        DataSourceTextField countField = new DataSourceTextField("count", "Count");  
        countField.setRequired(true);  

        setFields(usernameField, countField);  
  
        setDataURL("XmlData/XmlData.xml");  
        setClientOnly(true);  
    }  
}  

