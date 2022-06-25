package com.github.peacetrue.servlet;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author peace
 **/
class CachedBodyFilterTest {

    @Test
    void doFilter() throws ServletException, IOException {
        CachedBodyFilter cachedBodyFilter = new CachedBodyFilter();
        cachedBodyFilter.init(Mockito.mock(FilterConfig.class));

        cachedBodyFilter.doFilter(Mockito.mock(ServletRequest.class), Mockito.mock(ServletResponse.class), Mockito.mock(FilterChain.class));
        cachedBodyFilter.doFilter(Mockito.mock(HttpServletRequest.class), Mockito.mock(HttpServletResponse.class), Mockito.mock(FilterChain.class));

        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(httpServletRequest.getContentType()).thenReturn(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        cachedBodyFilter.doFilter(httpServletRequest, Mockito.mock(HttpServletResponse.class), Mockito.mock(FilterChain.class));

        Mockito.when(httpServletRequest.getContentType()).thenReturn(MediaType.MULTIPART_FORM_DATA_VALUE);
        cachedBodyFilter.doFilter(httpServletRequest, Mockito.mock(HttpServletResponse.class), Mockito.mock(FilterChain.class));

        Mockito.when(httpServletRequest.getContentType()).thenReturn(MediaType.APPLICATION_JSON_VALUE);
        cachedBodyFilter.doFilter(httpServletRequest, Mockito.mock(HttpServletResponse.class), Mockito.mock(FilterChain.class));

        cachedBodyFilter.destroy();
    }
}
