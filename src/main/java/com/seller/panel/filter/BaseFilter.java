package com.seller.panel.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.seller.panel.controller.ExceptionHandlerController;
import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.handler.ExceptionHandler;
import org.codehaus.jackson.map.util.ISO8601DateFormat;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class BaseFilter {

    private ExceptionHandlerController exceptionHandlerController;
    private ExceptionHandler exceptionHandler;

    public BaseFilter(ExceptionHandlerController exceptionHandlerController, ExceptionHandler exceptionHandler) {
        this.exceptionHandlerController = exceptionHandlerController;
        this.exceptionHandler = exceptionHandler;
    }

    protected void handleException(ServletResponse response, Exception ex) throws IOException {
        HttpServletResponse res = (HttpServletResponse) response;
        ResponseEntity<Object> errorDTO = exceptionHandlerController.handleApplicationException(ex, null);
        res.setStatus(errorDTO.getStatusCodeValue());
        res.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new ISO8601DateFormat());
        PrintWriter out = res.getWriter();
        out.print(mapper.writeValueAsString(errorDTO.getBody()));
        out.flush();
    }

    protected SellerPanelException getException(String key) {
        return exceptionHandler.getException(key);
    }

    protected SellerPanelException getException(String key, Object... params) {
        return exceptionHandler.getException(key, params);
    }

}
