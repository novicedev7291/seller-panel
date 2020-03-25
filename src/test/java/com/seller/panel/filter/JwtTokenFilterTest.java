package com.seller.panel.filter;

import com.github.javafaker.App;
import com.seller.panel.controller.ExceptionHandlerController;
import com.seller.panel.data.TestDataMaker;
import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.handler.ExceptionHandler;
import com.seller.panel.util.AppConstants;
import com.seller.panel.util.EndPointConstants;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class JwtTokenFilterTest {

    @InjectMocks
    private JwtTokenFilter jwtTokenFilter;

    @Mock
    private ExceptionHandlerController exceptionHandlerController;

    @Mock
    private ExceptionHandler exceptionHandler;

    @Mock
    private ResourceServerTokenServices resourceServerTokenServices;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private OAuth2AccessToken oAuth2AccessToken;

    @Mock
    private Exception exception;

    @Mock
    private FilterConfig filterConfig;

    @Test
    public void shouldCallDoFilterWithoutJwt() throws IOException, ServletException  {
        when(request.getRequestURI()).thenReturn("/api/v1"+ EndPointConstants.Login.LOGIN);
        when(request.getContextPath()).thenReturn("/api/v1");
        jwtTokenFilter.doFilter(request, response, filterChain);
    }

    @Test
    public void shouldCallDoFilterWithJwtInHeader() throws IOException, ServletException  {
        String token = AppConstants.BEARER+TestDataMaker.JWT_TOKEN;
        when(request.getRequestURI()).thenReturn("/api/v1"+ EndPointConstants.Users.USERS);
        when(request.getContextPath()).thenReturn("/api/v1");
        when(request.getHeader(AppConstants.AUTHORIZATION)).thenReturn(token);
        when(resourceServerTokenServices.readAccessToken(token.trim().substring(7))).thenReturn(oAuth2AccessToken);
        when(oAuth2AccessToken.getAdditionalInformation()).thenReturn(TestDataMaker.makeAdditionalInfo());
        jwtTokenFilter.doFilter(request, response, filterChain);
        verify(request,times(5)).getRequestURI();
        verify(request,times(5)).getContextPath();
        verify(request,times(1)).getHeader(AppConstants.AUTHORIZATION);
        verify(request,times(1)).setAttribute(AppConstants.ADDITIONAL_INFO, TestDataMaker.makeAdditionalInfo());
        verify(request,times(1)).setAttribute(AppConstants.TOKEN, token.trim().substring(7));
        verify(resourceServerTokenServices).readAccessToken(token.trim().substring(7));
        verify(oAuth2AccessToken).getAdditionalInformation();
        verifyNoMoreInteractions(request);
        verifyNoMoreInteractions(resourceServerTokenServices);
        verifyNoMoreInteractions(oAuth2AccessToken);
    }

    @Test
    public void shouldWorkAndExceptionForAccessTokenMissing() throws IOException, ServletException {
        SellerPanelException sellerPanelException = new SellerPanelException("Access token missing");
        when(request.getRequestURI()).thenReturn("/api/v1"+ EndPointConstants.Users.USERS);
        when(request.getContextPath()).thenReturn("/api/v1");
        when(request.getHeader(AppConstants.AUTHORIZATION)).thenReturn(null);
        when(exceptionHandlerController.handleApplicationException(sellerPanelException, null)).thenReturn(new ResponseEntity<Object>(TestDataMaker.EMAIL1, HttpStatus.BAD_REQUEST));
        when(exceptionHandler.getException("SP-6")).thenReturn(sellerPanelException);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        jwtTokenFilter.doFilter(request, response, filterChain);
        verify(request,times(5)).getRequestURI();
        verify(request,times(5)).getContextPath();
        verify(request,times(1)).getHeader(AppConstants.AUTHORIZATION);
        verify(request).getCookies();
        verify(exceptionHandlerController, times(1)).handleApplicationException(sellerPanelException, null);
        verify(exceptionHandler, times(1)).getException("SP-6");
        verifyNoMoreInteractions(request);
        verifyNoMoreInteractions(exceptionHandlerController);
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldCallDoFilterWithJwtInCookie() throws IOException, ServletException  {
        String token = AppConstants.BEARER+TestDataMaker.JWT_TOKEN;
        when(request.getRequestURI()).thenReturn("/api/v1"+ EndPointConstants.Users.USERS);
        when(request.getContextPath()).thenReturn("/api/v1");
        when(request.getCookies()).thenReturn(TestDataMaker.makeCookies());
        when(resourceServerTokenServices.readAccessToken(token.trim().substring(7))).thenReturn(oAuth2AccessToken);
        when(oAuth2AccessToken.getAdditionalInformation()).thenReturn(TestDataMaker.makeAdditionalInfo());
        jwtTokenFilter.doFilter(request, response, filterChain);
        verify(request,times(5)).getRequestURI();
        verify(request,times(5)).getContextPath();
        verify(request,times(1)).getHeader(AppConstants.AUTHORIZATION);
        verify(request).getCookies();
        verify(request,times(1)).setAttribute(AppConstants.ADDITIONAL_INFO, TestDataMaker.makeAdditionalInfo());
        verify(request,times(1)).setAttribute(AppConstants.TOKEN, token.trim().substring(7));
        verify(resourceServerTokenServices).readAccessToken(token.trim().substring(7));
        verify(oAuth2AccessToken).getAdditionalInformation();
        verifyNoMoreInteractions(request);
        verifyNoMoreInteractions(resourceServerTokenServices);
        verifyNoMoreInteractions(oAuth2AccessToken);
    }

    @Test
    public void shouldCallDestroy() {
        jwtTokenFilter.destroy();
    }

    @Test
    public void shouldCallInit() {
        jwtTokenFilter.init(filterConfig);
    }
}
