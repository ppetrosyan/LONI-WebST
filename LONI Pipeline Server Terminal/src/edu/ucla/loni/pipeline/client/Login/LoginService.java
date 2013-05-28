package edu.ucla.loni.pipeline.client.Login;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.ucla.loni.pipeline.client.Login.UserDTO;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService{
    String login(UserDTO user);
}