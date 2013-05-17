package edu.ucla.loni.pipeline.server.Requesters.ResourceUsage;

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

import edu.ucla.loni.pipeline.client.Requesters.ResourceUsage.RequestResourceXMLService;

public class RequestResourceXMLServlet extends RemoteServiceServlet implements RequestResourceXMLService {

	private static final long serialVersionUID = 5448261063802349760L;
	private Key xmlResourceKey;

	public RequestResourceXMLServlet() {
		xmlResourceKey = KeyFactory.createKey("XMLType", "ResourceData");
	}

	@Override
	public String getXMLData() {
		String xmlData = "";

		FileService fileService = FileServiceFactory.getFileService();
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		try {
			Entity simulatedData = dataStore.get(xmlResourceKey);

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
		catch (EntityNotFoundException | IOException e) {
			System.out.println("Entity Not Found.");
		}

		return xmlData;
	}
}