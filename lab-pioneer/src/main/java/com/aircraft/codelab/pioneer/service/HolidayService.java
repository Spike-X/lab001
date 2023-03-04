package com.aircraft.codelab.pioneer.service;

/**
 * 2022-12-17
 * 工作日服务
 *
 * @author tao.zhang
 * @since 1.0
 */
public interface HolidayService {
    void create(String year);

    String getWorkDay(String startDate, String intervalDays);
}
