package com.seller.panel.service;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.model.Users;
import com.seller.panel.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldThrowInvalidUsernameOrPasswordForNoUser(){
        when(userRepository.findByEmail(TestDataMaker.EMAIL1)).thenReturn(null);
        when(messageHandler.getMessage("SP-1")).thenReturn("Invalid credentials");
        Assertions.assertThrows(OAuth2Exception.class, () -> {
            userDetailsService.loadUserByUsername(TestDataMaker.EMAIL1);
        });
        verify(userRepository, times(1)).findByEmail(TestDataMaker.EMAIL1);
        verify(messageHandler, times(1)).getMessage("SP-1");
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(messageHandler);
    }

    @Test
    public void shouldThrowInvalidUsernameOrPasswordForInActive(){
        Users user = TestDataMaker.makeUsers();
        user.setActive(false);
        when(userRepository.findByEmail(TestDataMaker.EMAIL1)).thenReturn(user);
        when(messageHandler.getMessage("SP-1")).thenReturn("Invalid credentials");
        Assertions.assertThrows(OAuth2Exception.class, () -> {
            userDetailsService.loadUserByUsername(TestDataMaker.EMAIL1);
        });
        verify(userRepository, times(1)).findByEmail(TestDataMaker.EMAIL1);
        verify(messageHandler, times(1)).getMessage("SP-1");
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(messageHandler);
    }

    @Test
    public void shouldLoadUserByUsername(){
        Users actual = TestDataMaker.makeUsers();
        when(userRepository.findByEmail(TestDataMaker.EMAIL1)).thenReturn(actual);
        UserDetails expected = userDetailsService.loadUserByUsername(TestDataMaker.EMAIL1);
        assertThat(actual.getEmail(), equalTo(expected.getUsername()));
        verify(userRepository, times(1)).findByEmail(TestDataMaker.EMAIL1);
        verifyNoMoreInteractions(userRepository);
    }
}
