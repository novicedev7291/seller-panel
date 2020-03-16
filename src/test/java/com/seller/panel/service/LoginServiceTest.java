package com.seller.panel.service;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.LoginRequest;
import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.repository.UserRepository;
import com.seller.panel.util.JwtTokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest extends BaseServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Test
    public void shouldValidateAndGenerateToken() {
        when(userService.validateUsernameAndPassword(TestDataMaker.EMAIL1, TestDataMaker.PASSWORD)).thenReturn(TestDataMaker.makeUser());
        when(jwtTokenUtil.generateToken(anyString(), anyMap())).thenReturn(TestDataMaker.JWT_TOKEN);
        String token = loginService.validateDataAndGenerateToken(new LoginRequest(TestDataMaker.EMAIL1, TestDataMaker.PASSWORD));
        assertThat(token, notNullValue());
        verify(userService, times(1)).validateUsernameAndPassword(TestDataMaker.EMAIL1, TestDataMaker.PASSWORD);
        verify(jwtTokenUtil, times(1)).generateToken(anyString(), anyMap());
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(jwtTokenUtil);
    }

}
