package com.github.peacetrue.servlet;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

/**
 * 缓存请求体的 {@link ServletRequest}。
 *
 * @author peace
 */
public class CachedBodyServletRequest extends ServletRequestWrapper {

    private final byte[] cachedBody;

    /**
     * 构造缓存请求体的请求。
     *
     * @param request 请求
     * @throws IOException 读取输入流时发生异常
     */
    public CachedBodyServletRequest(ServletRequest request) throws IOException {
        this(request, CachedBodyUtils.getCachedBody(request));
    }

    /**
     * 构造缓存请求体的请求。
     *
     * @param request    请求
     * @param cachedBody 缓存的请求体
     */
    public CachedBodyServletRequest(ServletRequest request, byte[] cachedBody) {
        super(request);
        this.cachedBody = Objects.requireNonNull(cachedBody);
    }

    @Override
    public ServletInputStream getInputStream() {
        return CachedBodyUtils.getInputStream(cachedBody);
    }

    @Override
    public BufferedReader getReader() {
        return CachedBodyUtils.getReader(cachedBody);
    }

}
