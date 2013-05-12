package edu.ucla.loni.pipeline.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 5842494091385532249L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, final HttpServletResponse resp)
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
						item.getInputStream().reset();

						// Get a file service
						FileService fileService = FileServiceFactory.getFileService();

						// Create a new Blob file with mime-type "text/plain"
						AppEngineFile file = fileService.createNewBlobFile("text/plain");

						// Open a channel to write to it
						boolean lock = true;
						FileWriteChannel writeChannel = fileService.openWriteChannel(file, lock);

						// Different standard Java ways of writing to the channel
						// are possible. Here we use a PrintWriter:
						PrintWriter out = new PrintWriter(Channels.newWriter(writeChannel, "UTF8"));
						out.println("test...");

						// Close without finalizing and save the file path for writing later
						out.close();
						
						// Now finalize
						writeChannel.closeFinally();
						
						String path = file.getFullPath();

						// Write more to the file in a separate request:
						file = new AppEngineFile(path);

						// Later, read from the file using the file API
						lock = false; // Let other people read at the same time
						FileReadChannel readChannel = fileService.openReadChannel(file, false);

						// Again, different standard Java ways of reading from the channel.
						BufferedReader reader =
								new BufferedReader(Channels.newReader(readChannel, "UTF8"));
						String line = reader.readLine();

						readChannel.close();

						// Now read from the file using the Blobstore API
						/*BlobKey blobKey = fileService.getBlobKey(file);
						BlobstoreService blobStoreService = BlobstoreServiceFactory.getBlobstoreService();
						String segment = new String(blobStoreService.fetchData(blobKey, 30, 40));*/

						resp.setStatus(HttpServletResponse.SC_OK);
						resp.getWriter().print("File Uploaded Successfully, detected Configuration Data." + line);
						resp.flushBuffer();
					}
					else if(rootTag.equalsIgnoreCase("LONISimulatedData")){
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