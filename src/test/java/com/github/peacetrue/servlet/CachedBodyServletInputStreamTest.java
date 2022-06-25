package com.github.peacetrue.servlet;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author peace
 **/
class CachedBodyServletInputStreamTest {

    @Test
    void basic() throws IOException {
        byte[] cachedBody = RandomStringUtils.randomAlphanumeric(10).getBytes(StandardCharsets.UTF_8);

        CachedBodyServletInputStream servletInputStream = new CachedBodyServletInputStream(cachedBody);
        Assertions.assertFalse(servletInputStream.isFinished());
        servletInputStream.setReadListener(null);
        Assertions.assertArrayEquals(cachedBody, StreamUtils.copyToByteArray(servletInputStream));
        Assertions.assertTrue(servletInputStream.isReady());
        Assertions.assertTrue(servletInputStream.isFinished());

        servletInputStream = new CachedBodyServletInputStream(cachedBody);
        ReadListener readListener = Mockito.mock(ReadListener.class);
        servletInputStream.setReadListener(readListener);
        Assertions.assertArrayEquals(cachedBody, StreamUtils.copyToByteArray(servletInputStream));

        servletInputStream = new CachedBodyServletInputStream(cachedBody);
        Mockito.doThrow(IOException.class).when(readListener).onDataAvailable();
        Mockito.doThrow(IOException.class).when(readListener).onAllDataRead();
        servletInputStream.setReadListener(readListener);
        Assertions.assertArrayEquals(cachedBody, StreamUtils.copyToByteArray(servletInputStream));
    }

}
