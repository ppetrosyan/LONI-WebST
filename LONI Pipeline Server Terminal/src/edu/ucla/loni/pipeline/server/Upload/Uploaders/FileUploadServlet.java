package edu.ucla.loni.pipeline.server.Upload.Uploaders;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.channels.Channels;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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

public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 5842494091385532249L;
	private Key xmlResourceKey, xmlConfigurationKey;
	private StringBuilder respMessage;
	private int respMessageCounter;

	public FileUploadServlet() {
		xmlConfigurationKey = KeyFactory.createKey("XMLType", "ConfigurationData");
		xmlResourceKey = KeyFactory.createKey("XMLType", "ResourceData");
		respMessage = new StringBuilder();
		respMessageCounter = 1;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// set response status
		resp.setStatus(HttpServletResponse.SC_OK);
		
		// reset response message
		resetRespMessage();

		// handle file upload
		handleFileUpload(req);
		
		// get response message
		String message = getRespMessage();
		
		resp.getWriter().print(message);

		// flush response buffer
		resp.flushBuffer();
	}

	private String getRespMessage() {
		return respMessage.toString();
	}

	private void resetRespMessage() {
		respMessageCounter = 1;
		
		respMessage.setLength(0);
		
		respMessage.append("Message(s) from Server:\n");
	}
	
	private void appendRespMessage(String message) {
		respMessage.append(respMessageCounter + ")  " + message);
		respMessageCounter++;
	}

	private void handleFileUpload(HttpServletRequest req) {

		// process only multipart requests
		if (ServletFileUpload.isMultipartContent(req)) {
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload();
			
			try {								
				// Parse the request
				FileItemIterator iter = upload.getItemIterator(req);
				
				while(iter.hasNext()) {
					FileItemStream item = iter.next();
					handleUploadedFile(item, respMessage);
				}
			} 
			catch (FileUploadException e) {
				appendRespMessage("The file was not uploaded successfully.\n");
			} 
			catch (IOException e) {
				appendRespMessage("The file was not uploaded successfully.\n");
			}	
		} 
		else {
			appendRespMessage("Your form of request is not supported by this upload servlet.\n");
		}
	}

	private void handleUploadedFile(FileItemStream item, StringBuilder respMessage) {
		try {
			// process only file upload - discard other form item types
			if (item.isFormField())
				return;

			// Validate
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(item.openStream());

			// Normalize
			doc.getDocumentElement().normalize();

			// Determine Root Tag
			String rootTag = doc.getDocumentElement().getNodeName();

			if(rootTag.equalsIgnoreCase("LONIConfigurationData")) {

				// Write to Blobstore
				writeFileToBlobStore(doc, xmlConfigurationKey, respMessage);

				appendRespMessage("File Uploaded Successfully, detected Configuration Data.\n");
			}
			else if(rootTag.equalsIgnoreCase("LONIResourceData")){

				// Write to Blobstore
				writeFileToBlobStore(doc, xmlResourceKey, respMessage);

				appendRespMessage("File Uploaded Successfully, detected Resource Data.");
			}
			else {
				appendRespMessage("The RootTag of this XML is incorrect.\n");
			}
		}
		catch (ParserConfigurationException e) {
			appendRespMessage("File uploaded is not a valid XML file.\n");
		}
		catch (SAXException e) {
			appendRespMessage("File uploaded is not a valid XML file.\n");
		} 
		catch (IOException e) {
			appendRespMessage("Could not parse inputstream.\n");
		}
	}

	private void writeFileToBlobStore(Document doc, Key xmlKey, StringBuilder respMessage) {
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

			DOMSource domSource = new DOMSource(doc);

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
			appendRespMessage("An error occurred during transformation of DOM Source to stream.\n");
		} 
		catch (IOException e) {
			appendRespMessage("Could not create new file in blobstore.");
		}
	}
}