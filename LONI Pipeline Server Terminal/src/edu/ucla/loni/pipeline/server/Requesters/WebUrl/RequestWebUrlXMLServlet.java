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
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Base64Encoder;
import com.googlecode.gwt.crypto.client.TripleDesCipher;
import com.googlecode.gwt.crypto.gwtx.io.ByteArrayOutputStream;
import com.googlecode.gwt.crypto.gwtx.io.OutputStream;

import edu.ucla.loni.pipeline.client.Requesters.WebUrl.RequestWebUrlXMLService;
import edu.ucla.loni.pipeline.client.Utilities.WebUrlResponseBuilder;
import edu.ucla.loni.pipeline.server.Utilities.DatastoreUtils;
import edu.ucla.loni.pipeline.server.Utilities.ResponseBuilder;

public class RequestWebUrlXMLServlet extends RemoteServiceServlet implements
		RequestWebUrlXMLService {

	private static final long serialVersionUID = 5448261063802349760L;
	private final Key xmlResourceKey, xmlConfigurationKey;
	WebUrlResponseBuilder webUrlresponse = null;

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

		if (webUrlresponse == null) {
			webUrlresponse = new WebUrlResponseBuilder();
		}
		webUrlresponse.reset();

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

			Base64Encoder enc = new Base64Encoder();
			String userpassword = username + ":" + password;

			OutputStream out = new ByteArrayOutputStream();
			enc.encode(userpassword.getBytes(), 0, userpassword.length(), out);
			byte[] buffer = { 0 };
			out.write(buffer);
			new String(buffer);

			// below Authorization was not tested as there were no such web URLs
			// of LONI for testing
			// huc.setRequestProperty("Authorization", "Basic "+
			// encodedAuthorization);

			// Here we check that username and password are "loni" "refresher"
			// for testing
			if (!username.equals("loni") || !password.equals("refresher")) {
				webUrlresponse.setMessage("Authentication error");
				webUrlresponse.setStatus(false);
				return webUrlresponse;
			}

			int status = huc.getResponseCode();
			if (status != 200) {
				throw (new MalformedURLException());
			}
			InputStream in = huc.getInputStream();

			BufferedReader d = new BufferedReader(new InputStreamReader(in));

			String lineRead = null;
			while ((lineRead = d.readLine()) != null) {
				webUrlresponse.appendXml(lineRead);
			}

			if (webUrlresponse.getXml().length() == 0) {
				webUrlresponse
						.setMessage("Empty XML file at target, check URL and try again");
			} else {
				webUrlresponse.setStatus(true);

				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(new InputSource(
						new StringReader(webUrlresponse.getXml())));
				document.getDocumentElement().normalize();
				String rootTag = document.getDocumentElement().getNodeName();

				ResponseBuilder response = new ResponseBuilder();
				if (rootTag.equalsIgnoreCase("LONIConfigurationData")) {
					webUrlresponse.setRootTag("LONIConfigurationData");
					if (DatastoreUtils.writeXMLFileToBlobStore(document,
							xmlConfigurationKey, response) == false) {
						webUrlresponse
								.setMessage("Error in storing Config data on server");
					}
				} else if (rootTag.equalsIgnoreCase("LONIResourceData")) {
					webUrlresponse.setRootTag("LONIResourceData");
					if (DatastoreUtils.writeXMLFileToBlobStore(document,
							xmlResourceKey, response) == false) {
						webUrlresponse
								.setMessage("Error in storing Config data on server");
					}
				} else {
					webUrlresponse
							.setMessage("Invalid file format, check the URL and try again");
				}
			}
		} catch (IOException e) {
			webUrlresponse.setMessage("Error on file retrieval, try again");
		} catch (ParserConfigurationException | SAXException e) {
			webUrlresponse.setMessage("Error in parsing retrieved file");
		} catch (com.googlecode.gwt.crypto.gwtx.io.IOException e) {
			webUrlresponse.setMessage("Authentication error");
		}

		if (webUrlresponse.getMessage().length() != 0) {
			webUrlresponse.setStatus(false);
		}

		return webUrlresponse;
	}
}