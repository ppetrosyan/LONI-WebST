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

public class SaveResourceCSVServlet extends RemoteServiceServlet implements SaveResourceCSVService {

	private static final long serialVersionUID = -1248291926712674832L;
	private Key csvResourceKey;

	public SaveResourceCSVServlet() {
		csvResourceKey = KeyFactory.createKey("CSVType", "ResourceData");
	}

	@Override
	public boolean setCSVData(String csvData) {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		try {				
			// Get a file service
			FileService fileService = FileServiceFactory.getFileService();

			// Create a new Blob file with mime-type "text/plain"
			AppEngineFile file = fileService.createNewBlobFile("text/plain");

			// Open a channel to write to it
			boolean lock = true;
			FileWriteChannel writeChannel = fileService.openWriteChannel(file, lock);

			// Different standard Java ways of writing to the channel
			// are possible. Here we use a PrintWriter:
			PrintWriter out = new PrintWriter(Channels.newWriter(writeChannel, "UTF8"));
			out.println(csvData);

			// Close without finalizing and save the file path for writing later
			out.close();

			// Now finalize
			writeChannel.closeFinally();

			// Add Path to Hashtable
			Entity configurationData = new Entity(csvResourceKey);
			configurationData.setProperty("tag", file.getFullPath());
			dataStore.put(configurationData);
		}
		catch (IllegalStateException | IOException e) {
			return false;
		}

		return true;
	}
}