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

package edu.ucla.loni.pipeline.server.Utilities;

/**
 * Utility class to build a server response for a client
 */
public class ResponseBuilder {
	private final StringBuilder respMessage;

	/**
	 * Constructor
	 */
	public ResponseBuilder() {
		respMessage = new StringBuilder();

		resetRespMessage();
	}

	/**
	 * Returns the complete response message
	 * 
	 * @return message
	 */
	public String getRespMessage() {
		/*
		 * if (respMessageCounter == 1) { respMessage.append("None.\n"); }
		 */

		return respMessage.toString();
	}

	/**
	 * Resets the response message
	 */
	public void resetRespMessage() {
		// respMessageCounter = 1;

		respMessage.setLength(0);

		// respMessage.append("Message(s) from Server:\n");
	}

	/**
	 * Appends a string to the response message
	 * 
	 * @param message
	 */
	public void appendRespMessage(String message) {
		respMessage.append(message + " ");

		/*
		 * respMessage.append(respMessageCounter + ")  " + message);
		 * 
		 * if (!message.endsWith("\n")) { respMessage.append("\n"); }
		 * respMessageCounter++;
		 */
	}
}
