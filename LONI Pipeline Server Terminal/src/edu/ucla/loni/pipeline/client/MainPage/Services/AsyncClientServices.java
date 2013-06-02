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

package edu.ucla.loni.pipeline.client.MainPage.Services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import edu.ucla.loni.pipeline.client.Requesters.Configuration.RequestConfigurationXMLService;
import edu.ucla.loni.pipeline.client.Requesters.Configuration.RequestConfigurationXMLServiceAsync;
import edu.ucla.loni.pipeline.client.Requesters.ResourceUsage.RequestResourceXMLService;
import edu.ucla.loni.pipeline.client.Requesters.ResourceUsage.RequestResourceXMLServiceAsync;
import edu.ucla.loni.pipeline.client.Requesters.WebUrl.RequestWebUrlXMLService;
import edu.ucla.loni.pipeline.client.Requesters.WebUrl.RequestWebUrlXMLServiceAsync;
import edu.ucla.loni.pipeline.client.Savers.Configuration.SaveConfigurationXMLService;
import edu.ucla.loni.pipeline.client.Savers.Configuration.SaveConfigurationXMLServiceAsync;

public class AsyncClientServices {

	// Client-side Services
	private final String reqResourceXML, reqConfigurationXML,
			saveConfigurationXML, reqwebUrlXML;

	public RequestResourceXMLServiceAsync reqResourceXMLService;
	public RequestConfigurationXMLServiceAsync reqConfigurationXMLService;
	public SaveConfigurationXMLServiceAsync saveConfigurationXMLService;
	public RequestWebUrlXMLServiceAsync reqwebUrlXMLService;

	public AsyncClientServices() {
		reqResourceXML = "RequestResourceXMLServlet";
		reqConfigurationXML = "RequestConfigurationXMLServlet";
		saveConfigurationXML = "SaveConfigurationXMLServlet";
		reqwebUrlXML = "RequestWebUrlXMLServlet";

		reqResourceXMLService = GWT.create(RequestResourceXMLService.class);
		reqConfigurationXMLService = GWT
				.create(RequestConfigurationXMLService.class);
		saveConfigurationXMLService = GWT
				.create(SaveConfigurationXMLService.class);
		reqwebUrlXMLService = GWT.create(RequestWebUrlXMLService.class);

		defEntryPoint();
	}

	public void defEntryPoint() {
		((ServiceDefTarget) reqResourceXMLService)
				.setServiceEntryPoint(reqResourceXML);
		((ServiceDefTarget) reqConfigurationXMLService)
				.setServiceEntryPoint(reqConfigurationXML);
		((ServiceDefTarget) saveConfigurationXMLService)
				.setServiceEntryPoint(saveConfigurationXML);
		((ServiceDefTarget) reqwebUrlXMLService)
				.setServiceEntryPoint(reqwebUrlXML);
	}
}
