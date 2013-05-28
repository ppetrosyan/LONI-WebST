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

	private LONI_Pipeline_ST_Login_Display wbsl;
	private LONI_Pipeline_ST_Tabset_Display wbst;
	private final SessionId sessionId = new SessionId();
	private UserDTO user = new UserDTO();

	public LONI_Pipeline_Server_Terminal() {
		wbsl = new LONI_Pipeline_ST_Login_Display();
		wbst = new LONI_Pipeline_ST_Tabset_Display("Guest");
		sessionId.setSessionId(Cookies.getCookie("session"));
		
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
	public void onModuleLoad() {
		// Build Login Page
		validateSession();
//		wbsl.buildMainPage(user, sessionId);
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
				if((result == null) || (!sessionId.getSessionId().equals(result.getSessionId()))){
					// Build Login Page
					System.out.println("First log in");
					wbsl.buildMainPage(user, sessionId);
				}else if(sessionId.getSessionId().equals(result.getSessionId())){
					System.out.println("Already log in");
					wbst.buildMainPage();
				}
			}
		};
		myServiceAsync.session(sessionId, asyncCallback);
	}	
}
