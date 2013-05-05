package edu.ucla.loni.pipeline.client;

public class OnlineData {
	 private static OnlineRecord[] records;  
	  
	    public static OnlineRecord[] getRecords() {  
	        if (records == null) {  
	            records = getNewRecords();  
	        }  
	        return records;  
	    }  
	  
	    public static OnlineRecord[] getNewRecords() {  
	        return new OnlineRecord[]{  
	                new OnlineRecord("North America", "United States", "US", "9631420", "298444215","111", "abcd", "qqqq"),
	                new OnlineRecord("North", "United States", "US", "24y3784y", "298444215","111", "abcd", "qqqq"),
	                new OnlineRecord("North America", "United States", "US", "9631420", "298444215","111", "abcd", "qqqq"),
	                new OnlineRecord("North", "United States", "US", "24y3784y", "298444215","111", "abcd", "qqqq")
	    };
	}
}
/*
 // source: http://www.mkyong.com/java/how-to-export-data-to-csv-file-java/
 * package com.mkyong.test;
 
import java.io.FileWriter;
import java.io.IOException;
 
public class GenerateCsv
{
   public static void main(String [] args)
   {
	   generateCsvFile("c:\\test.csv"); 
   }
 
   private static void generateCsvFile(String sFileName)
   {
	try
	{
	    FileWriter writer = new FileWriter(sFileName);
 
	    writer.append("DisplayName");
	    writer.append(',');
	    writer.append("Age");
	    writer.append('\n');
 
	    writer.append("MKYONG");
	    writer.append(',');
	    writer.append("26");
            writer.append('\n');
 
	    writer.append("YOUR NAME");
	    writer.append(',');
	    writer.append("29");
	    writer.append('\n');
 
	    //generate whatever data you want
 
	    writer.flush();
	    writer.close();
	}
	catch(IOException e)
	{
	     e.printStackTrace();
	} 
    }
}
 */
