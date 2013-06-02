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

package edu.ucla.loni.pipeline.server.Download.Downloaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.channels.Channels;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileReadChannel;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;

public class FileDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 5842494091385532249L;
	private final Key csvResourceKey;

	public FileDownloadServlet() {
		csvResourceKey = KeyFactory.createKey("CSVType", "ResourceData");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String xmlData = "";

		FileService fileService = FileServiceFactory.getFileService();
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();

		try {
			Entity ResourceData = dataStore.get(csvResourceKey);

			String ResourceTag = (String) ResourceData.getProperty("tag");

			AppEngineFile file = new AppEngineFile(ResourceTag);

			// Later, read from the file using the file API
			boolean lock = false; // Let other people read at the same time
			FileReadChannel readChannel = fileService.openReadChannel(file,
					lock);

			// Again, different standard Java ways of reading from the channel.
			BufferedReader reader = new BufferedReader(Channels.newReader(
					readChannel, "UTF8"));

			StringBuilder stringBuilder = new StringBuilder();

			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
			}

			readChannel.close();

			xmlData = stringBuilder.toString();
		} catch (EntityNotFoundException | IOException e) {
			System.out.println("Entity Not Found.");
		}

		if (xmlData.isEmpty()) {
			xmlData = "{Empty}: No Information";
		}

		resp.setHeader("Content-Type", "text/csv");
		resp.setHeader("Content-Disposition",
				"inline; filename=\"LONIUsersOnline.csv\"");
		resp.setStatus(HttpServletResponse.SC_OK);

		OutputStream outputStream = resp.getOutputStream();
		PrintWriter printWriter = new PrintWriter(outputStream);
		printWriter.write(xmlData);
		printWriter.close();
		outputStream.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}
}