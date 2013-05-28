package edu.ucla.loni.pipeline.client.Login;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.ucla.loni.pipeline.client.Login.SessionId;

public interface SessionServiceAsync {
    void session(SessionId sessionId, AsyncCallback<SessionId> asyncCallback);
}
