package com.seller.panel.controller;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.User;
import com.seller.panel.dto.UserResponse;
import com.seller.panel.service.UserService;
import com.seller.panel.util.AppConstants;
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

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    public void shouldFindUserById() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        User actual = TestDataMaker.makeUser();
        when(userService.findUserByIdAndCompanyId(NumberUtils.LONG_ONE, NumberUtils.LONG_ONE)).thenReturn(actual);
        when(this.request.getAttribute(AppConstants.ADDITIONAL_INFO)).thenReturn(TestDataMaker.makeAdditionalInfo());
        ResponseEntity<UserResponse> responseEntity = userController.findUserById(NumberUtils.LONG_ONE);
        assertThat(HttpStatus.OK.value(), equalTo(responseEntity.getStatusCodeValue()));
        UserResponse expected = responseEntity.getBody();
        assertThat(actual.getId(), equalTo(expected.getId()));
        assertThat(actual.getName(), equalTo(expected.getName()));
        assertThat(actual.getEmail(), equalTo(expected.getEmail()));
        assertThat(actual.getPhone(), equalTo(expected.getPhone()));
        assertThat(actual.getCountryCode(), equalTo(expected.getCountryCode()));
        assertThat(actual.getActive(), equalTo(expected.getActive()));
        verify(userService, times(1)).findUserByIdAndCompanyId(NumberUtils.LONG_ONE, NumberUtils.LONG_ONE);
        verifyNoMoreInteractions(userService);

    }
}
