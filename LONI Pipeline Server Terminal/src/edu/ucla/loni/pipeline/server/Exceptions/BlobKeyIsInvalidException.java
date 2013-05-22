package edu.ucla.loni.pipeline.server.Exceptions;

public class BlobKeyIsInvalidException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BlobKeyIsInvalidException() {
		super();
	}

	public BlobKeyIsInvalidException(String message) {
		super(message);
	}
}
