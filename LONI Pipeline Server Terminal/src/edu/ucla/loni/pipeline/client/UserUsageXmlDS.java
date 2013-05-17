package edu.ucla.loni.pipeline.client;

import com.smartgwt.client.data.DataSource;  
import com.smartgwt.client.data.fields.*;  
   
public class UserUsageXmlDS extends DataSource {  
  
    private static UserUsageXmlDS instance = null;  
  
    public static UserUsageXmlDS getInstance() {  
        if (instance == null) {  
            instance = new UserUsageXmlDS("userusageDS");  
        }  
        return instance;  
    }  
  
    public UserUsageXmlDS(String id) {  
  
        setID(id);  
        setRecordXPath("/List/userusage");   
  
        DataSourceTextField usernameField = new DataSourceTextField("username", "Username");  
        usernameField.setRequired(true);  
        
        DataSourceTextField workflowIDField = new DataSourceTextField("workflowID", "Workflow ID");  
        workflowIDField.setRequired(true);  
  
        DataSourceTextField nodeNameField = new DataSourceTextField("nodeName", "NodeName");  
        nodeNameField.setRequired(true);  
        
        DataSourceTextField instanceField = new DataSourceTextField("instance", "Instance");  
        instanceField.setRequired(true);   

        setFields(usernameField, workflowIDField, nodeNameField, instanceField);  
  
        setDataURL("XmlData/XmlData.xml");  
        setClientOnly(true);  
    }  
}  

