package com.aston.thirdTask.api;

import com.aston.thirdTask.domain.exception.ExceptionMessage;
import com.aston.thirdTask.domain.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

/**
 * Global exception handler for REST controllers.
 */
@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionRestHandler extends ResponseEntityExceptionHandler {
    private static final Map<Class<?>, HttpStatus> EXCEPTION_STATUS = Map.of(
            ConstraintViolationException.class, HttpStatus.BAD_REQUEST,
            DataIntegrityViolationException.class, HttpStatus.BAD_REQUEST,
            EntityNotFoundException.class, HttpStatus.NOT_FOUND,
            NotFoundException.class, HttpStatus.NOT_FOUND
    );

    /**
     * Handles internal exceptions and logs the error details.
     *
     * @param exception  the exception that was thrown.
     * @param body       the body of the response.
     * @param headers    the HTTP headers.
     * @param statusCode the HTTP status code.
     * @param request    the web request.
     * @return a ResponseEntity with the error details.
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception exception,
            @Nullable Object body,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode statusCode,
            @NonNull WebRequest request
    ) {

        log.error("[{}]", exception.getMessage());
        log.error("exception: ID=[{}], HttpStatus=[{}]", statusCode, exception.getClass().getName());
        log.error("Exception trace:", exception);
        return super.handleExceptionInternal(exception, ExceptionMessage.of(exception.getMessage()), headers, statusCode, request);

    }

    /**
     * Handles general exceptions and returns an appropriate HTTP response.
     *
     * @param exception the exception that was thrown.
     * @return a ResponseEntity with the error details.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception exception) {
        return doHandle(exception, getHttpStatusFromException(exception.getClass()));
    }

    /**
     * Retrieves the HTTP status code for a given exception class.
     *
     * @param exception the exception class.
     * @return the corresponding HTTP status code.
     */
    private HttpStatus getHttpStatusFromException(final Class<?> exception) {
        return EXCEPTION_STATUS.getOrDefault(
                exception, HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    /**
     * Creates a ResponseEntity with the error details.
     *
     * @param exception the exception that was thrown.
     * @param status    the HTTP status code.
     * @return a ResponseEntity with the error details.
     */
    private ResponseEntity<?> doHandle(Exception exception, HttpStatus status) {
        String message = exception.getMessage();
        log.error("exception: message={}, HttpStatus={}", message, status, exception);
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ExceptionMessage.of(message));

    }

}
