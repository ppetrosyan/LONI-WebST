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

package edu.ucla.loni.pipeline.server.Requesters.Depreciated;

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

import edu.ucla.loni.pipeline.client.Requesters.Depreciated.XMLDataService;

public class XMLDataServlet extends RemoteServiceServlet implements
		XMLDataService {

	private static final long serialVersionUID = 5448261063802349760L;
	private final Key xmlResourceKey, xmlConfigurationKey;

	public XMLDataServlet() {
		xmlConfigurationKey = KeyFactory.createKey("XMLType",
				"ConfigurationData");
		xmlResourceKey = KeyFactory.createKey("XMLType", "ResourceData");
	}

	@Override
	public String getXMLData(String xmlDataType) {

		String xmlData = "";

		FileService fileService = FileServiceFactory.getFileService();
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();

		try {
			if (xmlDataType.equalsIgnoreCase("ConfigurationData")) {

				Entity configurationData = dataStore.get(xmlConfigurationKey);

				String configurationTag = (String) configurationData
						.getProperty("tag");

				AppEngineFile file = new AppEngineFile(configurationTag);

				// Later, read from the file using the file API
				boolean lock = false; // Let other people read at the same time
				FileReadChannel readChannel = fileService.openReadChannel(file,
						lock);

				// Again, different standard Java ways of reading from the
				// channel.
				BufferedReader reader = new BufferedReader(Channels.newReader(
						readChannel, "UTF8"));

				StringBuilder stringBuilder = new StringBuilder();

				String line;
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
				}

				readChannel.close();

				xmlData = stringBuilder.toString();
			} else if (xmlDataType.equalsIgnoreCase("ResourceData")) {
				Entity simulatedData = dataStore.get(xmlResourceKey);
				String simulatedTag = (String) simulatedData.getProperty("tag");

				AppEngineFile file = new AppEngineFile(simulatedTag);

				// Later, read from the file using the file API
				boolean lock = false; // Let other people read at the same time
				FileReadChannel readChannel = fileService.openReadChannel(file,
						lock);

				// Again, different standard Java ways of reading from the
				// channel.
				BufferedReader reader = new BufferedReader(Channels.newReader(
						readChannel, "UTF8"));

				StringBuilder stringBuilder = new StringBuilder();

				String line;
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
				}

				readChannel.close();

				xmlData = stringBuilder.toString();
			} else {
				/** Do Nothing */
			}
		} catch (EntityNotFoundException | IOException e) {
			System.out.println("Entity Not Found.");
		}

		return xmlData;
	}
}