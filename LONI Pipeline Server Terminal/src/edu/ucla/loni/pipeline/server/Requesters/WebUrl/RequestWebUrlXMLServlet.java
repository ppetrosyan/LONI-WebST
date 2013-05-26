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

import edu.ucla.loni.pipeline.client.Requesters.WebUrl.RequestWebUrlXMLService;
import edu.ucla.loni.pipeline.server.Utilities.DatastoreUtils;
import edu.ucla.loni.pipeline.server.Utilities.WebUrlResponseBuilder;

public class RequestWebUrlXMLServlet extends RemoteServiceServlet implements RequestWebUrlXMLService {

	private static final long serialVersionUID = 5448261063802349760L;
	private Key xmlResourceKey, xmlConfigurationKey;
	WebUrlResponseBuilder response;

	public RequestWebUrlXMLServlet() {
		xmlConfigurationKey = KeyFactory.createKey("XMLType", "ConfigurationData");
		xmlResourceKey = KeyFactory.createKey("XMLType", "ResourceData");
		response = new WebUrlResponseBuilder();
	}

	@Override
	public String getXML(String url) {
		String xml = null;
		
		try {
			  	URL u = new URL(url);
			  	HttpURLConnection huc = (HttpURLConnection) u.openConnection();
			  	huc.setRequestMethod("GET");
			  	huc.setDoOutput(false);
			  	int status = huc.getResponseCode();
			  	if (status != 200)
			  		throw (new MalformedURLException());
			  	InputStream in = huc.getInputStream();
			  	
			  	BufferedReader d = new BufferedReader(new InputStreamReader(in));
			  	xml = d.readLine();
			  	if(xml.length() == 0)
			  		return null;
			  	
			  	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			  	DocumentBuilder builder = factory.newDocumentBuilder();
			  	Document document = builder.parse(in);
				document.getDocumentElement().normalize();

				String rootTag = document.getDocumentElement().getNodeName();
				if(rootTag.equalsIgnoreCase("LONIConfigurationData")) {
					//DatastoreUtils.writeXMLFileToBlobStore(document, xmlConfigurationKey, null);//response);
				}
				else if(rootTag.equalsIgnoreCase("LONIResourceData")){
					//DatastoreUtils.writeXMLFileToBlobStore(document, xmlResourceKey, null);//response);
				}
				else {
					//response.appendRespMessage("Invalid file format, incorrect root tags founds");
					//xml = null;
				}
		} 
		catch (IOException e) {
			response.appendRespMessage("Error on file retrieval, try again");
			xml = null;
		} 
		catch (ParserConfigurationException | SAXException e) {
			response.appendRespMessage("Error in parsing retrieved file");
			//xml = null;
		}

		//if (xml != null)
			//response.setXml(xml);
		return xml; //response;
	}
}