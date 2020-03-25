package com.seller.panel.filter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomCorsFilterTest {

    @InjectMocks
    private CustomCorsFilter customCorsFilter;

    @Mock
    private HttpServletRequest servletRequest;

    @Mock
    private HttpServletResponse servletResponse;

    @Mock
    private FilterChain filterChain;

    @Mock
    private FilterConfig filterConfig;

    @Test
    public void shouldCallDoFilter() throws IOException, ServletException  {
        customCorsFilter.doFilter(servletRequest, servletResponse, filterChain);
    }

    @Test
    public void shouldCallDoFilterWithOptions() throws IOException, ServletException  {
        when(servletRequest.getMethod()).thenReturn("OPTIONS");
        customCorsFilter.doFilter(servletRequest, servletResponse, filterChain);
    }

    @Test
    public void shouldCallDestroy() throws IOException, ServletException  {
        customCorsFilter.destroy();
    }

    @Test
    public void shouldCallInit() throws IOException, ServletException  {
        customCorsFilter.init(filterConfig);
    }
}
