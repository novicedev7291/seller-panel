package com.seller.panel.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageHandler {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key) {
        return getMessage(key, null);
    }

    public String getMessage(String key, Object[] params) {
        return getMessage(key, params, LocaleContextHolder.getLocale());
    }

    public String getMessage(String key, Object[] params, Locale locale) {
        return messageSource.getMessage(key, params, locale);
    }

}
