package com.seller.panel.controller;

import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.handler.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {

    @Autowired
    private ExceptionHandler exceptionHandler;

    protected SellerPanelException getException(String key) {
        return exceptionHandler.getException(key);
    }

}
