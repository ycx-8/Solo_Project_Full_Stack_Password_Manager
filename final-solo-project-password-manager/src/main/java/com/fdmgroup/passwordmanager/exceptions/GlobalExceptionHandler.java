package com.fdmgroup.passwordmanager.exceptions;

import java.time.Instant;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * GlobalExceptionHandler is a class responsible for handling global exceptions across
 * the application.
 *
 * This class uses the @ControllerAdvice annotation to provide a centralized
 * exception handling mechanism. It captures and handles exceptions and returns
 * a well-defined error response to the client.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	/**
     * Handles exceptions of type CustomizedNotFoundException.
     *
     * @param exc The caught CustomizedNotFoundException.
     * @return ResponseEntity with HttpStatus.NOT_FOUND and the error details.
     */
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(CustomizedNotFoundException exc) {
		ErrorResponse error = new ErrorResponse();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(Instant.now());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	/**
     * Default exception handler for exceptions not caught by other handlers.
     *
     * @param exc The caught general Exception.
     * @return ResponseEntity with HttpStatus.BAD_REQUEST and the error details.
     */
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(Exception exc) {
		ErrorResponse error = new ErrorResponse();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(Instant.now());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	/**
     * Handles exceptions of type UnauthorizedException.
     *
     * @param exc The caught UnauthorizedException.
     * @return ResponseEntity with HttpStatus.UNAUTHORIZED and the error details.
     */
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(UnauthorizedException exc) {
		ErrorResponse error = new ErrorResponse();
		error.setStatus(HttpStatus.UNAUTHORIZED.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(Instant.now());
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

}
