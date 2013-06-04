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

package edu.ucla.loni.pipeline.client.Utilities;

import java.io.Serializable;

public class WebUrlResponseBuilder implements Serializable {

	private static final long serialVersionUID = 7102038232914698194L;
	private String xml;
	private String message;
	private boolean status;
	private String rootTag;

	public WebUrlResponseBuilder() {
		xml = new String();
		message = new String();
		rootTag = new String();
		
		reset();
	}
	
	public void reset() {
		xml = "";
		message = "";
		rootTag = "";
		status = false;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void appendXml(String xml) {
		this.xml += xml;
	}
	
	public void appendMessage(String message) {
		if (this.message.length() != 0)
			this.message += "\n";
		this.message += message;
	}
	
	public void setRootTag(String rootTag) {
		this.rootTag = rootTag;
	}
	
	public String getXml() {
		return xml;
	}

	public String getMessage() {
		return message;
	}

	public boolean getStatus() {
		return status;
	}
	
	public String getRootTag() {
		return rootTag;
	}
}
