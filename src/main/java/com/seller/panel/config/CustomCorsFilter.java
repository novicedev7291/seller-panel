package com.seller.panel.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomCorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) res;
        final HttpServletRequest request = (HttpServletRequest) req;
        request.setAttribute("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, X-Requested-With, Accept");
        response.setHeader("Access-Control-Max-Age", "1800");
        chain.doFilter(req, res);
    }
    @Override
    public void destroy() {
        // Implementing <code>Filter</code> interface
    }
    @Override
    public void init(FilterConfig config) throws ServletException {
        // Implementing <code>Filter</code> interface
    }

}
