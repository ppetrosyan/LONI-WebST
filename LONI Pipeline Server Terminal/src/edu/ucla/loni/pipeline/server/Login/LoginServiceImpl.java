package edu.ucla.loni.pipeline.server.Login;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.ucla.loni.pipeline.client.Login.UserDTO;
import edu.ucla.loni.pipeline.client.Login.LoginService;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService{

        private static final long serialVersionUID = 270628040929463623L;

        public String login(UserDTO user) {
        		System.out.println("LoginServiceImpl.login()");
                if((user != null) && 
                   (user.getUsername().equals("abc")) &&
                   (user.getPassword().equals("123"))) {
                		System.out.println("abc & 123");
                		Window.alert("abc & 123");
//                        return getThreadLocalRequest().getSession().getId();
                        
                		HttpSession httpSession = getThreadLocalRequest().getSession();
                		httpSession.setMaxInactiveInterval(1000 * 60 *2);
                		System.out.println("httpSessionId = " + httpSession.getId());
                		
                		return httpSession.getId();
                }
                
                System.out.println("not abc & 123");
                Window.alert("not abc & 123");
                return null;
        }
}