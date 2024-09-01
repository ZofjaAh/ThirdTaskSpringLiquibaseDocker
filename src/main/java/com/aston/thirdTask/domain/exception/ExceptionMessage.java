package com.aston.thirdTask.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * Represents an exception message with an error ID.
 */
@Value
@Builder
@AllArgsConstructor(staticName = "of")
public class ExceptionMessage {
    /**
     * The unique identifier for the error.
     */
    String errorId;
}
