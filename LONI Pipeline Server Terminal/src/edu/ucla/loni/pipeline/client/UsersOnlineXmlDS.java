package edu.ucla.loni.pipeline.client;

import com.smartgwt.client.data.DataSource;  
import com.smartgwt.client.data.fields.*;  
   
public class UsersOnlineXmlDS extends DataSource {  
  
    private static UsersOnlineXmlDS instance = null;  
  
    public static UsersOnlineXmlDS getInstance() {  
        if (instance == null) {  
            instance = new UsersOnlineXmlDS("usersonlineDS");  
        }  
        return instance;  
    }  
  
    public UsersOnlineXmlDS(String id) {  
  
        setID(id);  
        setRecordXPath("/List/usersonline");   
  
        DataSourceTextField usernameField = new DataSourceTextField("username", "Username");  
        usernameField.setRequired(true);  
        
        DataSourceTextField ipAddressField = new DataSourceTextField("ipAddress", "IP Address");  
        ipAddressField.setRequired(true);  
  
        DataSourceTextField pipelineInterfaceField = new DataSourceTextField("pipelineInterface", "Pipeline Interface");  
        pipelineInterfaceField.setRequired(true);  
        
        DataSourceTextField pipelineVersionField = new DataSourceTextField("pipelineVersion", "Pipeline Version");  
        pipelineVersionField.setRequired(true);  
  
        DataSourceTextField osVersionField = new DataSourceTextField("osVersion", "OS Version");  
        osVersionField.setRequired(true);  
        
        DataSourceTextField connectTimeField = new DataSourceTextField("connectTime", "Connect Time");  
        connectTimeField.setRequired(true);  
        
        DataSourceTextField lastActivityField = new DataSourceTextField("lastActivity", "Last Activity");  
        lastActivityField.setRequired(true);  
  
        DataSourceTextField disconnectField = new DataSourceTextField("disconnect", "Disconnect");  
        disconnectField.setRequired(true);  

        setFields(usernameField, ipAddressField, pipelineInterfaceField, pipelineVersionField, osVersionField, connectTimeField,
        		lastActivityField, disconnectField);  
  
        setDataURL("XmlData/XmlData.xml");  
        setClientOnly(true);  
    }  
}  