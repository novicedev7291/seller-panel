package com.seller.panel.controller;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.LoginRequest;
import com.seller.panel.dto.LoginResponse;
import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.service.UserService;
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

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest extends BaseControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Test
    public void shouldLogin() {
        LoginRequest loginRequest = new LoginRequest(TestDataMaker.EMAIL1, TestDataMaker.PASSWORD);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword())).thenReturn(TestDataMaker.makeUser());
        when(jwtTokenUtil.generateToken(anyString(), anyMap())).thenReturn(TestDataMaker.JWT_TOKEN);
        ResponseEntity<LoginResponse> responseEntity = loginController.login(loginRequest);
        assertThat(HttpStatus.CREATED.value(), equalTo(responseEntity.getStatusCodeValue()));
        verify(userService, times(1)).authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        verify(jwtTokenUtil, times(1)).generateToken(anyString(), anyMap());
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(jwtTokenUtil);
    }

}
