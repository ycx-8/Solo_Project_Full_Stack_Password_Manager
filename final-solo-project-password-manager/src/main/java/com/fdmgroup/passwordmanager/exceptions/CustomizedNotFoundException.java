package com.fdmgroup.passwordmanager.exceptions;

/**
 * CustomizedNotFoundException is a custom exception class that extends the RuntimeException.
 * 
 * This exception is used for resource-not-found scenarios within the application. 
 * It wraps around a String message that provides details about the nature of the error.
 */
public class CustomizedNotFoundException extends RuntimeException{

	/**
     * Default Serial Version ID generated for serialization.
     */
	private static final long serialVersionUID = 1L;

	/**
     * Constructs a new CustomizedNotFoundException with a specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
	public CustomizedNotFoundException(String message) {
		super(message);
	}

}
