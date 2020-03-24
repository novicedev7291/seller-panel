package com.seller.panel.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.seller.panel.exception.CustomOAuthException;
import com.seller.panel.util.AppConstants;

import java.io.IOException;

public class CustomOAuthExceptionSerializer extends StdSerializer<CustomOAuthException> {

    public CustomOAuthExceptionSerializer() {
        super(CustomOAuthException.class);
    }

    @Override
    public void serialize(CustomOAuthException value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        gen.writeStartObject();
        gen.writeStringField(AppConstants.GENERIC, value.getMessage());
        gen.writeEndObject();
    }

}
