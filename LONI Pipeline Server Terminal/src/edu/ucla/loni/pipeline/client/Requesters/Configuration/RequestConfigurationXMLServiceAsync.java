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

package edu.ucla.loni.pipeline.client.Requesters.Configuration;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Service used to Retrieve Configuration Data from server
 * 
 * @author Jared
 */
public interface RequestConfigurationXMLServiceAsync {

	/**
	 * Retrieves Configuration Data from server
	 * 
	 * @param callback
	 */
	public void getXMLData(AsyncCallback<String> callback);
}
