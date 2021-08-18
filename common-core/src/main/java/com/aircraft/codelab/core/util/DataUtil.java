package com.aircraft.codelab.core.util;

import com.aircraft.codelab.core.service.DatePattern;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 2021-08-18
 * 时间工具类
 *
 * @author tao.zhang
 * @since 1.0
 */
public class DataUtil {
    public static String getDateTimeNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);
        return LocalDateTime.now().format(formatter);
    }

    public static String getDateTimeNow(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.now().format(formatter);
    }
}
