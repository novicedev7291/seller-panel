package com.seller.panel.controller;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.InvitationRequest;
import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.handler.ExceptionHandler;
import com.seller.panel.service.MailService;
import com.seller.panel.util.JwtTokenUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InvitationControllerTest {

    @InjectMocks
    private InvitationController invitationController;

    @Mock
    private ExceptionHandler exceptionHandler;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private MailService mailService;

    @Mock
    private Environment env;

    @Test
    public void shouldThrowProvideEmailException() {
        when(exceptionHandler.getException("SP-1")).thenReturn(new SellerPanelException("Provide email address"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            invitationController.invite(new InvitationRequest());
        });
        verify(exceptionHandler, times(1)).getException("SP-1");
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldInviteUser() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(jwtTokenUtil.generateToken(TestDataMaker.EMAIL1)).thenReturn(StringUtils.EMPTY);
        when(env.getProperty("server.port")).thenReturn(TestDataMaker.PORT);
        when(jwtTokenUtil.getJtiFromToken(StringUtils.EMPTY)).thenReturn(StringUtils.EMPTY);
        doNothing().when(mailService).sendEmail(any(String.class), any(String.class), any(String.class));
        ResponseEntity<Void> responseEntity = invitationController.invite(new InvitationRequest(TestDataMaker.EMAIL1));
        Assertions.assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCodeValue());
        verify(jwtTokenUtil, times(1)).generateToken(TestDataMaker.EMAIL1);
        verify(jwtTokenUtil, times(1)).getJtiFromToken(StringUtils.EMPTY);
        verifyNoMoreInteractions(jwtTokenUtil);
        verify(env, times(1)).getProperty("server.port");
        verifyNoMoreInteractions(env);
        verify(mailService, times(1)).sendEmail(any(String.class), any(String.class), any(String.class));
        verifyNoMoreInteractions(mailService);
    }

}
