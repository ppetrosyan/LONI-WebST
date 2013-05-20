package edu.ucla.loni.pipeline.client;

public class ExecutablesData {
	 private static ExecutablesRecord[] records;  
	  
	    public static ExecutablesRecord[] getRecords() {  
	        if (records == null) {  
	            records = getNewRecords();  
	        }  
	        return records;  
	    }  
	  
	    public static ExecutablesRecord[] getNewRecords() {  
	        return new ExecutablesRecord[]{  
	                new ExecutablesRecord("java", "*", "/usr/local/java/latest/bin/java"),
	                new ExecutablesRecord("perl", "*", "usr/bin/perl")};
	        }
	}

