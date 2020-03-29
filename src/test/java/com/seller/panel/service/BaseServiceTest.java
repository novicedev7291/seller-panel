package com.seller.panel.service;

import com.seller.panel.handler.ExceptionHandler;
import com.seller.panel.handler.MessageHandler;
import org.mockito.Mock;

public abstract class BaseServiceTest {

    @Mock
    protected ExceptionHandler exceptionHandler;

    @Mock
    protected MessageHandler messageHandler;

}
