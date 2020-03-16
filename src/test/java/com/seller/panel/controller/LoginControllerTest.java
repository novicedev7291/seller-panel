package com.seller.panel.controller;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.InvitationRequest;
import com.seller.panel.dto.LoginRequest;
import com.seller.panel.dto.LoginResponse;
import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.service.LoginService;
import com.seller.panel.util.AppConstants;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest extends BaseControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private LoginService loginService;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Test
    public void shouldThrowProvideUsernameException() {
        when(exceptionHandler.getException("SP-3")).thenReturn(new SellerPanelException("Provide username"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            loginController.login(new LoginRequest());
        });
        verify(exceptionHandler, times(1)).getException("SP-3");
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldThrowProvidePasswordException() {
        when(exceptionHandler.getException("SP-4")).thenReturn(new SellerPanelException("Provide password"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            loginController.login(new LoginRequest(TestDataMaker.EMAIL1, StringUtils.EMPTY));
        });
        verify(exceptionHandler, times(1)).getException("SP-4");
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldLogin() {
        LoginRequest loginRequest = new LoginRequest(TestDataMaker.EMAIL1, TestDataMaker.PASSWORD);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(loginService.validateDataAndGenerateToken(loginRequest)).thenReturn(TestDataMaker.JWT_TOKEN);
        ResponseEntity<LoginResponse> responseEntity = loginController.login(loginRequest);
        assertThat(HttpStatus.CREATED.value(), equalTo(responseEntity.getStatusCodeValue()));
        verify(loginService, times(1)).validateDataAndGenerateToken(loginRequest);
    }

}
