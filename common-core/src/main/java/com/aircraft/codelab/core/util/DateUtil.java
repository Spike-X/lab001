package com.aircraft.codelab.core.util;

import cn.hutool.core.date.DatePattern;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

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

    public static String getDate(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
    }

    public static LocalDate getDate(String localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN);
        return LocalDate.parse(localDate, formatter);
    }

    public static String getDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
    }

    public static String getDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);
        return formatter.format(LocalDateTime.now());
    }

    public static String getDateTime(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(LocalDateTime.now());
    }

    public static boolean isHoliday(String currentDate, List<String> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            for (String s : list) {
                if (currentDate.equals(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isHoliday(Date currentDate, List<Date> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            for (Date date : list) {
                int compare = currentDate.compareTo(date);
                if (0 == compare) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isHoliday(LocalDate currentDate, List<LocalDate> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            for (LocalDate localDate : list) {
                if (currentDate.equals(localDate)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isExtraWorkday(LocalDate currentDate, List<LocalDate> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            for (LocalDate localDate : list) {
                if (currentDate.equals(localDate)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isWeekend(LocalDate currentDate) {
        DayOfWeek week = currentDate.getDayOfWeek();
        return week == DayOfWeek.SATURDAY || week == DayOfWeek.SUNDAY;
    }


    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DatePattern.NORM_DATE_PATTERN);
        Date date = simpleDateFormat.parse("2022-12-17 00:00:01");

        // 字符串比较
//        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        String StringDate = getDate(localDate);
//        boolean holiday = isHoliday(StringDate, Lists.newArrayList("2022-12-17 00:00:00"));

        // date比较
//        Date newDate = new Date();
//        String format = simpleDateFormat.format(newDate);
//        Date newDate1 = simpleDateFormat.parse(format);
//        boolean holiday = isHoliday(date, Lists.newArrayList(newDate1));

        // LocalDate比较
        LocalDate now = LocalDate.now();
        LocalDate localDate = now.plusDays(1L).minusDays(1L);
        boolean holiday = isHoliday(now, Lists.newArrayList(localDate));
        System.out.println(holiday);
    }
}
