package com.seller.panel.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.seller.panel.handler.CustomOAuthExceptionSerializer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@JsonSerialize(using = CustomOAuthExceptionSerializer.class)
public class CustomOAuthException extends OAuth2Exception {

    public CustomOAuthException(String msg) {
        super(msg);
    }

}
