package com.seller.panel.service;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.model.Users;
import com.seller.panel.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldLoadUserByUsername(){
        Users actual = TestDataMaker.makeUsers();
        when(userRepository.findByEmailAndActive(TestDataMaker.EMAIL1, true)).thenReturn(actual);
        UserDetails expected = userDetailsService.loadUserByUsername(TestDataMaker.EMAIL1);
        assertThat(actual.getEmail(), equalTo(expected.getUsername()));
        verify(userRepository, times(1)).findByEmailAndActive(TestDataMaker.EMAIL1, true);
        verifyNoMoreInteractions(userRepository);
    }
}
