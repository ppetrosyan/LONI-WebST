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

package edu.ucla.loni.pipeline.server.Upload.Uploaders;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import edu.ucla.loni.pipeline.server.Utilities.DatastoreUtils;
import edu.ucla.loni.pipeline.server.Utilities.ResponseBuilder;

/**
 * Handles HTTP GET/POST Requests for File Upload
 */
public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 5842494091385532249L;

	private final Key xmlResourceKey, xmlConfigurationKey;

	/**
	 * Constructor
	 */
	public FileUploadServlet() {
		xmlConfigurationKey = KeyFactory.createKey("XMLType",
				"ConfigurationData");
		xmlResourceKey = KeyFactory.createKey("XMLType", "ResourceData");
	}

	/**
	 * Handles HTTP GET Request/Response
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}

	/**
	 * Handles HTTP POST Request/Response
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
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

	/**
	 * Handles Request to Upload File, Builds a Response
	 * 
	 * @param req
	 * @param respBuilder
	 */
	private void handleFileUpload(HttpServletRequest req,
			ResponseBuilder respBuilder) {

		// process only multipart requests
		if (ServletFileUpload.isMultipartContent(req)) {
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload();

			try {
				// Parse the request
				FileItemIterator iter = upload.getItemIterator(req);

				while (iter.hasNext()) {
					FileItemStream item = iter.next();
					handleUploadedFile(item, respBuilder);
				}
			} catch (FileUploadException e) {
				respBuilder
						.appendRespMessage("The file was not uploaded successfully.");
			} catch (IOException e) {
				respBuilder
						.appendRespMessage("The file was not uploaded successfully.");
			}
		} else {
			respBuilder
					.appendRespMessage("Your form of request is not supported by this upload servlet.");
		}
	}

	/**
	 * Determines XML Data type and stores file on server.
	 * 
	 * @param item
	 * @param respBuilder
	 */
	private void handleUploadedFile(FileItemStream item,
			ResponseBuilder respBuilder) {
		try {
			// process only file upload - discard other form item types
			if (item.isFormField()) {
				return;
			}

			// Validate
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(item.openStream());

			// Normalize
			document.getDocumentElement().normalize();

			// Determine Root Tag
			String rootTag = document.getDocumentElement().getNodeName();

			if (rootTag.equalsIgnoreCase("LONIConfigurationData")) {

				// Write to Blobstore
				DatastoreUtils.writeXMLFileToBlobStore(document,
						xmlConfigurationKey, respBuilder);

				// reset respBuilder (no need to have verbose messages because
				// upload was successful)
				respBuilder.resetRespMessage();

				respBuilder
						.appendRespMessage("LONI Configuration Data was uploaded successfully.");
			} else if (rootTag.equalsIgnoreCase("LONIResourceData")) {

				// Write to Blobstore
				DatastoreUtils.writeXMLFileToBlobStore(document,
						xmlResourceKey, respBuilder);

				// reset respBuilder (no need to have verbose messages because
				// upload was successful)
				respBuilder.resetRespMessage();

				respBuilder
						.appendRespMessage("LONI Resource Data was uploaded successfully.");
			} else {
				respBuilder
						.appendRespMessage("ERROR: Please upload either LONI Configuration or Resource Data.");
			}
		} catch (ParserConfigurationException e) {
			respBuilder
					.appendRespMessage("ERROR: File uploaded is not a valid XML file. Please try again.");
		} catch (SAXException e) {
			respBuilder
					.appendRespMessage("ERROR: File uploaded is not a valid XML file. Please try again.");
		} catch (IOException e) {
			respBuilder
					.appendRespMessage("ERROR: Could not parse file.  Please upload a text-based XML file.");
		}
	}
}