package edu.ucla.loni.pipeline.client.MainPage.WorkFlows;

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
  
        DataSourceTextField workflowsField = new DataSourceTextField("workflowID", "Workflow ID");  
        workflowsField.setRequired(true);  
  
        DataSourceTextField usernameField = new DataSourceTextField("username", "Username");  
        usernameField.setRequired(true);  
        
        DataSourceTextField stateField = new DataSourceTextField("state", "State");  
        stateField.setRequired(true);  
  
        DataSourceTextField startTimeField = new DataSourceTextField("startTime", "Start Time");  
        startTimeField.setRequired(true);  
        
        DataSourceTextField endTimeField = new DataSourceTextField("endTime", "End Time");  
        endTimeField.setRequired(true);  
  
        DataSourceTextField durationField = new DataSourceTextField("duration", "Duration");  
        durationField.setRequired(true);  
        
        DataSourceTextField numofnodeField = new DataSourceTextField("numofnode", "N");  
        numofnodeField.setRequired(true);  
        
        DataSourceTextField numofinstancesField = new DataSourceTextField("numofinstances", "I");  
        numofinstancesField.setRequired(true);  
  
        DataSourceTextField numBacklabField = new DataSourceTextField("numBacklab", "B");  
        numBacklabField.setRequired(true);  
        
        DataSourceTextField numSubmittingField = new DataSourceTextField("numSubmitting", "S");  
        numSubmittingField.setRequired(true);  
        
        DataSourceTextField numQueuedField = new DataSourceTextField("numQueued", "Q");  
        numQueuedField.setRequired(true);  
  
        DataSourceTextField numRunningField = new DataSourceTextField("numRunning", "R");  
        numRunningField.setRequired(true);  
        
        DataSourceTextField numCompletedField = new DataSourceTextField("numCompleted", "C");  
        numCompletedField.setRequired(true);  
  
        setFields(workflowsField, usernameField, stateField, startTimeField, endTimeField,  
        		durationField, numofnodeField, numofinstancesField, numBacklabField, numSubmittingField,  
        		numQueuedField, numRunningField, numCompletedField);  
  
        setDataURL("XmlData/XmlData.xml");  
        setClientOnly(true);  
    }  
}  