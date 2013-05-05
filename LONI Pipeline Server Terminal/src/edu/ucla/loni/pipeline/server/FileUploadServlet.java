package edu.ucla.loni.pipeline.server;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 8683470156282697544L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// process only multipart requests
        /*if (ServletFileUpload.isMultipartContent(req)) {
            
            XMLSerializer xmlSerializer = new XMLSerializer();
			JSON json = xmlSerializer.readFromStream(req.getInputStream());
			
			resp.setContentType("text/html");
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().print(json.toString(2));
            resp.flushBuffer();
        } 
        else {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "Request contents type is not supported by the servlet.");
        }*/
		
		if (ServletFileUpload.isMultipartContent(req)) {

            ServletInputStream sis = req.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(sis));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();

            String xml = stringBuilder.toString();

            resp.setContentType("text/html");
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().print(xml);
            resp.flushBuffer();

        } else {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "Request contents type is not supported by the servlet.");
        }
	}
}