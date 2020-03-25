package com.seller.panel.evaluator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomPermissionEvaluatorTest {

    @InjectMocks
    private CustomPermissionEvaluator customPermissionEvaluator;

    @Mock
    private Object object;

    @Mock
    private Authentication authentication;

    @Mock
    private Serializable serializable;

    @Test
    public void shouldReturnFalseForPermissionWithObject() {
        Boolean hasPermission = customPermissionEvaluator.hasPermission(authentication,
                                            object, object);
        assertThat(hasPermission, equalTo(false));
    }

    @Test
    public void shouldReturnFalseForPermissionWithSerializable() {
        Boolean hasPermission = customPermissionEvaluator.hasPermission(authentication,
                                serializable, "abc", object);
        assertThat(hasPermission, equalTo(false));
    }

}
