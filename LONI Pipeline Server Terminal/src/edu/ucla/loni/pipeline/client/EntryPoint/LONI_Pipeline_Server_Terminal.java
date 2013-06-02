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

package edu.ucla.loni.pipeline.client.EntryPoint;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.ucla.loni.pipeline.client.Login.LONI_Pipeline_ST_Login_Display;
import edu.ucla.loni.pipeline.client.Login.SessionId;
import edu.ucla.loni.pipeline.client.Login.SessionService;
import edu.ucla.loni.pipeline.client.Login.SessionServiceAsync;
import edu.ucla.loni.pipeline.client.Login.UserDTO;
import edu.ucla.loni.pipeline.client.MainPage.LONI_Pipeline_ST_Tabset_Display;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LONI_Pipeline_Server_Terminal implements EntryPoint {

	private final LONI_Pipeline_ST_Login_Display wbsl;
	private final LONI_Pipeline_ST_Tabset_Display wbst;
	private final SessionId sessionId = new SessionId();
	private final UserDTO user = new UserDTO();

	public LONI_Pipeline_Server_Terminal() {
		wbsl = new LONI_Pipeline_ST_Login_Display();
		wbst = new LONI_Pipeline_ST_Tabset_Display("Guest");
		sessionId.setSessionId(Cookies.getCookie("session"));
		System.out.println("Get Cookie: session = " + Cookies.getCookie("session"));

		if (sessionId == null) {
			System.out.println("sessionId == null");
		} else {
			System.out.println("sessionId != null");
		}
	}

	/**
	 * This is the entry point method. This is generated and managed by the
	 * visual designer.
	 */
	@Override
	public void onModuleLoad() {
		// Build Login Page
		validateSession();
		// wbsl.buildMainPage(user, sessionId);
	}

	private void validateSession(){
		System.out.println("validateSession()");

		SessionServiceAsync myServiceAsync = GWT.create(SessionService.class);

		AsyncCallback<SessionId> asyncCallback = new AsyncCallback<SessionId>(){
			public void onFailure(Throwable caught) {
				System.out.println(caught);
				System.out.println("validateSession(): onFailure");
			}
			public void onSuccess(SessionId result) {
				System.out.println("validateSession(): onSuccess");

				if (result == null) {
					System.out.println("result == null");
				} else {
					System.out.println("result.getSessionId = " + result.getSessionId());
				}
				System.out.println("sessionId.getSessionId = " + sessionId.getSessionId());

				if((result == null) || (!sessionId.getSessionId().equals(result.getSessionId()))){
					// Build Login Page
					System.out.println("First log in");
					wbsl.buildMainPage(user, sessionId);
				}else if(sessionId.getSessionId().equals(result.getSessionId())){
					System.out.println("Already log in");
					wbst.buildMainPage(user, sessionId);
				}
			}
		};
		myServiceAsync.session(sessionId, asyncCallback);
	}
}