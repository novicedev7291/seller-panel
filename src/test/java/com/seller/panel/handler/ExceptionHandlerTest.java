package com.seller.panel.handler;

import com.seller.panel.exception.SellerPanelException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ExceptionHandlerTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private ExceptionHandler exceptionHandler;

    @Test
    public void shouldGetException() {
        when(messageSource.getMessage("test", null, LocaleContextHolder.getLocale())).thenReturn("error occurred");

        SellerPanelException actual = exceptionHandler.getException("test");

        assertThat(actual.getMessage(), equalTo("error occurred"));
        assertThat(actual.getMessageKey(), equalTo("test"));

        verify(messageSource, times(1)).getMessage("test", null, LocaleContextHolder.getLocale());
        verifyNoMoreInteractions(messageSource);
    }
}
