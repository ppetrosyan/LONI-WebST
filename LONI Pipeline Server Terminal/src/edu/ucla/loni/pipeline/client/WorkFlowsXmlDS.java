package edu.ucla.loni.pipeline.client;

import com.smartgwt.client.data.DataSource;  
import com.smartgwt.client.data.fields.*;  
   
public class WorkFlowsXmlDS extends DataSource {  
  
    private static WorkFlowsXmlDS instance = null;  
  
    public static WorkFlowsXmlDS getInstance() {  
        if (instance == null) {  
            instance = new WorkFlowsXmlDS("workflowsDS");  
        }  
        return instance;  
    }  
  
    public WorkFlowsXmlDS(String id) {  
  
        setID(id);  
        setRecordXPath("/List/workflow");   
  
        DataSourceTextField workflowsField = new DataSourceTextField("workflowID", "workflowID");  
        workflowsField.setRequired(true);  
  
        DataSourceTextField usernameField = new DataSourceTextField("username", "username");  
        usernameField.setRequired(true);  
        
        DataSourceTextField stateField = new DataSourceTextField("state", "state");  
        stateField.setRequired(true);  
  
        DataSourceTextField startTimeField = new DataSourceTextField("startTime", "startTime");  
        startTimeField.setRequired(true);  
        
        DataSourceTextField endTimeField = new DataSourceTextField("endTime", "endTime");  
        endTimeField.setRequired(true);  
  
        DataSourceTextField durationField = new DataSourceTextField("duration", "duration");  
        durationField.setRequired(true);  
        
        DataSourceTextField numofnodeField = new DataSourceTextField("numofnode", "numofnode");  
        numofnodeField.setRequired(true);  
        
        DataSourceTextField numofinstancesField = new DataSourceTextField("numofinstances", "numofinstances");  
        numofinstancesField.setRequired(true);  
  
        DataSourceTextField numBacklabField = new DataSourceTextField("numBacklab", "numBacklab");  
        numBacklabField.setRequired(true);  
        
        DataSourceTextField numSubmittingField = new DataSourceTextField("numSubmitting", "numSubmitting");  
        numSubmittingField.setRequired(true);  
        
        DataSourceTextField numQueuedField = new DataSourceTextField("numQueued", "numQueued");  
        numQueuedField.setRequired(true);  
  
        DataSourceTextField numRunningField = new DataSourceTextField("numRunning", "numRunning");  
        numRunningField.setRequired(true);  
        
        DataSourceTextField numCompletedField = new DataSourceTextField("numCompleted", "numCompleted");  
        numCompletedField.setRequired(true);  
  
        
  
        setFields(workflowsField, usernameField, stateField, startTimeField, endTimeField,  
        		durationField, numofnodeField, numofinstancesField, numBacklabField, numSubmittingField,  
        		numQueuedField, numRunningField, numCompletedField);  
  
        setDataURL("XmlData/WorkFlowsData.xml");  
        setClientOnly(true);  
    }  
}  