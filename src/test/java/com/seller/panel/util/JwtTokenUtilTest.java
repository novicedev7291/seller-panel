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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtTokenUtilTest {

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private Environment env;

    @Test
    public void shouldGenerateJwtToken() {
        when(env.getProperty("jwt.expiration")).thenReturn(TestDataMaker.JWT_EXPIRATION);
        when(env.getProperty("jwt.secret")).thenReturn(TestDataMaker.JWT_SECRET);
        String token = jwtTokenUtil.generateToken(TestDataMaker.EMAIL1);
        Assertions.assertNotNull(token);
        verify(env, times(1)).getProperty("jwt.expiration");
        verify(env, times(1)).getProperty("jwt.secret");
        verifyNoMoreInteractions(env);
    }

    @Test
    public void shouldThrowExpiredJwtException() {
        when(env.getProperty("jwt.secret")).thenReturn(TestDataMaker.JWT_SECRET);
        Assertions.assertThrows(ExpiredJwtException.class, () -> {
            jwtTokenUtil.validateToken(TestDataMaker.JWT_EXPIRED_TOKEN, TestDataMaker.EMAIL1);
        });
        verify(env, times(1)).getProperty("jwt.secret");
        verifyNoMoreInteractions(env);
    }

    @Test
    public void shouldInvalidToken() {
        when(env.getProperty("jwt.expiration")).thenReturn(TestDataMaker.JWT_EXPIRATION);
        when(env.getProperty("jwt.secret")).thenReturn(TestDataMaker.JWT_SECRET);
        String token = jwtTokenUtil.generateToken(TestDataMaker.EMAIL1);
        when(env.getProperty("jwt.secret")).thenReturn(TestDataMaker.JWT_SECRET);
        jwtTokenUtil.validateToken(token, TestDataMaker.EMAIL2);
        verify(env, times(1)).getProperty("jwt.expiration");
        verify(env, times(2)).getProperty("jwt.secret");
        verifyNoMoreInteractions(env);
    }

    @Test
    public void shouldValidToken() {
        when(env.getProperty("jwt.expiration")).thenReturn(TestDataMaker.JWT_EXPIRATION);
        when(env.getProperty("jwt.secret")).thenReturn(TestDataMaker.JWT_SECRET);
        String token = jwtTokenUtil.generateToken(TestDataMaker.EMAIL1);
        when(env.getProperty("jwt.secret")).thenReturn(TestDataMaker.JWT_SECRET);
        jwtTokenUtil.validateToken(token, TestDataMaker.EMAIL1);
        verify(env, times(1)).getProperty("jwt.expiration");
        verify(env, times(2)).getProperty("jwt.secret");
        verifyNoMoreInteractions(env);
    }

    @Test
    public void shouldFetchSubjectFromToken() {
        when(env.getProperty("jwt.expiration")).thenReturn(TestDataMaker.JWT_EXPIRATION);
        when(env.getProperty("jwt.secret")).thenReturn(TestDataMaker.JWT_SECRET);
        String token = jwtTokenUtil.generateToken(TestDataMaker.EMAIL1);
        when(env.getProperty("jwt.secret")).thenReturn(TestDataMaker.JWT_SECRET);
        String subject = jwtTokenUtil.getSubjectFromToken(token);
        Assertions.assertEquals(TestDataMaker.EMAIL1, subject);
        verify(env, times(1)).getProperty("jwt.expiration");
        verify(env, times(2)).getProperty("jwt.secret");
        verifyNoMoreInteractions(env);
    }

    @Test
    public void shouldFetchJtiFromToken() {
        when(env.getProperty("jwt.expiration")).thenReturn(TestDataMaker.JWT_EXPIRATION);
        when(env.getProperty("jwt.secret")).thenReturn(TestDataMaker.JWT_SECRET);
        String token = jwtTokenUtil.generateToken(TestDataMaker.EMAIL1);
        when(env.getProperty("jwt.secret")).thenReturn(TestDataMaker.JWT_SECRET);
        String jti = jwtTokenUtil.getJtiFromToken(token);
        Assertions.assertNotNull(jti);
    }
}
