package com.seller.panel.handler;

import com.seller.panel.exception.SellerPanelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    public SellerPanelException getException(String key) {
        return getException(key, null);
    }

    public SellerPanelException getException(String key, Object[] params) {
        return getException(key, params, LocaleContextHolder.getLocale());
    }

    public SellerPanelException getException(String key, Object[] params, Locale locale) {
        return new SellerPanelException(messageSource.getMessage(key, params, locale), key);
    }
}
