package com.seller.panel.controller;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.handler.ExceptionHandler;
import com.seller.panel.util.JwtTokenUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest {

    @InjectMocks
    private RegistrationController registrationController;

    @Mock
    private ExceptionHandler exceptionHandler;

    @Test
    public void shouldThrowProvideIdException() {
        when(exceptionHandler.getException("SP-2")).thenReturn(new SellerPanelException("Provide id"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            registrationController.register(StringUtils.EMPTY);
        });
        verify(exceptionHandler, times(1)).getException("SP-2");
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldRegisterUser() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<Void> responseEntity = registrationController.register(TestDataMaker.REGISTER_ID);
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        verifyNoMoreInteractions(exceptionHandler);
    }
}
