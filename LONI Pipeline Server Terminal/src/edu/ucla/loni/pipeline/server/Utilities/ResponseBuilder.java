package edu.ucla.loni.pipeline.server.Utilities;

public class ResponseBuilder {
	private StringBuilder respMessage;
	private int respMessageCounter;
	
	public ResponseBuilder() {
		respMessage = new StringBuilder();

		resetRespMessage();
	}
	
	public String getRespMessage() {
		return respMessage.toString();
	}

	public void resetRespMessage() {
		respMessageCounter = 1;
		
		respMessage.setLength(0);
		
		respMessage.append("Message(s) from Server:\n");
	}
	
	public void appendRespMessage(String message) {
		respMessage.append(respMessageCounter + ")  " + message);
		respMessageCounter++;
	}
}
