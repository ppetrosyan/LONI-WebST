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

import com.google.appengine.api.blobstore.BlobKey;
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

import edu.ucla.loni.pipeline.server.Exceptions.BlobKeyIsInvalidException;

/**
 * Utilities for Storing XML Data to server "Blobstore"
 */
public class DatastoreUtils {

	/**
	 * Writes an XML file to server Blobstore
	 * 
	 * @param document
	 * @param xmlKey
	 * @param respBuilder
	 * @return success
	 */
	public static boolean writeXMLFileToBlobStore(Document document,
			Key xmlKey, ResponseBuilder respBuilder) {
		try {
			// Initialize DataStore
			DatastoreService dataStore = DatastoreServiceFactory
					.getDatastoreService();

			// Get a file service
			FileService fileService = FileServiceFactory.getFileService();

			// Create a new Blob file with mime-type "text/plain"
			AppEngineFile file = fileService.createNewBlobFile("text/plain");

			// Open a channel to write to it
			boolean lock = true;
			FileWriteChannel writeChannel = fileService.openWriteChannel(file,
					lock);

			DOMSource domSource = new DOMSource(document);

			StringWriter writer = new StringWriter();
			StreamResult streamResult = new StreamResult(writer);

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(domSource, streamResult);

			// Different standard Java ways of writing to the channel
			// are possible. Here we use a PrintWriter:
			PrintWriter out = new PrintWriter(Channels.newWriter(writeChannel,
					"UTF8"));
			out.println(writer.toString());

			// Close without finalizing and save the file path for writing later
			out.close();

			// Now finalize
			writeChannel.closeFinally();

			// Add Path to Hashtable
			BlobKey xmlBlobKeytoDelete = null;
			boolean previousExists = true;

			try {
				Entity xmlEntity = dataStore.get(xmlKey);
				xmlBlobKeytoDelete = (BlobKey) xmlEntity.getProperty("blobkey");
			} catch (EntityNotFoundException e) {
				previousExists = false;
			}

			Entity data = new Entity(xmlKey);
			BlobKey xmlBlobKey = fileService.getBlobKey(file);
			respBuilder.appendRespMessage("Storing new XML file.");
			data.setProperty("blobkey", xmlBlobKey);
			dataStore.put(data);

			// Delete previous file if it exists
			try {
				if (previousExists) {
					if (xmlBlobKeytoDelete == null) {
						throw new BlobKeyIsInvalidException();
					}

					respBuilder
							.appendRespMessage("Deleting previous XML file.");
					AppEngineFile filetoDelete = fileService
							.getBlobFile(xmlBlobKeytoDelete);
					fileService.delete(filetoDelete);
				}
			} catch (BlobKeyIsInvalidException e) {

			}
		} catch (TransformerException e) {
			respBuilder
					.appendRespMessage("An error occurred during transformation of DOM Source to stream.");
			return false;
		} catch (IOException e) {
			respBuilder
					.appendRespMessage("Could not create new file in blobstore.");
			return false;
		}

		return true;
	}

	/**
	 * Reads an XML file from server Blobstore
	 * 
	 * @param xmlKey
	 * @param respBuilder
	 * @return xmlFile
	 */
	public static String readXMLFileFromBlobStore(Key xmlKey,
			ResponseBuilder respBuilder) {
		String xmlData = "";

		FileService fileService = FileServiceFactory.getFileService();
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();

		try {
			Entity xmlEntity = dataStore.get(xmlKey);

			BlobKey xmlBlobKey = (BlobKey) xmlEntity.getProperty("blobkey");

			if (xmlBlobKey == null) {
				throw new BlobKeyIsInvalidException();
			}
			AppEngineFile file = fileService.getBlobFile(xmlBlobKey);

			boolean lock = false; // Let other people read at the same time
			FileReadChannel readChannel = fileService.openReadChannel(file,
					lock);

			BufferedReader reader = new BufferedReader(Channels.newReader(
					readChannel, "UTF8"));

			StringBuilder stringBuilder = new StringBuilder();

			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
			}

			readChannel.close();

			xmlData = stringBuilder.toString();
		} catch (EntityNotFoundException e) {
			respBuilder.appendRespMessage("Entity Not Found: "
					+ xmlKey.getName());
		} catch (IOException e) {
			respBuilder
					.appendRespMessage("Could not read XML file from Blobstore.");
		} catch (BlobKeyIsInvalidException e) {
			respBuilder.appendRespMessage("BlobKey does not exist for Entity: "
					+ xmlKey.getName());
		}

		return xmlData;
	}
}
