package com.fdmgroup.passwordmanager.exceptions;

import java.time.Instant;

import lombok.*;

/**
 * ErrorResponse is a POJO (Plain Old Java Object) that encapsulates information about an error response.
 * 
 * This class is used to send standardized error responses back to the client. It includes status codes,
 * a message explaining the error, and a timestamp indicating when the error occurred.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ErrorResponse {
	
	/**
     * The HTTP status code representing the type of error (e.g., 404 for Not Found, 500 for Internal Server Error).
     */
	private int status;
	
	/**
     * A descriptive message providing more details about the error.
     */
	private String message;
	
	/**
     * A timestamp marking when the error occurred.
     */
	private Instant timeStamp;
	
}