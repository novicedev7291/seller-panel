package com.seller.panel.controller;

import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.handler.ExceptionHandler;
import com.seller.panel.handler.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {

    @Autowired
    private ExceptionHandler exceptionHandler;

    @Autowired
    private MessageHandler messageHandler;

    protected SellerPanelException getException(String key) {
        return exceptionHandler.getException(key);
    }

    protected String getMessage(String key) {
        return messageHandler.getMessage(key);
    }

}
