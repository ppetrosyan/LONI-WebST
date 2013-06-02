/*
 * This file is part of LONI Pipeline Web-based Server Terminal.
 * 
 * LONI Pipeline Web-based Server Terminal is free software: 
 * you can redistribute it and/or modify it under the terms of the 
 * GNU Lesser General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * LONI Pipeline Web-based Server Terminal is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the 
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public License
 * along with LONI Pipeline Web-based Server Terminal.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package edu.ucla.loni.pipeline.client.MainPage.Preferences.Packages;

public class PackagesData {
	private static PackagesRecord[] records;

	public static PackagesRecord[] getRecords() {
		if (records == null) {
			records = getNewRecords();
		}
		return records;
	}

	public static PackagesRecord[] getNewRecords() {
		return new PackagesRecord[] {
				new PackagesRecord("AAL", "*",
						"/user/local/loniData-1.0-_64bit", "", ""),
				new PackagesRecord("AD", "5.5.2_26",
						"/user/local/loniData-1.0-_64bit", "", ""),
				new PackagesRecord("ADNI", "*", "/user/local/", "", ""),
				new PackagesRecord("AFNI", "1.0",
						"/user/local/loniData-1.0-_64bit", "", ""), };
	}
}
/*
 * // source: http://www.mkyong.com/java/how-to-export-data-to-csv-file-java/
 * package com.mkyong.test;
 * 
 * import java.io.FileWriter; import java.io.IOException;
 * 
 * public class GenerateCsv { public static void main(String [] args) {
 * generateCsvFile("c:\\test.csv"); }
 * 
 * private static void generateCsvFile(String sFileName) { try { FileWriter
 * writer = new FileWriter(sFileName);
 * 
 * writer.append("DisplayName"); writer.append(','); writer.append("Age");
 * writer.append('\n');
 * 
 * writer.append("MKYONG"); writer.append(','); writer.append("26");
 * writer.append('\n');
 * 
 * writer.append("YOUR NAME"); writer.append(','); writer.append("29");
 * writer.append('\n');
 * 
 * //generate whatever data you want
 * 
 * writer.flush(); writer.close(); } catch(IOException e) { e.printStackTrace();
 * } } }
 */
