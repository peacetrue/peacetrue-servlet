package com.github.peacetrue.servlet;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * 缓存请求体的 {@link ServletInputStream}。
 *
 * @author peace
 */
public class CachedBodyServletInputStream extends ServletInputStream {

    private final ByteArrayInputStream cachedBodyInputStream;
    private ReadListener readListener;


    /**
     * 通过缓存的请求体构造输入流。
     *
     * @param cachedBody 缓存的请求体字节数组
     */
    public CachedBodyServletInputStream(byte[] cachedBody) {
        this.cachedBodyInputStream = new ByteArrayInputStream(Objects.requireNonNull(cachedBody));
    }

    public int read() {
        int read = cachedBodyInputStream.read();
        onAllDataRead(read);
        return read;
    }

    private void onAllDataRead(int read) {
        if (readListener == null || read > -1) return;
        try {
            readListener.onAllDataRead();
        } catch (IOException e) {
            readListener.onError(e);
        }
    }

    @Override
    public boolean isFinished() {
        return cachedBodyInputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        this.readListener = readListener;
        onDataAvailable();
    }

    private void onDataAvailable() {
        if (readListener == null) return;
        try {
            readListener.onDataAvailable();
        } catch (IOException e) {
            readListener.onError(e);
        }
    }
}
