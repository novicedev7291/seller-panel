package com.seller.panel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.InvitationRequest;
import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.handler.ExceptionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static  org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class InvitationControllerTest {

    @InjectMocks
    InvitationController invitationController;

    @Mock
    ExceptionHandler exceptionHandler;

    @Test
    public void shouldThrowProvideEmailException() {
        when(exceptionHandler.getException("SP-1")).thenReturn(new SellerPanelException("Provide email address"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            invitationController.invite(new InvitationRequest());
        });
    }

}
