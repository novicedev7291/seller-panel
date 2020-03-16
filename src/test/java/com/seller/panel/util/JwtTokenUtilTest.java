package com.seller.panel.util;

import com.seller.panel.data.TestDataMaker;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.util.Collections;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtTokenUtilTest {

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private Environment env;

    @Test
    public void shouldGenerateJwtToken() {
        when(env.getProperty(AppConstants.JWT_EXPIRATION)).thenReturn(TestDataMaker.JWT_EXPIRATION);
        when(env.getProperty(AppConstants.JWT_SECRET)).thenReturn(TestDataMaker.JWT_SECRET);
        String token = jwtTokenUtil.generateToken(TestDataMaker.EMAIL1, new HashMap<>());
        assertThat(token, notNullValue());
        verify(env, times(1)).getProperty(AppConstants.JWT_EXPIRATION);
        verify(env, times(1)).getProperty(AppConstants.JWT_SECRET);
        verifyNoMoreInteractions(env);
    }

    @Test
    public void shouldThrowExpiredJwtException() {
        when(env.getProperty(AppConstants.JWT_SECRET)).thenReturn(TestDataMaker.JWT_SECRET);
        Assertions.assertThrows(ExpiredJwtException.class, () -> {
            jwtTokenUtil.validateToken(TestDataMaker.JWT_TOKEN, TestDataMaker.EMAIL1);
        });
        verify(env, times(1)).getProperty(AppConstants.JWT_SECRET);
        verifyNoMoreInteractions(env);
    }

    @Test
    public void shouldInvalidToken() {
        when(env.getProperty(AppConstants.JWT_EXPIRATION)).thenReturn(TestDataMaker.JWT_EXPIRATION);
        when(env.getProperty(AppConstants.JWT_SECRET)).thenReturn(TestDataMaker.JWT_SECRET);
        String token = jwtTokenUtil.generateToken(TestDataMaker.EMAIL1, new HashMap<>());
        when(env.getProperty(AppConstants.JWT_SECRET)).thenReturn(TestDataMaker.JWT_SECRET);
        Boolean isValid = jwtTokenUtil.validateToken(token, TestDataMaker.EMAIL2);
        assertThat(isValid, equalTo(false));
        verify(env, times(1)).getProperty(AppConstants.JWT_EXPIRATION);
        verify(env, times(2)).getProperty(AppConstants.JWT_SECRET);
        verifyNoMoreInteractions(env);
    }

    @Test
    public void shouldValidToken() {
        when(env.getProperty(AppConstants.JWT_EXPIRATION)).thenReturn(TestDataMaker.JWT_EXPIRATION);
        when(env.getProperty(AppConstants.JWT_SECRET)).thenReturn(TestDataMaker.JWT_SECRET);
        String token = jwtTokenUtil.generateToken(TestDataMaker.EMAIL1, new HashMap<>());
        when(env.getProperty(AppConstants.JWT_SECRET)).thenReturn(TestDataMaker.JWT_SECRET);
        Boolean isValid = jwtTokenUtil.validateToken(token, TestDataMaker.EMAIL1);
        assertThat(isValid, equalTo(true));
        verify(env, times(1)).getProperty(AppConstants.JWT_EXPIRATION);
        verify(env, times(2)).getProperty(AppConstants.JWT_SECRET);
        verifyNoMoreInteractions(env);
    }

    @Test
    public void shouldFetchSubjectFromToken() {
        when(env.getProperty(AppConstants.JWT_EXPIRATION)).thenReturn(TestDataMaker.JWT_EXPIRATION);
        when(env.getProperty(AppConstants.JWT_SECRET)).thenReturn(TestDataMaker.JWT_SECRET);
        String token = jwtTokenUtil.generateToken(TestDataMaker.EMAIL1, new HashMap<>());
        when(env.getProperty(AppConstants.JWT_SECRET)).thenReturn(TestDataMaker.JWT_SECRET);
        String subject = jwtTokenUtil.getSubjectFromToken(token);
        assertThat(TestDataMaker.EMAIL1, equalTo(subject));
        verify(env, times(1)).getProperty(AppConstants.JWT_EXPIRATION);
        verify(env, times(2)).getProperty(AppConstants.JWT_SECRET);
        verifyNoMoreInteractions(env);
    }

    @Test
    public void shouldFetchJtiFromToken() {
        when(env.getProperty(AppConstants.JWT_EXPIRATION)).thenReturn(TestDataMaker.JWT_EXPIRATION);
        when(env.getProperty(AppConstants.JWT_SECRET)).thenReturn(TestDataMaker.JWT_SECRET);
        String token = jwtTokenUtil.generateToken(TestDataMaker.EMAIL1, new HashMap<>());
        when(env.getProperty(AppConstants.JWT_SECRET)).thenReturn(TestDataMaker.JWT_SECRET);
        String jti = jwtTokenUtil.getJtiFromToken(token);
        assertThat(jti, notNullValue());
        verify(env, times(1)).getProperty(AppConstants.JWT_EXPIRATION);
        verify(env, times(2)).getProperty(AppConstants.JWT_SECRET);
        verifyNoMoreInteractions(env);
    }
}
