package edu.ucla.loni.pipeline.client; 
  
public class WorkFlowsData {  
  
    private static WorkFlowsRecord[] records;  
  
    public static WorkFlowsRecord[] getRecords() {  
        if (records == null) {  
            records = getNewRecords();  
        }  
        return records;  
    }  
  
    public static WorkFlowsRecord[] getNewRecords() {  
        return new WorkFlowsRecord[]{  
                new WorkFlowsRecord("11374", "William", "Running", "12:30", "13:00", "00:30", "4", "3", "1", "0", "0", "2", "1"), 
                new WorkFlowsRecord("26453", "Peter", "Running", "11:30", "14:00", "02:30", "1", "0", "2", "0", "0", "1", "1"),
                new WorkFlowsRecord("32135", "John", "Waiting", "13:30", "14:00", "00:30", "3", "4", "0", "0", "0", "0", "0")
        };  
    }  
}  