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
class CachedBodyUtilsTest {

    @Test
    void wrapper() throws IOException {
        byte[] cachedBody = RandomStringUtils.randomNumeric(10).getBytes(StandardCharsets.UTF_8);
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(httpServletRequest.getInputStream()).thenReturn(new CachedBodyServletInputStream(cachedBody));

        CachedBodyHttpServletRequest wrapper = CachedBodyUtils.wrapper(httpServletRequest);
        Assertions.assertArrayEquals(cachedBody, StreamUtils.copyToByteArray(wrapper.getInputStream()));

        wrapper = CachedBodyUtils.wrapper(httpServletRequest, cachedBody);
        Assertions.assertArrayEquals(cachedBody, wrapper.getReader().lines().collect(Collectors.joining()).getBytes(StandardCharsets.UTF_8));

        Assertions.assertSame(wrapper, CachedBodyUtils.wrapper(wrapper));
        Assertions.assertSame(wrapper, CachedBodyUtils.wrapper(wrapper, cachedBody));

    }

    /**
     * 封装请求为缓存请求体的请求。
     *
     * @param request    请求
     * @param cachedBody 缓存的请求体
     * @return 缓存请求体的请求
     */
    private static ServletRequest wrapper(ServletRequest request, byte[] cachedBody) {
        if (request instanceof CachedBodyServletRequest
                || request instanceof CachedBodyHttpServletRequest) return request;
        return request instanceof HttpServletRequest
                ? new CachedBodyHttpServletRequest((HttpServletRequest) request, cachedBody)
                : new CachedBodyServletRequest(request, cachedBody);
    }

}
