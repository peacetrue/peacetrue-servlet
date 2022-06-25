package com.github.peacetrue.servlet;

import org.springframework.http.MediaType;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

/**
 * 内容类型工具类。
 *
 * @author peace
 * @see HttpServletRequest#getContentType()
 **/
public class ContentTypeUtils {

    private ContentTypeUtils() {
    }

    /**
     * 是否原始类型。
     *
     * @param contentType 内容类型
     * @return {@code true} 如果是原始类型，否则 {@code false}
     */
    public static boolean isRaw(@Nullable String contentType) {
        return contentType != null && !isForm(contentType);
    }

    /**
     * 是否表单类型。
     *
     * @param contentType 内容类型
     * @return {@code true} 如果是表单类型，否则 {@code false}
     */
    public static boolean isForm(String contentType) {
        return isForm(MediaType.parseMediaType(contentType));
    }

    /**
     * 是否表单类型。
     *
     * @param contentType 内容类型
     * @return {@code true} 如果是表单类型，否则 {@code false}
     */
    public static boolean isForm(MediaType contentType) {
        return contentType.includes(MediaType.APPLICATION_FORM_URLENCODED)
                || contentType.includes(MediaType.MULTIPART_FORM_DATA);
    }

}
