package com.seller.panel.controller;

import com.seller.panel.handler.ExceptionHandler;
import com.seller.panel.handler.MessageHandler;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseControllerTest {

    @Mock
    protected ExceptionHandler exceptionHandler;

    @Mock
    protected MessageHandler messageHandler;

    @Mock
    protected HttpServletRequest request;

}
