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

package edu.ucla.loni.pipeline.client.MainPage.Preferences.Packages;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PackagesRecord extends ListGridRecord {

	public PackagesRecord() {
	}

	public PackagesRecord(String package_name, String version, String location,
			String variables, String sources) {
		setPackageName(package_name);
		setVersion(version);
		setLocation(location);
		setVariables(variables);
		setSources(sources);

	}

	public void setPackageName(String user_name) {
		setAttribute("package_name", user_name);
	}

	public String getPackageName() {
		return getAttributeAsString("package_name");
	}

	public void setVersion(String version) {
		setAttribute("version", version);
	}

	public String getVersion() {
		return getAttributeAsString("verison");
	}

	public void setLocation(String location) {
		setAttribute("location", location);
	}

	public String getLocation() {
		return getAttributeAsString("location");
	}

	public void setVariables(String variables) {
		setAttribute("variables", variables);
	}

	public String getVariables() {
		return getAttributeAsString("Version");
	}

	public void setSources(String sources) {
		setAttribute("sources", sources);
	}

	public String getSources() {
		return getAttributeAsString("sources");
	}
}