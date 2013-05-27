package edu.ucla.loni.pipeline.client.Utilities;

import java.io.Serializable;

public class WebUrlResponseBuilder implements Serializable {

	private static final long serialVersionUID = 7102038232914698194L;
	private String xml;
	private String message;
	private boolean status;
	
	public WebUrlResponseBuilder() {
		xml = new String();
		message = new String();
		xml = "";
		message = "";
		status = false;
	}
	
	public void setXml(String xml) {
		this.xml = xml;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public String getXml() {
		return xml;
	}
	
	public String getMessage() {
		return message;
	}
	
	public boolean getStatus() {
		return status;
	}
}
