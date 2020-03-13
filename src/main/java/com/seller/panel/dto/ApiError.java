package com.seller.panel.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
public class ApiError {
    private HttpStatus status;
    private String messageKey;
    private String message;
    private String cause;
    private Instant dateTime;
}
