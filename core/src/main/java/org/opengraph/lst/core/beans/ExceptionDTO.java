package org.opengraph.lst.core.beans;

public class ExceptionDTO {
	
	private String message;
	
	public ExceptionDTO(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
