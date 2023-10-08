package com.fdmgroup.passwordmanager.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;

class ExceptionTest {

	@Test
    public void testHandleExceptionReturnsCorrectErrorResponse() {
        // Arrange
        String exceptionMessage = "Resource not found";
        CustomizedNotFoundException exception = new CustomizedNotFoundException(exceptionMessage);
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        // Act
        ResponseEntity<ErrorResponse> responseEntity = handler.handleException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getBody().getStatus());
    }

    @Test
    public void testHandleExceptionSetsCurrentTimestamp() {
        // Arrange
        String exceptionMessage = "Resource not found";
        CustomizedNotFoundException exception = new CustomizedNotFoundException(exceptionMessage);
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Instant before = Instant.now();

        // Act
        ResponseEntity<ErrorResponse> responseEntity = handler.handleException(exception);
        Instant after = Instant.now();

        // Assert
        Instant actualTimestamp = responseEntity.getBody().getTimeStamp();
        assertEquals(true, (actualTimestamp.isAfter(before) || actualTimestamp.equals(before)));
        assertEquals(true, (actualTimestamp.isBefore(after) || actualTimestamp.equals(after)));
    }

}
