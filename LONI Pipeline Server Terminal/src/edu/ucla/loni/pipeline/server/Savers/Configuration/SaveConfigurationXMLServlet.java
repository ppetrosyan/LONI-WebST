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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.ucla.loni.pipeline.client.Savers.Configuration.SaveConfigurationXMLService;
import edu.ucla.loni.pipeline.server.Utilities.DatastoreUtils;
import edu.ucla.loni.pipeline.server.Utilities.ResponseBuilder;

/**
 * Stores Configuration data in server Blobstore
 */
public class SaveConfigurationXMLServlet extends RemoteServiceServlet implements
		SaveConfigurationXMLService {

	private static final long serialVersionUID = -3691205431099234883L;
	private final Key xmlConfigurationKey;

	/**
	 * Constructor
	 */
	public SaveConfigurationXMLServlet() {
		xmlConfigurationKey = KeyFactory.createKey("XMLType",
				"ConfigurationData");
	}

	/**
	 * Stores Configuration data in server Blobstore
	 * 
	 * @see edu.ucla.loni.pipeline.client.Savers.Configuration.SaveConfigurationXMLService#setXMLData(java.lang.String)
	 */
	@Override
	public boolean setXMLData(String xmlData) {
		DatastoreServiceFactory.getDatastoreService();

		try {
			// Validate
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(xmlData);

			// Normalize
			document.getDocumentElement().normalize();

			// Determine Root Tag
			String rootTag = document.getDocumentElement().getNodeName();

			if (rootTag.equalsIgnoreCase("LONIConfigurationData")) {

				ResponseBuilder respBuilder = new ResponseBuilder();

				// Write to Blobstore
				DatastoreUtils.writeXMLFileToBlobStore(document,
						xmlConfigurationKey, respBuilder);

				// Debug
				System.out.println(respBuilder.getRespMessage());

			} else {
				return false;
			}
		} catch (ParserConfigurationException | SAXException
				| IllegalStateException | IOException e) {
			return false;
		}

		return true;
	}
}