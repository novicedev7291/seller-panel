package com.seller.panel.handler;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.repository.UserRepository;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomMethodSecurityExpressionRootTest {

    @InjectMocks
    private CustomMethodSecurityExpressionRoot customMethodSecurityExpressionRoot;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Test
    public void shouldAuthorized() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        customMethodSecurityExpressionRoot.setThis(this);
        customMethodSecurityExpressionRoot.setUserRepository(userRepository);
        customMethodSecurityExpressionRoot.setFilterObject(this);
        customMethodSecurityExpressionRoot.getFilterObject();
        customMethodSecurityExpressionRoot.setReturnObject(this);
        customMethodSecurityExpressionRoot.getReturnObject();
        when(userRepository.findByEmailAndCompanyId(null, NumberUtils.LONG_ONE))
                        .thenReturn(TestDataMaker.makeUsers());
        Boolean isAuthorized = customMethodSecurityExpressionRoot.isAuthorized("Test Verb", "Test Link");
        assertThat(isAuthorized, equalTo(true));
        verify(userRepository, times(1))
                .findByEmailAndCompanyId(null, NumberUtils.LONG_ONE);
        verifyNoMoreInteractions(userRepository);
    }

    public static Long getCompanyId() {
        return NumberUtils.LONG_ONE;
    }

}
