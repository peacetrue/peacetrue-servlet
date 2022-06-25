package com.github.peacetrue.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 缓存请求体的 {@link Filter}，只缓存原始内容类型的请求。
 *
 * @author peace
 **/
@Slf4j
public class CachedBodyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest wrapper = wrapper(request);
        if (request != wrapper) log.debug("wrapped to cached body request: {} -> {}", request, wrapper);
        chain.doFilter(wrapper, response);
    }

    static ServletRequest wrapper(ServletRequest request) throws IOException {
        return request instanceof HttpServletRequest ? wrapper((HttpServletRequest) request) : request;
    }

    static HttpServletRequest wrapper(HttpServletRequest request) throws IOException {
        return ContentTypeUtils.isRaw(request.getContentType()) ? CachedBodyUtils.wrapper(request) : request;
    }

    @Override
    public void destroy() {
    }
}
