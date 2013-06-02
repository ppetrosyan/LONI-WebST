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

package edu.ucla.loni.pipeline.server.Savers.Configuration;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.Channels;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.ucla.loni.pipeline.client.Savers.Configuration.SaveResourceCSVService;

/**
 * Stores CSV data in server Blobstore
 * 
 * @author Jared
 */
public class SaveResourceCSVServlet extends RemoteServiceServlet implements
		SaveResourceCSVService {

	private static final long serialVersionUID = -1248291926712674832L;
	private final Key csvResourceKey;

	/**
	 * Constructor
	 */
	public SaveResourceCSVServlet() {
		csvResourceKey = KeyFactory.createKey("CSVType", "ResourceData");
	}

	/**
	 * Stores CSV data in server Blobstore
	 * 
	 * @see edu.ucla.loni.pipeline.client.Savers.Configuration.SaveResourceCSVService#setCSVData(java.lang.String)
	 */
	@Override
	public boolean setCSVData(String csvData) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();

		try {
			// Get a file service
			FileService fileService = FileServiceFactory.getFileService();

			// Create a new Blob file with mime-type "text/plain"
			AppEngineFile file = fileService.createNewBlobFile("text/plain");

			// Open a channel to write to it
			boolean lock = true;
			FileWriteChannel writeChannel = fileService.openWriteChannel(file,
					lock);

			// Different standard Java ways of writing to the channel
			// are possible. Here we use a PrintWriter:
			PrintWriter out = new PrintWriter(Channels.newWriter(writeChannel,
					"UTF8"));
			out.println(csvData);

			// Close without finalizing and save the file path for writing later
			out.close();

			// Now finalize
			writeChannel.closeFinally();

			// Add Path to Hashtable
			Entity configurationData = new Entity(csvResourceKey);
			configurationData.setProperty("tag", file.getFullPath());
			dataStore.put(configurationData);
		} catch (IllegalStateException | IOException e) {
			return false;
		}

		return true;
	}
}