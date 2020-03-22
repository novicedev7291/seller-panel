package com.seller.panel.service;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.model.Users;
import com.seller.panel.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        when(userRepository.findByEmailAndActive(TestDataMaker.EMAIL1, true)).thenReturn(null);
        when(exceptionHandler.getException("SP-1")).thenReturn(new SellerPanelException("Invalid username or password"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            userService.authenticate(TestDataMaker.EMAIL1, TestDataMaker.PASSWORD);
        });
        verify(userRepository, times(1)).findByEmailAndActive(TestDataMaker.EMAIL1, true);
        verify(exceptionHandler, times(1)).getException("SP-1");
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldInvalidateUsernameAndPasswordForWrongPassword() {
        when(userRepository.findByEmailAndActive(TestDataMaker.EMAIL1, true)).thenReturn(TestDataMaker.makeUser());
        when(exceptionHandler.getException("SP-1")).thenReturn(new SellerPanelException("Invalid username or password"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            userService.authenticate(TestDataMaker.EMAIL1, TestDataMaker.PASSWORD);
        });
        verify(userRepository, times(1)).findByEmailAndActive(TestDataMaker.EMAIL1, true);
        verify(exceptionHandler, times(1)).getException("SP-1");
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldValidateUsernameAndPassword() {
        Users expectedUser = TestDataMaker.makeUser();
        when(userRepository.findByEmailAndActive(TestDataMaker.EMAIL1, true)).thenReturn(expectedUser);
        Users actualUser = userService.authenticate(TestDataMaker.EMAIL1, expectedUser.getPassword());
        assertThat(actualUser, equalTo(expectedUser));
        verify(userRepository, times(1)).findByEmailAndActive(TestDataMaker.EMAIL1, true);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void shouldThrow400WhileForExistingEmail() {
        Users user = TestDataMaker.makeUser();
        when(userRepository.findByEmailAndActive(user.getEmail(), true)).thenReturn(user);
        when(exceptionHandler.getException("SP-4", user.getEmail())).thenReturn(new SellerPanelException("Email already exists"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            userService.createUser(user);
        });
        verify(userRepository, times(1)).findByEmailAndActive(user.getEmail(), true);
        verify(exceptionHandler, times(1)).getException("SP-4", user.getEmail());
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldCreateUser() {
        Users user = TestDataMaker.makeUser();
        when(userRepository.findByEmailAndActive(user.getEmail(), true)).thenReturn(null);
        when(userRepository.save(any(Users.class))).thenReturn(user);
        userService.createUser(user);
        verify(userRepository, times(1)).findByEmailAndActive(user.getEmail(), true);
        verify(userRepository, times(1)).save(user);
        verifyNoMoreInteractions(userRepository);
    }

}
