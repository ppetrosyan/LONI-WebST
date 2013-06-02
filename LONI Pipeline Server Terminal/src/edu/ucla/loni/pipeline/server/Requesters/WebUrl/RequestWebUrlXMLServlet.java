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

package edu.ucla.loni.pipeline.server.Requesters.WebUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.gwt.crypto.client.TripleDesCipher;

import edu.ucla.loni.pipeline.client.Requesters.WebUrl.RequestWebUrlXMLService;
import edu.ucla.loni.pipeline.client.Utilities.WebUrlResponseBuilder;
import edu.ucla.loni.pipeline.server.Utilities.DatastoreUtils;
import edu.ucla.loni.pipeline.server.Utilities.ResponseBuilder;

public class RequestWebUrlXMLServlet extends RemoteServiceServlet implements
		RequestWebUrlXMLService {

	private static final long serialVersionUID = 5448261063802349760L;
	private final Key xmlResourceKey, xmlConfigurationKey;
	WebUrlResponseBuilder webUrlresponse;

	public RequestWebUrlXMLServlet() {
		xmlConfigurationKey = KeyFactory.createKey("XMLType",
				"ConfigurationData");
		xmlResourceKey = KeyFactory.createKey("XMLType", "ResourceData");
		webUrlresponse = new WebUrlResponseBuilder();
	}

	@Override
	public WebUrlResponseBuilder getXML(String wE, String uE, String pE,
			byte[] key) {

		TripleDesCipher cipher = new TripleDesCipher();
		cipher.setKey(key);
		String webUrl = "", username = "", password = "";
		webUrlresponse.setMessage("");

		try {
			webUrl = cipher.decrypt(wE);
			username = cipher.decrypt(uE);
			password = cipher.decrypt(pE);
		} catch (Exception e) {
			webUrlresponse.setMessage("Error on decryption");
		}

		if ((webUrl.length() == 0) || (username.length() == 0)
				|| (password.length() == 0)) {
			webUrlresponse
					.setMessage("Missing web URL, username or password sent to server");

		}

		if (webUrlresponse.getMessage().length() != 0) {
			webUrlresponse.setStatus(false);
			return webUrlresponse;
		}

		try {
			URL u = new URL(webUrl);
			HttpURLConnection huc = (HttpURLConnection) u.openConnection();
			huc.setRequestMethod("GET");
			huc.setDoOutput(false);
			int status = huc.getResponseCode();
			if (status != 200) {
				throw (new MalformedURLException());
			}
			InputStream in = huc.getInputStream();

			BufferedReader d = new BufferedReader(new InputStreamReader(in));

			webUrlresponse.setXml(d.readLine());

			if (webUrlresponse.getXml().length() == 0) {
				webUrlresponse
						.setMessage("Empty XML file at target, check URL and try again");
				webUrlresponse.setStatus(false);
			} else {
				webUrlresponse.setStatus(true);

				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(in);
				document.getDocumentElement().normalize();
				String rootTag = document.getDocumentElement().getNodeName();

				ResponseBuilder response = new ResponseBuilder();

				if (rootTag.equalsIgnoreCase("LONIConfigurationData")) {
					DatastoreUtils.writeXMLFileToBlobStore(document,
							xmlConfigurationKey, response);
					webUrlresponse.setMessage(response.getRespMessage());
				} else if (rootTag.equalsIgnoreCase("LONIResourceData")) {
					DatastoreUtils.writeXMLFileToBlobStore(document,
							xmlResourceKey, response);
					webUrlresponse.setMessage(response.getRespMessage());
				} else {
					webUrlresponse
							.setMessage("Invalid file format, check the URL and try again");
					webUrlresponse.setStatus(false);
				}
			}
		} catch (IOException e) {
			webUrlresponse.setMessage("Error on file retrieval, try again");
			webUrlresponse.setStatus(false);
		} catch (ParserConfigurationException | SAXException e) {
			webUrlresponse.setMessage("Error in parsing retrieved file");
			webUrlresponse.setStatus(false);
		}

		return webUrlresponse;
	}
}