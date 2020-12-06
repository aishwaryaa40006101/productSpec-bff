package com.pccw.prodspec.bff.exception;

/**
 * This class is used to handle Custom Exceptions
 * 
 * @author 20023424
 */
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String details;

	/**
	 * This is parameterized constructor of CustomExceprion class
	 * 
	 * @param message
	 * @param details
	 */
	public CustomException(String message, String details) {
		super(message);
		this.details = details;
	}

	/**
	 * This method returns the exception details
	 * 
	 * @return details
	 */
	public String getDetails() {
		return details;
	}

}