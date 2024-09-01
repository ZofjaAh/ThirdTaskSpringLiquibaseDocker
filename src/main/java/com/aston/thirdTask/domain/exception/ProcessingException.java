package com.aston.thirdTask.domain.exception;

/**
 * Exception thrown when an error occurs during processing.
 */
public class ProcessingException extends RuntimeException {
    /**
     * Constructs a new ProcessingException with the specified detail message.
     *
     * @param message the detail message.
     */
    public ProcessingException(String message) {
        super(message);
    }
}
