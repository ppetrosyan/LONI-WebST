package edu.ucla.loni.pipeline.client.Login;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.ucla.loni.pipeline.client.Login.SessionId;

@RemoteServiceRelativePath("session")
public interface SessionService extends RemoteService{
    SessionId session(SessionId sessionId);
}