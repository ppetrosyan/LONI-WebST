package edu.ucla.loni.pipeline.server.Upload.Uploaders;

import java.io.IOException;

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

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import edu.ucla.loni.pipeline.server.Utilities.DatastoreUtils;
import edu.ucla.loni.pipeline.server.Utilities.ResponseBuilder;

public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 5842494091385532249L;
	
	private Key xmlResourceKey, xmlConfigurationKey;

	public FileUploadServlet() {
		xmlConfigurationKey = KeyFactory.createKey("XMLType", "ConfigurationData");
		xmlResourceKey = KeyFactory.createKey("XMLType", "ResourceData");
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
		ResponseBuilder respBuilder = new ResponseBuilder();

		// handle file upload
		handleFileUpload(req, respBuilder);
		
		// get response message
		String message = respBuilder.getRespMessage();
		
		resp.getWriter().print(message);

		// flush response buffer
		resp.flushBuffer();
	}

	

	private void handleFileUpload(HttpServletRequest req, ResponseBuilder respBuilder) {

		// process only multipart requests
		if (ServletFileUpload.isMultipartContent(req)) {
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload();
			
			try {								
				// Parse the request
				FileItemIterator iter = upload.getItemIterator(req);
				
				while(iter.hasNext()) {
					FileItemStream item = iter.next();
					handleUploadedFile(item, respBuilder);
				}
			} 
			catch (FileUploadException e) {
				respBuilder.appendRespMessage("The file was not uploaded successfully.\n");
			} 
			catch (IOException e) {
				respBuilder.appendRespMessage("The file was not uploaded successfully.\n");
			}	
		} 
		else {
			respBuilder.appendRespMessage("Your form of request is not supported by this upload servlet.\n");
		}
	}

	private void handleUploadedFile(FileItemStream item, ResponseBuilder respBuilder) {
		try {
			// process only file upload - discard other form item types
			if (item.isFormField())
				return;

			// Validate
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(item.openStream());

			// Normalize
			document.getDocumentElement().normalize();

			// Determine Root Tag
			String rootTag = document.getDocumentElement().getNodeName();

			if(rootTag.equalsIgnoreCase("LONIConfigurationData")) {

				// Write to Blobstore
				DatastoreUtils.writeXMLFileToBlobStore(document, xmlConfigurationKey, respBuilder);

				respBuilder.appendRespMessage("File Uploaded Successfully, detected Configuration Data.\n");
			}
			else if(rootTag.equalsIgnoreCase("LONIResourceData")){

				// Write to Blobstore
				DatastoreUtils.writeXMLFileToBlobStore(document, xmlResourceKey, respBuilder);

				respBuilder.appendRespMessage("File Uploaded Successfully, detected Resource Data.");
			}
			else {
				respBuilder.appendRespMessage("The RootTag of this XML is incorrect.\n");
			}
		}
		catch (ParserConfigurationException e) {
			respBuilder.appendRespMessage("File uploaded is not a valid XML file.\n");
		}
		catch (SAXException e) {
			respBuilder.appendRespMessage("File uploaded is not a valid XML file.\n");
		} 
		catch (IOException e) {
			respBuilder.appendRespMessage("Could not parse inputstream.\n");
		}
	}
}