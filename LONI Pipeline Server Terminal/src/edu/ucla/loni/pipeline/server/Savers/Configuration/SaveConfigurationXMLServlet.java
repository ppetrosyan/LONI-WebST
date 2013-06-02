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
import java.io.StringWriter;
import java.nio.channels.Channels;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

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

import edu.ucla.loni.pipeline.client.Savers.Configuration.SaveConfigurationXMLService;

public class SaveConfigurationXMLServlet extends RemoteServiceServlet implements
		SaveConfigurationXMLService {

	private static final long serialVersionUID = -3691205431099234883L;
	private final Key xmlConfigurationKey;

	public SaveConfigurationXMLServlet() {
		xmlConfigurationKey = KeyFactory.createKey("XMLType",
				"ConfigurationData");
	}

	@Override
	public boolean setXMLData(String xmlData) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();

		try {
			// Validate
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlData);

			doc.getDocumentElement().normalize();

			// Determine Root Tag
			String rootTag = doc.getDocumentElement().getNodeName();

			if (rootTag.equalsIgnoreCase("LONIConfigurationData")) {
				// Get a file service
				FileService fileService = FileServiceFactory.getFileService();

				// Create a new Blob file with mime-type "text/plain"
				AppEngineFile file = fileService
						.createNewBlobFile("text/plain");

				// Open a channel to write to it
				boolean lock = true;
				FileWriteChannel writeChannel = fileService.openWriteChannel(
						file, lock);

				DOMSource domSource = new DOMSource(doc);

				StringWriter writer = new StringWriter();
				StreamResult streamResult = new StreamResult(writer);

				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.transform(domSource, streamResult);

				// Different standard Java ways of writing to the channel
				// are possible. Here we use a PrintWriter:
				PrintWriter out = new PrintWriter(Channels.newWriter(
						writeChannel, "UTF8"));
				out.println(writer.toString());

				// Close without finalizing and save the file path for writing
				// later
				out.close();

				// Now finalize
				writeChannel.closeFinally();

				// Add Path to Hashtable
				Entity configurationData = new Entity(xmlConfigurationKey);
				configurationData.setProperty("tag", file.getFullPath());
				dataStore.put(configurationData);
			} else {
				return false;
			}
		} catch (TransformerException | ParserConfigurationException
				| SAXException | IllegalStateException | IOException e) {
			return false;
		}

		return true;
	}
}