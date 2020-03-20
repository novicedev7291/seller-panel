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
public class MessageHandlerTest {

    @InjectMocks
    private MessageHandler messageHandler;

    @Mock
    private MessageSource messageSource;

     @Test
    public void shouldGetMessage() {
        when(messageSource.getMessage("test", null, LocaleContextHolder.getLocale())).thenReturn("error occurred");
        String actualMessage = messageHandler.getMessage("test");
        assertThat(actualMessage, equalTo("error occurred"));
        verify(messageSource, times(1)).getMessage("test", null, LocaleContextHolder.getLocale());
        verifyNoMoreInteractions(messageSource);
    }

}
