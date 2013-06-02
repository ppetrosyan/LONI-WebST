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

import javax.servlet.http.HttpSession;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.ucla.loni.pipeline.client.Login.LoginService;
import edu.ucla.loni.pipeline.client.Login.UserDTO;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService{

    private static final long serialVersionUID = 270628040929463623L;

    public String login(UserDTO user) {
    	System.out.println("LoginServiceImpl.login()");
    	return storeUserInSession(user);
    }
    
    public void logout() {
    	System.out.println("LoginServiceImpl.logout()");
        deleteUserFromSession();
    }
    
    private String storeUserInSession(UserDTO user) {
    		System.out.println("storeUserInSession()");
    		
            if((user != null) && 
               (user.getUsername().equals("abc")) &&
               (user.getPassword().equals("123"))) {
            		System.out.println("abc & 123");
//                    return getThreadLocalRequest().getSession().getId();
                    
            		HttpSession httpSession = this.getThreadLocalRequest().getSession(true);

            		httpSession.setMaxInactiveInterval(1000 * 60 * 2);
            		System.out.println("httpSessionId = " + httpSession.getId());
            		
            		return httpSession.getId();
            }
            
            System.out.println("not abc & 123");
            return null;
    }
    
    private void deleteUserFromSession() {
    	System.out.println("deleteUserFormSession()");
    	HttpSession httpSession = this.getThreadLocalRequest().getSession();
    	httpSession.invalidate();
    	
    	return;
    }
    
}