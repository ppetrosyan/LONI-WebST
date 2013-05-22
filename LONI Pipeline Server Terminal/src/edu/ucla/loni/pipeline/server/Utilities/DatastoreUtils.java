package edu.ucla.loni.pipeline.server.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.channels.Channels;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileReadChannel;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;

public class DatastoreUtils {

	public static boolean writeXMLFileToBlobStore(Document document, Key xmlKey, ResponseBuilder respBuilder) {
		try {
			// Initialize DataStore
			DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

			// Get a file service
			FileService fileService = FileServiceFactory.getFileService();

			// Create a new Blob file with mime-type "text/plain"
			AppEngineFile file = fileService.createNewBlobFile("text/plain");

			// Open a channel to write to it
			boolean lock = true;
			FileWriteChannel writeChannel = fileService.openWriteChannel(file, lock);

			DOMSource domSource = new DOMSource(document);

			StringWriter writer = new StringWriter();
			StreamResult streamResult = new StreamResult(writer);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(domSource, streamResult);

			// Different standard Java ways of writing to the channel
			// are possible. Here we use a PrintWriter:
			PrintWriter out = new PrintWriter(Channels.newWriter(writeChannel, "UTF8"));
			out.println(writer.toString());

			// Close without finalizing and save the file path for writing later
			out.close();

			// Now finalize
			writeChannel.closeFinally();

			// Add Path to Hashtable
			Entity data = new Entity(xmlKey);
			data.setProperty("tag", file.getFullPath());
			dataStore.put(data);
		}
		catch (TransformerException e) {
			respBuilder.appendRespMessage("An error occurred during transformation of DOM Source to stream.\n");
			return false;
		} 
		catch (IOException e) {
			respBuilder.appendRespMessage("Could not create new file in blobstore.");
			return false;
		}

		return true;
	}

	public static String readXMLFileFromBlobStore(Key xmlKey, ResponseBuilder respBuilder) {
		String xmlData = "";

		FileService fileService = FileServiceFactory.getFileService();
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

		try {
			Entity configurationData = dataStore.get(xmlKey); 

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
		catch (EntityNotFoundException e) {
			respBuilder.appendRespMessage("Entity Not Found: " + xmlKey.getName());
		}
		catch (IOException e) {
			respBuilder.appendRespMessage("Could not read XML file from Blobstore.");
		}

		return xmlData;
	}
}
