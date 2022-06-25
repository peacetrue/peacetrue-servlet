package com.github.peacetrue.servlet;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 缓存请求体工具类。
 *
 * @author peace
 **/
public class CachedBodyUtils {

    private CachedBodyUtils() {
    }

    static byte[] getCachedBody(ServletRequest request) throws IOException {
        return StreamUtils.copyToByteArray(request.getInputStream());
    }

    static ServletInputStream getInputStream(byte[] cachedBody) {
        return new CachedBodyServletInputStream(cachedBody);
    }

    static BufferedReader getReader(byte[] cachedBody) {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(cachedBody)));
    }


    /**
     * 封装请求为缓存请求体的请求。
     *
     * @param request    请求
     * @param cachedBody 缓存的请求体
     * @return 缓存请求体的请求
     */
    public static CachedBodyHttpServletRequest wrapper(HttpServletRequest request, byte[] cachedBody) {
        return request instanceof CachedBodyHttpServletRequest
                ? (CachedBodyHttpServletRequest) request
                : new CachedBodyHttpServletRequest(request, cachedBody);
    }

    /**
     * 封装请求为缓存请求体的请求。
     *
     * @param request 请求
     * @return 缓存请求体的请求
     * @throws IOException 读取输入流时发生异常
     */
    public static CachedBodyHttpServletRequest wrapper(HttpServletRequest request) throws IOException {
        return request instanceof CachedBodyHttpServletRequest
                ? (CachedBodyHttpServletRequest) request
                : new CachedBodyHttpServletRequest(request);
    }

}
