package com.seller.panel.config;

import com.seller.panel.util.AppConstants;
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
            Cookie headerPayload = new Cookie(AppConstants.HEADER_PAYLOAD, tokenPart[0].concat(".").concat(tokenPart[1]));
            headerPayload.setMaxAge(1800);
            response.addCookie(headerPayload);
            Cookie signature = new Cookie(AppConstants.SIGNATURE, tokenPart[2]);
            signature.setMaxAge(86400);
            signature.setHttpOnly(true);
            response.addCookie(signature);
        }
    }

}
