package com.seller.panel.controller;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.RegistrationResponse;
import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.util.JwtTokenUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest extends BaseControllerTest {

    @InjectMocks
    private RegistrationController registrationController;

    @Mock
    private RedisTemplate redisTemplate;

    @Mock
    private ValueOperations valueOperations;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private HttpServletRequest httpServletRequest;

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
    public void shouldThrowAccessDeniedException() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(messageHandler.getMessage("SP-6")).thenReturn("Link expired, sign up again");
        Assertions.assertThrows(AccessDeniedException.class, () -> {
            registrationController.register(TestDataMaker.EMAIL1);
        });
        verify(redisTemplate, times(1)).opsForValue();
        verify(messageHandler, times(1)).getMessage("SP-6");
        verifyNoMoreInteractions(redisTemplate);
        verifyNoMoreInteractions(messageHandler);
    }

    @Test
    public void shouldRegisterUser() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(redisTemplate.opsForValue().get(anyString())).thenReturn(TestDataMaker.JWT_TOKEN);
        when(jwtTokenUtil.getSubjectFromToken(TestDataMaker.JWT_TOKEN)).thenReturn(TestDataMaker.EMAIL1);
        when(jwtTokenUtil.generateToken(TestDataMaker.EMAIL1, new HashMap<>())).thenReturn(TestDataMaker.JWT_TOKEN);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<RegistrationResponse> responseEntity = registrationController.register(TestDataMaker.REGISTER_ID);
        assertThat(HttpStatus.OK.value(), equalTo(responseEntity.getStatusCodeValue()));
        verify(redisTemplate, times(2)).opsForValue();
        verify(jwtTokenUtil, times(1)).getSubjectFromToken(TestDataMaker.JWT_TOKEN);
        verify(jwtTokenUtil, times(1)).generateToken(TestDataMaker.EMAIL1, new HashMap<>());
        verifyNoMoreInteractions(redisTemplate);
        verifyNoMoreInteractions(jwtTokenUtil);
    }
}
