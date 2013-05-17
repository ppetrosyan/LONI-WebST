package edu.ucla.loni.pipeline.server.Upload.Uploaders;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.channels.Channels;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
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

		// process only multipart requests
		if (ServletFileUpload.isMultipartContent(req)) {

			DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

			try {				
				// Create a factory for disk-based file items
				FileItemFactory factory = new DiskFileItemFactory();

				// Create a new file upload handler
				ServletFileUpload upload = new ServletFileUpload(factory);

				// Parse the request
				List<FileItem> items = upload.parseRequest(req);
				for (FileItem item : items) {
					// process only file upload - discard other form item types
					if (item.isFormField()) continue;

					// Validate
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(item.getInputStream());

					doc.getDocumentElement().normalize();

					// Determine Root Tag
					String rootTag = doc.getDocumentElement().getNodeName();

					if(rootTag.equalsIgnoreCase("LONIConfigurationData")) {
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
						Entity configurationData = new Entity(xmlConfigurationKey);
						configurationData.setProperty("tag", file.getFullPath());
						dataStore.put(configurationData);

						resp.setStatus(HttpServletResponse.SC_OK);
						resp.getWriter().print("File Uploaded Successfully, detected Configuration Data.");
						resp.flushBuffer();
					}
					else if(rootTag.equalsIgnoreCase("LONIResourceData")){
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
						Entity resourceData = new Entity(xmlResourceKey);
						resourceData.setProperty("tag", file.getFullPath());
						dataStore.put(resourceData);

						resp.setStatus(HttpServletResponse.SC_OK);
						resp.getWriter().print("File Uploaded Successfully, detected Resource Data.");
						resp.flushBuffer();
					}
					else {
						resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
								"The RootTag of this XML is incorrect.");
					}
				}
			} 
			catch (TransformerException | FileUploadException | ParserConfigurationException | SAXException e) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"An error occurred while creating the file : " + e.getMessage());
			}
		} else {
			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
					"Request contents type is not supported by the servlet.");
		}
	}
}