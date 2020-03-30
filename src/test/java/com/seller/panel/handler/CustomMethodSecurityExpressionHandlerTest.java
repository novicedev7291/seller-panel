package com.seller.panel.handler;

import com.seller.panel.repository.UserRepository;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomMethodSecurityExpressionHandlerTest {

    @InjectMocks
    private CustomMethodSecurityExpressionHandler customMethodSecurityExpressionHandler;

    @Mock
    private Authentication authentication;

    @Mock
    private MethodInvocation invocation;

    @Mock
    private ApplicationContext context;

    @Test
    public void shouldCreateSecurityExpressionRoot() {
        when(context.getBean(UserRepository.class)).thenReturn(null);
        customMethodSecurityExpressionHandler.createSecurityExpressionRoot(authentication, invocation);
        verify(context, times(1)).getBean(UserRepository.class);
        verifyNoMoreInteractions(context);
    }
}
