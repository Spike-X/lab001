package com.aircraft.codelab.core.util;

import cn.hutool.core.date.DatePattern;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 2021-08-18
 * 时间工具类
 *
 * @author tao.zhang
 * @since 1.0
 */
public class DateUtil {
    private DateUtil() {
    }

    public static String getDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
    }

    public static String getDate(String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);
        return formatter.format(LocalDateTime.now());
    }

    public static String getDateTime(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(LocalDateTime.now());
    }
}
