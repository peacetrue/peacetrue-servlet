package com.github.peacetrue.servlet;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * @author peace
 **/
class CachedBodyServletRequestTest {

    /**
     * {@link HttpServletRequest#getParameterMap()}
     * {@link HttpServletRequest#getParameter(String)}
     * {@link HttpServletRequest#getInputStream()}
     * {@link HttpServletRequest#getReader()}
     */
    @Test
    void basic() throws IOException {
        byte[] cachedBody = RandomStringUtils.randomAlphanumeric(10).getBytes(StandardCharsets.UTF_8);
        ServletRequest servletRequest = Mockito.mock(ServletRequest.class);
        Mockito.when(servletRequest.getInputStream()).thenReturn(new CachedBodyServletInputStream(cachedBody));
        CachedBodyServletRequest cachedBodyServletRequest = new CachedBodyServletRequest(servletRequest);
        Assertions.assertArrayEquals(cachedBody, StreamUtils.copyToByteArray(cachedBodyServletRequest.getInputStream()));
        Assertions.assertArrayEquals(cachedBody, cachedBodyServletRequest.getReader().lines().collect(Collectors.joining()).getBytes(StandardCharsets.UTF_8));

        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        httpServletRequest.getParameterMap();
        httpServletRequest.getParameter("name");
        httpServletRequest.getInputStream();
        httpServletRequest.getReader();
    }

}
