package com.seller.panel.service;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.LoginRequest;
import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.model.Users;
import com.seller.panel.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends BaseServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldINvalidateUsernameAndPasswordForWrongUsername() {
        when(userRepository.findByEmailAndActive(TestDataMaker.EMAIL1, true)).thenReturn(null);
        when(exceptionHandler.getException("SP-5")).thenReturn(new SellerPanelException("Invalid username and password"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            userService.validateUsernameAndPassword(TestDataMaker.EMAIL1, TestDataMaker.PASSWORD);
        });
        verify(userRepository, times(1)).findByEmailAndActive(TestDataMaker.EMAIL1, true);
        verify(exceptionHandler, times(1)).getException("SP-5");
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldINvalidateUsernameAndPasswordForWrongPassword() {
        when(userRepository.findByEmailAndActive(TestDataMaker.EMAIL1, true)).thenReturn(TestDataMaker.makeUser());
        when(exceptionHandler.getException("SP-5")).thenReturn(new SellerPanelException("Invalid username and password"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            userService.validateUsernameAndPassword(TestDataMaker.EMAIL1, TestDataMaker.PASSWORD);
        });
        verify(userRepository, times(1)).findByEmailAndActive(TestDataMaker.EMAIL1, true);
        verify(exceptionHandler, times(1)).getException("SP-5");
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldValidateUsernameAndPassword() {
        Users expectedUser = TestDataMaker.makeUser();
        when(userRepository.findByEmailAndActive(TestDataMaker.EMAIL1, true)).thenReturn(expectedUser);
        Users actualUser = userService.validateUsernameAndPassword(TestDataMaker.EMAIL1, expectedUser.getPassword());
        assertThat(actualUser, equalTo(expectedUser));
        verify(userRepository, times(1)).findByEmailAndActive(TestDataMaker.EMAIL1, true);
        verifyNoMoreInteractions(userRepository);
    }

}
