package edu.ucla.loni.pipeline.client.Login;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
    void login(UserDTO user, AsyncCallback<String> asyncCallback);
}