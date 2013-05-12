package edu.ucla.loni.pipeline.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileReadChannel;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;

public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 5842494091385532249L;
	private Hashtable<XMLType, String> xmlPathTable;

	private enum XMLType {
		CONFIGURATION, SIMULATEDDATA
	}

	public FileUploadServlet() {
		xmlPathTable = new Hashtable<XMLType, String>();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String xmlFile = req.getParameter("xmlfile");

		if(xmlFile.equalsIgnoreCase("Configuration")) {

			if(xmlPathTable.contains(XMLType.CONFIGURATION)) {
				FileService fileService = FileServiceFactory.getFileService();

				// Get File from Blobstore
				AppEngineFile file = new AppEngineFile(xmlPathTable.get(XMLType.CONFIGURATION));

				// Later, read from the file using the file API
				boolean lock = false; // Let other people read at the same time
				FileReadChannel readChannel = fileService.openReadChannel(file, lock);

				// Again, different standard Java ways of reading from the channel.
				BufferedReader reader =
						new BufferedReader(Channels.newReader(readChannel, "UTF8"));

				StringBuilder stringBuilder = new StringBuilder();

				String line;
				while((line = reader.readLine()) != null)
					stringBuilder.append(line);

				readChannel.close();

				resp.setStatus(HttpServletResponse.SC_OK);
				resp.getWriter().print(stringBuilder.toString());
				resp.flushBuffer();
			}
			else {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND,
						"XML File not found");
			}

		}
		else if(xmlFile.equalsIgnoreCase("SimulatedData")) {

			if(xmlPathTable.contains(XMLType.SIMULATEDDATA)) {
				FileService fileService = FileServiceFactory.getFileService();

				// Get File from Blobstore
				AppEngineFile file = new AppEngineFile(xmlPathTable.get(XMLType.SIMULATEDDATA));

				// Later, read from the file using the file API
				boolean lock = false; // Let other people read at the same time
				FileReadChannel readChannel = fileService.openReadChannel(file, false);

				// Again, different standard Java ways of reading from the channel.
				BufferedReader reader =
						new BufferedReader(Channels.newReader(readChannel, "UTF8"));

				StringBuilder stringBuilder = new StringBuilder();

				String line;
				while((line = reader.readLine()) != null)
					stringBuilder.append(line);

				readChannel.close();

				resp.setStatus(HttpServletResponse.SC_OK);
				resp.getWriter().print(stringBuilder.toString());
				resp.flushBuffer();
			}
			else {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND,
						"XML File not found");
			}

		}
		else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"XML File Type not supported");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// process only multipart requests
		if (ServletFileUpload.isMultipartContent(req)) {

			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Parse the request
			try {
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
						xmlPathTable.put(XMLType.CONFIGURATION, file.getFullPath());

						resp.setStatus(HttpServletResponse.SC_OK);
						resp.getWriter().print("File Uploaded Successfully, detected Configuration Data.");
						resp.flushBuffer();
					}
					else if(rootTag.equalsIgnoreCase("LONISimulatedData")){
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
						xmlPathTable.put(XMLType.SIMULATEDDATA, file.getFullPath());

						resp.setStatus(HttpServletResponse.SC_OK);
						resp.getWriter().print("File Uploaded Successfully, detected Simulated Data.");
						resp.flushBuffer();
					}
					else {
						resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
								"The RootTag of this XML is incorrect.");
					}
				}
			} catch (Exception e) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"An error occurred while creating the file : " + e.getMessage());
			}

		} else {
			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
					"Request contents type is not supported by the servlet.");
		}
	}
}