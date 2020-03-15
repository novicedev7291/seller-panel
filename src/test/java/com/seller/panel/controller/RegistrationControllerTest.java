package com.seller.panel.controller;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.exception.SellerPanelException;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest extends BaseControllerTest {

    @InjectMocks
    private RegistrationController registrationController;

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
        assertThat(HttpStatus.OK.value(), equalTo(responseEntity.getStatusCodeValue()));
        verifyNoMoreInteractions(exceptionHandler);
    }
}
