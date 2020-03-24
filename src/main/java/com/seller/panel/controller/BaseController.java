package com.seller.panel.controller;

import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.handler.ExceptionHandler;
import com.seller.panel.handler.MessageHandler;
import com.seller.panel.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class BaseController {

    @Autowired
    protected HttpServletRequest request;

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

    public Long getCompanyId() {
        return Long.parseLong(getData("companyId", "nc-2"));
    }

    @SuppressWarnings("unchecked")
    private String getData(String key, String msgCode) {
        Object data = ((Map<String, Object>) request.getAttribute(AppConstants.ADDITIONAL_INFO)).get(key);
        if (data == null) {
            throw getException(msgCode);
        }

        return data.toString();
    }

}
