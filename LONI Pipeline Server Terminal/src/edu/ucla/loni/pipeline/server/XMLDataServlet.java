package edu.ucla.loni.pipeline.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.Channels;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileReadChannel;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.ucla.loni.pipeline.client.Requesters.XMLDataService;

public class XMLDataServlet extends RemoteServiceServlet implements XMLDataService {

	private static final long serialVersionUID = 5448261063802349760L;
	private Key xmlSimulatedKey, xmlConfigurationKey;

	public XMLDataServlet() {
		xmlConfigurationKey = KeyFactory.createKey("XMLType", "ConfigurationData");
		xmlSimulatedKey = KeyFactory.createKey("XMLType", "SimulatedData");
	}

	public String getXMLData(String xmlDataType) {

		String xmlData = "";

		FileService fileService = FileServiceFactory.getFileService();
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		try {
			if(xmlDataType.equalsIgnoreCase("ConfigurationData")) {

				Entity configurationData = dataStore.get(xmlConfigurationKey); 

				String configurationTag = (String) configurationData.getProperty("tag");

				AppEngineFile file = new AppEngineFile(configurationTag);

				// Later, read from the file using the file API
				boolean lock = false; // Let other people read at the same time
				FileReadChannel readChannel = fileService.openReadChannel(file, lock);

				// Again, different standard Java ways of reading from the channel.
				BufferedReader reader =
						new BufferedReader(Channels.newReader(readChannel, "UTF8"));

				StringBuilder stringBuilder = new StringBuilder();

				String line;
				while((line = reader.readLine()) != null)
					stringBuilder.append(line);

				readChannel.close();

				xmlData = stringBuilder.toString();
			}
			else if(xmlDataType.equalsIgnoreCase("SimulatedData")) {
				Entity simulatedData = dataStore.get(xmlSimulatedKey);
				String simulatedTag = (String) simulatedData.getProperty("tag");

				AppEngineFile file = new AppEngineFile(simulatedTag);

				// Later, read from the file using the file API
				boolean lock = false; // Let other people read at the same time
				FileReadChannel readChannel = fileService.openReadChannel(file, lock);

				// Again, different standard Java ways of reading from the channel.
				BufferedReader reader =
						new BufferedReader(Channels.newReader(readChannel, "UTF8"));

				StringBuilder stringBuilder = new StringBuilder();

				String line;
				while((line = reader.readLine()) != null)
					stringBuilder.append(line);

				readChannel.close();

				xmlData = stringBuilder.toString();
			}
			else {
				/** Do Nothing */
			}
		}
		catch (EntityNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		return xmlData;
	}
}