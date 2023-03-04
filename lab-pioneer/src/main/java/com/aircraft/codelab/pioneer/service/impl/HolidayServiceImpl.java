package com.aircraft.codelab.pioneer.service.impl;

import cn.hutool.core.date.DatePattern;
import com.aircraft.codelab.core.exception.ApiException;
import com.aircraft.codelab.core.util.DateUtil;
import com.aircraft.codelab.pioneer.pojo.dto.HolidayDto;
import com.aircraft.codelab.pioneer.pojo.entity.LegalDate;
import com.aircraft.codelab.pioneer.service.HolidayClient;
import com.aircraft.codelab.pioneer.service.HolidayService;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 2022-12-17
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Service
public class HolidayServiceImpl implements HolidayService {
    @Resource
    private HolidayClient holidayClient;

    @Override
    public void create(String year) {
        if (StringUtils.isBlank(year)) {
            int todayYear = LocalDate.now().getYear();
            year = String.valueOf(todayYear);
        }
        log.info("year :{}", year);
        HolidayDto holidayDto = null;
        try {
            // 获取指定年份法定节假、调休日
            holidayDto = holidayClient.holidayAllYear(year);
            if (!"0".equals(holidayDto.getCode())) {
                throw new ApiException("获取指定年份节假日信息失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        if (Objects.isNull(holidayDto)) {
            return;
        }
        List<LegalDate> legalDateList = new ArrayList<>(10);
        Map<String, HolidayDto.Vacation> holidayMap = holidayDto.getHoliday();
        holidayMap = holidayMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(String::compareTo))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        for (Map.Entry<String, HolidayDto.Vacation> entry : holidayMap.entrySet()) {
            HolidayDto.Vacation vacation = entry.getValue();
            Boolean holiday = vacation.getHoliday();
//            String date = vacation.getDate();
            LocalDate date = vacation.getDate();
            String name = vacation.getName();
            LegalDate legalDate = new LegalDate();
//            legalDate.setLegalDate(LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE));
            legalDate.setLegalDate(date);
            legalDate.setDescription(name);
            if (holiday) {
                legalDate.setDateType(1);
            } else {
                legalDate.setDateType(2);
            }
            legalDateList.add(legalDate);
        }
        log.info("legalDateList: {}", JSONObject.toJSONString(legalDateList));
    }

    @Override
    public String getWorkDay(String startDate, String intervalDays) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN);
        LocalDate targetDate = LocalDate.parse(startDate, formatter);
        int todayYear = targetDate.getYear();
        String year = String.valueOf(todayYear);
        HolidayDto holidayDto = null;
        try {
            // 获取指定年份法定节假、调休日
            holidayDto = holidayClient.holidayAllYear(year);
            if (!"0".equals(holidayDto.getCode())) {
                throw new ApiException("获取指定年份节假日信息失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        Map<LocalDate, HolidayDto.Vacation> legalHoliday = new LinkedHashMap<>(41);
        Map<LocalDate, HolidayDto.Vacation> extraWorkday = new LinkedHashMap<>(10);
        if (Objects.nonNull(holidayDto)) {
            Map<String, HolidayDto.Vacation> holidayMap = holidayDto.getHoliday();
            holidayMap = holidayMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey(String::compareTo))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            for (Map.Entry<String, HolidayDto.Vacation> entry : holidayMap.entrySet()) {
                HolidayDto.Vacation vacation = entry.getValue();
                Boolean holiday = vacation.getHoliday();
//                String date = vacation.getDate();
                LocalDate date = vacation.getDate();
                String name = vacation.getName();
                if (holiday) {
                    legalHoliday.put(date, vacation);
                } else {
                    extraWorkday.put(date, vacation);
                }
            }
        }

        List<LocalDate> legalHolidayList = new ArrayList<>(legalHoliday.keySet());
        List<LocalDate> extraWorkdayList = new ArrayList<>(extraWorkday.keySet());

        int days;
        if (StringUtils.isBlank(intervalDays)) {
            days = 1;
        } else {
            days = Integer.parseInt(intervalDays);
        }
        int times = 0;
        // 包括开始日期
        while (times < days) {
            if (!DateUtil.isHoliday(targetDate, legalHolidayList) && !DateUtil.isWeekend(targetDate)
                    || DateUtil.isExtraWorkday(targetDate, extraWorkdayList)) {
                times++;
            }
            if (times == days) {
                return targetDate.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
            }
            targetDate = targetDate.plusDays(1L);
        }
        return null;
    }

    public String includeStartDate(LocalDate startDate, String intervalDays,
                                   List<LocalDate> legalHolidayList, List<LocalDate> extraWorkdayList) {
        int days;
        if (StringUtils.isBlank(intervalDays) || Integer.parseInt(intervalDays) < 1) {
            days = 1;
        } else {
            days = Integer.parseInt(intervalDays);
        }
        int times = 0;
        // 包括开始日期
        while (times < days) {
            if (!DateUtil.isHoliday(startDate, legalHolidayList) && !DateUtil.isWeekend(startDate)
                    || DateUtil.isExtraWorkday(startDate, extraWorkdayList)) {
                times++;
            }
            if (times == days) {
                return startDate.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
            }
            startDate = startDate.plusDays(1L);
        }
        return null;
    }

    public String excludeStartDate(LocalDate startDate, String intervalDays,
                                   List<LocalDate> legalHolidayList, List<LocalDate> extraWorkdayList) {
        int days;
        if (StringUtils.isBlank(intervalDays) || Integer.parseInt(intervalDays) < 1) {
            days = 1;
        } else {
            days = Integer.parseInt(intervalDays);
        }
        int times = 0;
        // 不包括开始日期
        while (times < days) {
            startDate = startDate.plusDays(1L);
            if (!DateUtil.isHoliday(startDate, legalHolidayList) && !DateUtil.isWeekend(startDate)
                    || DateUtil.isExtraWorkday(startDate, extraWorkdayList)) {
                times++;
            }
        }
        return startDate.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
    }

}
