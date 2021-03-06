package com.seller.panel.controller;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.UserRequest;
import com.seller.panel.dto.UserResponse;
import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.model.Users;
import com.seller.panel.service.UserService;
import com.seller.panel.util.AppConstants;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void shouldThrow400ForAdditionalInfoFoundInTokenWhileFindUserById() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(this.request.getAttribute(AppConstants.ADDITIONAL_INFO)).thenReturn(new HashMap<>());
        when(exceptionHandler.getException("SP-9")).thenReturn(new SellerPanelException("Company not found in access token"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            userController.findUserById(NumberUtils.LONG_ONE);
        });
        verify(this.request, times(1)).getAttribute(AppConstants.ADDITIONAL_INFO);
        verify(exceptionHandler, times(1)).getException("SP-9");
        verifyNoMoreInteractions(this.request);
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldFindUserById() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Users actual = TestDataMaker.makeUsers();
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
        verify(this.request, times(1)).getAttribute(AppConstants.ADDITIONAL_INFO);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(this.request);
    }

    @Test
    public void shouldThrow400ForPasswordMismatchWhileCreatingUser() {
        when(exceptionHandler.getException("SP-3")).thenReturn(new SellerPanelException("Passwords are not equal"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            userController.createUsers(TestDataMaker.makeUserRequestWithMismatchInPassword());
        });
        verify(exceptionHandler, times(1)).getException("SP-3");
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldCreateUser() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        UserRequest actual = TestDataMaker.makeUserRequest();
        when(userService.createUser(any(Users.class))).thenReturn(NumberUtils.LONG_ONE);
        when(this.request.getAttribute(AppConstants.ADDITIONAL_INFO)).thenReturn(TestDataMaker.makeAdditionalInfo());
        ResponseEntity<UserResponse> responseEntity = userController.createUsers(actual);
        assertThat(HttpStatus.CREATED.value(), equalTo(responseEntity.getStatusCodeValue()));
        UserResponse expected = responseEntity.getBody();
        assertThat(NumberUtils.LONG_ONE, equalTo(expected.getId()));
        assertThat(actual.getName(), equalTo(expected.getName()));
        assertThat(actual.getEmail(), equalTo(expected.getEmail()));
        assertThat(actual.getPhone(), equalTo(expected.getPhone()));
        assertThat(actual.getCountryCode(), equalTo(expected.getCountryCode()));
        assertThat(Boolean.TRUE, equalTo(expected.getActive()));
        verify(userService, times(1)).createUser(any(Users.class));
        verify(this.request, times(1)).getAttribute(AppConstants.ADDITIONAL_INFO);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(this.request);
    }

    @Test
    public void shouldAllUsers() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Users actual = TestDataMaker.makeUsers();
        Map<String, Object> additionalInfo =  TestDataMaker.makeAdditionalInfo();
        additionalInfo.put(AppConstants.COMPANY_ID, actual.getCompanyId());
        when(userService.findAllUsersByCompanyIdAndPageAndSize(actual.getCompanyId(), AppConstants.DEFAULT_PAGE,
                AppConstants.DEFAULT_SIZE)).thenReturn(Arrays.asList(actual));
        when(this.request.getAttribute(AppConstants.ADDITIONAL_INFO)).thenReturn(additionalInfo);
        ResponseEntity<List<UserResponse>> responseEntity = userController.
                findAllUserByPageAndSize(AppConstants.DEFAULT_PAGE, AppConstants.DEFAULT_SIZE);
        assertThat(HttpStatus.OK.value(), equalTo(responseEntity.getStatusCodeValue()));
        List<UserResponse> expected = responseEntity.getBody();
        assertThat(NumberUtils.INTEGER_ONE, equalTo(expected.size()));
        assertThat(actual.getId(), equalTo(expected.get(NumberUtils.INTEGER_ZERO).getId()));
        assertThat(actual.getName(), equalTo(expected.get(NumberUtils.INTEGER_ZERO).getName()));
        assertThat(actual.getEmail(), equalTo(expected.get(NumberUtils.INTEGER_ZERO).getEmail()));
        assertThat(actual.getPhone(), equalTo(expected.get(NumberUtils.INTEGER_ZERO).getPhone()));
        assertThat(actual.getCountryCode(), equalTo(expected.get(NumberUtils.INTEGER_ZERO).getCountryCode()));
        assertThat(actual.getActive(), equalTo(expected.get(NumberUtils.INTEGER_ZERO).getActive()));
        verify(userService, times(1)).
                findAllUsersByCompanyIdAndPageAndSize(actual.getCompanyId(),
                        AppConstants.DEFAULT_PAGE, AppConstants.DEFAULT_SIZE);
        verify(this.request, times(1)).getAttribute(AppConstants.ADDITIONAL_INFO);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(this.request);
    }

}
