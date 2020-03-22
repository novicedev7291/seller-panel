package com.seller.panel.controller;

import com.seller.panel.dto.ApiError;
import com.seller.panel.exception.SellerPanelException;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private static final Log LOG = LogFactory.getLog(ExceptionHandlerController.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex, WebRequest req) {
        LOG.error("Exception occurred", ex);
        ApiError error = new ApiError();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setDateTime(Instant.now());
        error.setMessageKey("server.error");
        error.setMessage(ex.getLocalizedMessage());
        if (ex.getCause() != null) {
            error.setCause(ex.getCause().getLocalizedMessage());
        }
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(Exception ex, WebRequest req) {
        LOG.error("AccessDeniedException occurred", ex);
        ApiError error = new ApiError();
        error.setStatus(HttpStatus.FORBIDDEN);
        error.setDateTime(Instant.now());
        error.setMessageKey("access.denied");
        error.setMessage(ex.getLocalizedMessage());
        if (ex.getCause() != null) {
            error.setCause(ex.getCause().getLocalizedMessage());
        }
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiError> handleExpiredJwtException(Exception ex, WebRequest req) {
        LOG.error("ExpiredJwtException occurred", ex);
        ApiError error = new ApiError();
        error.setStatus(HttpStatus.UNAUTHORIZED);
        error.setDateTime(Instant.now());
        error.setMessageKey("token.expired");
        error.setMessage(ex.getLocalizedMessage());
        if (ex.getCause() != null) {
            error.setCause(ex.getCause().getLocalizedMessage());
        }
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(SellerPanelException.class)
    public ResponseEntity<ApiError> handleApplicationException(Exception ex, WebRequest req) {
        LOG.error("SellerPanelException occurred: ", ex);
        ApiError error = new ApiError();
        error.setStatus(HttpStatus.BAD_REQUEST);
        error.setDateTime(Instant.now());
        error.setMessageKey(((SellerPanelException) ex).getMessageKey());
        error.setMessage(ex.getLocalizedMessage());
        if (ex.getCause() != null) {
            error.setCause(ex.getCause().getLocalizedMessage());
        }
        return new ResponseEntity<>(error, error.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
