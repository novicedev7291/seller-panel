package com.seller.panel.controller;

import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.handler.ExceptionHandler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public abstract class BaseControllerTest {

    @Mock
    protected ExceptionHandler exceptionHandler;


}
