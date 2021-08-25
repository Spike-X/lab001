package com.aircraft.codelab.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 2021-08-18
 * Json工具包
 *
 * @author tao.zhang
 * @since 1.0
 */
public class JsonUtil {
    private JsonUtil() {
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }
}
