package com.aircraft.codelab.core.service;

/**
 * 2021-08-19
 *
 * @author tao.zhang
 * @since 1.0
 */
public interface DatePattern {
    String NORM_DATE_PATTERN = "yyyy-MM-dd";
    String NORM_TIME_PATTERN = "HH:mm:ss";
    String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    String NORM_DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    String CHINESE_DATE_PATTERN = "yyyy年MM月dd日";
    String CHINESE_DATE_TIME_PATTERN = "yyyy年MM月dd日HH时mm分ss秒";
    String PURE_DATETIME_PATTERN = "yyyyMMddHHmmss";
    String PURE_DATETIME_MS_PATTERN = "yyyyMMddHHmmssSSS";
}
