package com.fdmgroup.passwordmanager.exceptions;

/**
 * UnauthorizedException is a custom exception class that extends RuntimeException.
 * 
 * This exception is thrown when an operation is attempted that requires user
 * authentication or a specific role, and the user does not meet the criteria.
 */
public class UnauthorizedException extends RuntimeException {

	/**
	 * Serialization identifier for this class
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructs an UnauthorizedException with a detailed error message.
     *
     * @param message The details of the unauthorized attempt, to be incorporated
     *                into the exception message.
     */
	public UnauthorizedException(String message) {
		super(message);
	}
}
