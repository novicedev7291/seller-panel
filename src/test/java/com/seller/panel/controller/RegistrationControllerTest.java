package com.seller.panel.controller;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.Registration;
import com.seller.panel.service.RegistrationService;
import com.seller.panel.util.JwtTokenUtil;
import org.apache.commons.lang3.math.NumberUtils;
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
public class RegistrationControllerTest extends BaseControllerTest {

    @InjectMocks
    private RegistrationController registrationController;

    @Mock
    private RegistrationService registrationService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Test
    public void shouldRegister() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(registrationService.createCompanyAndUser(any(Registration.class))).thenReturn(NumberUtils.LONG_ONE);
        when(jwtTokenUtil.generateToken(anyString(), anyMap())).thenReturn(TestDataMaker.JWT_TOKEN);
        ResponseEntity<Void> responseEntity = registrationController.register(TestDataMaker.makeRegistrationRequest());
        assertThat(HttpStatus.CREATED.value(), equalTo(responseEntity.getStatusCodeValue()));
        verify(jwtTokenUtil, times(1)).generateToken(anyString(), anyMap());
        verifyNoMoreInteractions(registrationService);
        verifyNoMoreInteractions(jwtTokenUtil);
    }
}
