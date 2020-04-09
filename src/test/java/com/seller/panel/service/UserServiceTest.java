package com.seller.panel.service;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.model.Users;
import com.seller.panel.repository.UserRepository;
import com.seller.panel.util.AppConstants;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends BaseServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldInvalidateUsernameAndPasswordForWrongUsername() {
        when(userRepository.findByEmail(TestDataMaker.EMAIL1)).thenReturn(null);
        when(exceptionHandler.getException("SP-1")).thenReturn(new SellerPanelException("Invalid credentials"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            userService.authenticate(TestDataMaker.EMAIL1, TestDataMaker.PASSWORD);
        });
        verify(userRepository, times(1)).findByEmail(TestDataMaker.EMAIL1);
        verify(exceptionHandler, times(1)).getException("SP-1");
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldInvalidateUsernameAndPasswordForWrongPassword() {
        when(userRepository.findByEmail(TestDataMaker.EMAIL1)).thenReturn(TestDataMaker.makeUsers());
        when(exceptionHandler.getException("SP-1")).thenReturn(new SellerPanelException("Invalid credentials"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            userService.authenticate(TestDataMaker.EMAIL1, TestDataMaker.WRONG_PASSWORD);
        });
        verify(userRepository, times(1)).findByEmail(TestDataMaker.EMAIL1);
        verify(exceptionHandler, times(1)).getException("SP-1");
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldValidateUsernameAndPassword() {
        Users expectedUser = TestDataMaker.makeUsers();
        when(userRepository.findByEmail(TestDataMaker.EMAIL1)).thenReturn(expectedUser);
        Users actualUser = userService.authenticate(TestDataMaker.EMAIL1, TestDataMaker.PASSWORD);
        assertThat(actualUser, equalTo(expectedUser));
        verify(userRepository, times(1)).findByEmail(TestDataMaker.EMAIL1);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void shouldThrow400WhileForExistingEmail() {
        Users user = TestDataMaker.makeUsers();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(exceptionHandler.getException("SP-4", user.getEmail())).thenReturn(new SellerPanelException("Email already exists"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            userService.createUser(user);
        });
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(exceptionHandler, times(1)).getException("SP-4", user.getEmail());
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldCreateUser() {
        Users user = TestDataMaker.makeUsers();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.save(any(Users.class))).thenReturn(user);
        userService.createUser(user);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, times(1)).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void shouldThrow400WithInvalidUserWhileCallingFindUserByIdAndCompanyId() {
        when(userRepository.findByIdAndCompanyId(NumberUtils.LONG_ONE, NumberUtils.LONG_ONE)).thenReturn(null);
        when(exceptionHandler.getException("SP-5")).thenReturn(new SellerPanelException("Invalid user"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            userService.findUserByIdAndCompanyId(NumberUtils.LONG_ONE, NumberUtils.LONG_ONE);
        });
        verify(userRepository, times(1)).findByIdAndCompanyId(NumberUtils.LONG_ONE, NumberUtils.LONG_ONE);
        verify(exceptionHandler, times(1)).getException("SP-5");
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldCallFindUserByIdAndCompanyId() {
        Users actual = TestDataMaker.makeUsers();
        when(userRepository.findByIdAndCompanyId(NumberUtils.LONG_ONE, NumberUtils.LONG_ONE)).thenReturn(actual);
        Users expected = userService.findUserByIdAndCompanyId(NumberUtils.LONG_ONE, NumberUtils.LONG_ONE);
        assertThat(actual.getId(), equalTo(expected.getId()));
        assertThat(actual.getName(), equalTo(expected.getName()));
        assertThat(actual.getEmail(), equalTo(expected.getEmail()));
        assertThat(actual.getPhone(), equalTo(expected.getPhone()));
        assertThat(actual.getCountryCode(), equalTo(expected.getCountryCode()));
        assertThat(actual.getActive(), equalTo(expected.getActive()));
        verify(userRepository, times(1))
                    .findByIdAndCompanyId(NumberUtils.LONG_ONE, NumberUtils.LONG_ONE);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void findAllUsers() {
        Users actual = TestDataMaker.makeUsers();
        Pageable pageable = PageRequest.of(AppConstants.DEFAULT_PAGE, AppConstants.DEFAULT_SIZE);
        when(userRepository.findAllByCompanyIdOrderByUpdatedOnDesc(actual.getCompanyId(), pageable))
                        .thenReturn(Arrays.asList(actual));
        List<Users> expected = userService.findAllUsersByCompanyIdAndPageAndSize(actual.getCompanyId(),
                AppConstants.DEFAULT_PAGE, AppConstants.DEFAULT_SIZE);
        assertThat(NumberUtils.INTEGER_ONE, equalTo(expected.size()));
        assertThat(actual.getId(), equalTo(expected.get(NumberUtils.INTEGER_ZERO).getId()));
        assertThat(actual.getName(), equalTo(expected.get(NumberUtils.INTEGER_ZERO).getName()));
        assertThat(actual.getPhone(), equalTo(expected.get(NumberUtils.INTEGER_ZERO).getPhone()));
        assertThat(actual.getCountryCode(), equalTo(expected.get(NumberUtils.INTEGER_ZERO).getCountryCode()));
        assertThat(actual.getActive(), equalTo(expected.get(NumberUtils.INTEGER_ZERO).getActive()));
        assertThat(actual.getEmail(), equalTo(expected.get(NumberUtils.INTEGER_ZERO).getEmail()));
        verify(userRepository, times(1)).
                findAllByCompanyIdOrderByUpdatedOnDesc(actual.getCompanyId(), pageable);
        verifyNoMoreInteractions(userRepository);
    }

}
