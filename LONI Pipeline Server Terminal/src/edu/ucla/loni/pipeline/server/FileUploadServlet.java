package edu.ucla.loni.pipeline.server;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 8683470156282697544L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			ServletFileUpload upload = new ServletFileUpload();
			resp.setContentType("text/json");

			FileItemIterator iterator = upload.getItemIterator(req);
			while (iterator.hasNext()) {
				FileItemStream item = iterator.next();
				InputStream stream = item.openStream();

				XMLSerializer xmlSerializer = new XMLSerializer();
				JSON json = xmlSerializer.readFromStream(stream);

				resp.setStatus(HttpServletResponse.SC_OK);
				resp.getWriter().print(json.toString(2));
				resp.flushBuffer();
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}
}