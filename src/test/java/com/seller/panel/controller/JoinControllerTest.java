package com.seller.panel.controller;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.JoinRequest;
import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.service.MailService;
import com.seller.panel.util.AppConstants;
import com.seller.panel.util.JwtTokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JoinControllerTest extends BaseControllerTest {

    @InjectMocks
    private JoinController joinController;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private MailService mailService;

    @Mock
    private Environment env;

    @Mock
    private RedisTemplate redisTemplate;

    @Mock
    private ValueOperations valueOperations;

    @Test
    public void shouldThrowProvideEmailException() {
        when(exceptionHandler.getException("SP-1")).thenReturn(new SellerPanelException("Provide email address"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            joinController.join(new JoinRequest());
        });
        verify(exceptionHandler, times(1)).getException("SP-1");
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldThrowInvalidEmailException() {
        when(exceptionHandler.getException("SP-2")).thenReturn(new SellerPanelException("Invalid email address"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            joinController.join(new JoinRequest(TestDataMaker.WRONG_EMAIL));
        });
        verify(exceptionHandler, times(1)).getException("SP-2");
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldJoinUser() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(jwtTokenUtil.generateToken(TestDataMaker.EMAIL1, Collections.EMPTY_MAP)).thenReturn(TestDataMaker.JWT_TOKEN);
        when(env.getProperty(AppConstants.UI_INVITE_URL)).thenReturn(TestDataMaker.UI_INVITE_URL);
        when(jwtTokenUtil.getJtiFromToken(TestDataMaker.JWT_TOKEN)).thenReturn(UUID.randomUUID().toString());
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(env.getProperty(AppConstants.JOIN_TOKEN_EXPIRY)).thenReturn(TestDataMaker.JOIN_TOKEN_EXPIRY);
        doNothing().when(mailService).sendEmail(any(String.class), any(String.class), any(String.class));
        ResponseEntity<Void> responseEntity = joinController.join(new JoinRequest(TestDataMaker.EMAIL1));
        assertThat(HttpStatus.CREATED.value(), equalTo(responseEntity.getStatusCodeValue()));
        verify(jwtTokenUtil, times(1)).generateToken(TestDataMaker.EMAIL1, Collections.EMPTY_MAP);
        verify(jwtTokenUtil, times(1)).getJtiFromToken(TestDataMaker.JWT_TOKEN);
        verifyNoMoreInteractions(jwtTokenUtil);
        verify(redisTemplate, times(1)).opsForValue();
        verify(redisTemplate, times(1)).expire(anyString(), anyLong(), any(TimeUnit.class));
        verifyNoMoreInteractions(redisTemplate);
        verify(env, times(1)).getProperty(AppConstants.UI_INVITE_URL);
        verify(env, times(1)).getProperty(AppConstants.JOIN_TOKEN_EXPIRY);
        verifyNoMoreInteractions(env);
        verify(mailService, times(1)).sendEmail(any(String.class), any(String.class), any(String.class));
        verifyNoMoreInteractions(mailService);
    }

}
