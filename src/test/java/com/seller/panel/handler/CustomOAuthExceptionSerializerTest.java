package com.seller.panel.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.seller.panel.exception.CustomOAuthException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;



@ExtendWith(MockitoExtension.class)
public class CustomOAuthExceptionSerializerTest {

    @InjectMocks
    private CustomOAuthExceptionSerializer customOAuthExceptionSerializer;

    @Mock
    private CustomOAuthException customOAuthException;

    @Mock
    private JsonGenerator jsonGenerator;

    @Mock
    private SerializerProvider serializerProvider;

    @Test
    public void shouldSerialize() throws IOException {
        customOAuthExceptionSerializer.serialize(customOAuthException,
                                        jsonGenerator, serializerProvider);
    }
}
