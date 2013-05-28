package edu.ucla.loni.pipeline.server.Login;

import javax.servlet.http.HttpSession;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.ucla.loni.pipeline.client.Login.SessionId;
import edu.ucla.loni.pipeline.client.Login.SessionService;

public class SessionServiceImpl extends RemoteServiceServlet implements SessionService{

        private static final long serialVersionUID = -6274876845484737659L;

        public SessionId session(SessionId sessionId) {
        		System.out.println("SessionServiceImpl.session()");
                HttpSession httpSession = getThreadLocalRequest().getSession(false);
                if(httpSession != null){
                	
//                	sessionId.setSessionId(httpSession.getId());
//                  return sessionId;
                	
                	System.out.println("session(): httpSession != null");
                        
                	try {
                		sessionId.setSessionId(httpSession.getId());
                	} catch (IllegalStateException e) {
                		sessionId.setSessionId("");
                		System.out.println(e);
                	}
                	return sessionId;
                }
                return null;
        }
        
}