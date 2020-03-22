package com.seller.panel.controller;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.LoginResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(MockitoExtension.class)
public class SingUpControllerTest extends BaseControllerTest {

    @InjectMocks
    private SignUpController signUpController;

    @Test
    public void shouldSignUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<Void> responseEntity = signUpController.signUp(TestDataMaker.makeSignUpRequest());
        assertThat(HttpStatus.CREATED.value(), equalTo(responseEntity.getStatusCodeValue()));
    }
}
