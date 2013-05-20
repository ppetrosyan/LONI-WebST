package edu.ucla.loni.pipeline.client;

public class PackagesData {
	 private static PackagesRecord[] records;  
	  
	    public static PackagesRecord[] getRecords() {  
	        if (records == null) {  
	            records = getNewRecords();  
	        }  
	        return records;  
	    }  
	  
	    public static PackagesRecord[] getNewRecords() {  
	        return new PackagesRecord[]{  
	                new PackagesRecord("AAL", "*", "/user/local/loniData-1.0-_64bit", "", ""),
	                new PackagesRecord("AD", "5.5.2_26", "/user/local/loniData-1.0-_64bit", "", ""),
	                new PackagesRecord("ADNI", "*", "/user/local/", "", ""),
	                new PackagesRecord("AFNI", "1.0", "/user/local/loniData-1.0-_64bit", "", ""),
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
