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

package edu.ucla.loni.pipeline.server.Requesters.ResourceUsage;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.ucla.loni.pipeline.client.Requesters.ResourceUsage.RequestResourceXMLService;
import edu.ucla.loni.pipeline.server.Utilities.DatastoreUtils;
import edu.ucla.loni.pipeline.server.Utilities.ResponseBuilder;

/**
 * Retrieves Resource Data from server Blobstore
 */
public class RequestResourceXMLServlet extends RemoteServiceServlet implements
		RequestResourceXMLService {

	private static final long serialVersionUID = 5448261063802349760L;
	private final Key xmlResourceKey;

	/**
	 * Constructor
	 */
	public RequestResourceXMLServlet() {
		xmlResourceKey = KeyFactory.createKey("XMLType", "ResourceData");
	}

	/**
	 * Retrieves Resource Data from server Blobstore
	 * 
	 * @see edu.ucla.loni.pipeline.client.Requesters.ResourceUsage.RequestResourceXMLService#getXMLData()
	 */
	@Override
	public String getXMLData() {
		String xmlData = "";

		ResponseBuilder respBuilder = new ResponseBuilder();
		xmlData = DatastoreUtils.readXMLFileFromBlobStore(xmlResourceKey,
				respBuilder);

		// Debug
		System.out.println(respBuilder.getRespMessage());

		return xmlData;
	}
}