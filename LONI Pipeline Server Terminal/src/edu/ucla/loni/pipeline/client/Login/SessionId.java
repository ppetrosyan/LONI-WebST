package edu.ucla.loni.pipeline.client.Login;

import java.io.Serializable;

public class SessionId implements Serializable{

	private static final long serialVersionUID = 4637358200152132934L;

	private String sessionId = "";

	public void setSessionId(String sessionId) {
		if(sessionId == null)
			this.sessionId = "";
		else
			this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}

}