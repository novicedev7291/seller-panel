package com.seller.panel.controller;

import com.seller.panel.handler.ExceptionHandler;
import com.seller.panel.handler.MessageHandler;
import org.mockito.Mock;

public abstract class BaseControllerTest {

    @Mock
    protected ExceptionHandler exceptionHandler;

    @Mock
    protected MessageHandler messageHandler;


}
