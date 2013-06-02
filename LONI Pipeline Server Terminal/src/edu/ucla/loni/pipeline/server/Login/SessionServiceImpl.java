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

package edu.ucla.loni.pipeline.server.Login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.ucla.loni.pipeline.client.Login.SessionId;
import edu.ucla.loni.pipeline.client.Login.SessionService;

public class SessionServiceImpl extends RemoteServiceServlet implements SessionService{

	private static final long serialVersionUID = -6274876845484737659L;

	public SessionId session(SessionId sessionId) {
		System.out.println("SessionServiceImpl.session()");

		HttpServletRequest request = this.getThreadLocalRequest();

		if (request == null) {
			System.out.println("request == null");

			return null;
		}

		HttpSession httpSession = request.getSession(false);

		if(httpSession != null){
			try {
				sessionId.setSessionId(httpSession.getId());
			} catch (IllegalStateException e) {
				sessionId.setSessionId("");
				System.out.println(e);
			}

			System.out.println("return sessionId");

			return sessionId;
		} else {
			System.out.println("httpSession == null");

			return null;
		}
	}
}