package com.github.peacetrue.servlet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author peace
 **/
class ContentTypeUtilsTest {

    @Test
    void isRaw() {
        Assertions.assertFalse(ContentTypeUtils.isRaw(null));
        Assertions.assertFalse(ContentTypeUtils.isRaw(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        Assertions.assertFalse(ContentTypeUtils.isRaw(MediaType.MULTIPART_FORM_DATA_VALUE));
        Assertions.assertTrue(ContentTypeUtils.isRaw(MediaType.APPLICATION_JSON_VALUE));
    }

}
