package com.seller.panel.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion
            (HttpServletRequest request, HttpServletResponse response, Object
                    handler, Exception exception) throws Exception {
        String token = (String) request.getAttribute("token");
        if(StringUtils.isNotBlank(token)) {
            String[] tokenPart = token.split("[.]");
            Cookie headerPayload = new Cookie("header.payload", tokenPart[0].concat(tokenPart[1]));
            response.addCookie(headerPayload);
            Cookie signature = new Cookie("signature", tokenPart[2]);
            headerPayload.setHttpOnly(true);
            response.addCookie(signature);
        }
    }

}
