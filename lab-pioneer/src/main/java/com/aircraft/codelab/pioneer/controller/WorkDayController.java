package com.aircraft.codelab.pioneer.controller;

import com.aircraft.codelab.core.entities.CommonResult;
import com.aircraft.codelab.core.enums.ResultCode;
import com.aircraft.codelab.pioneer.service.HolidayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 2022-12-17
 * 工作日
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@RestController
@Api(tags = "工作日")
@RequestMapping("/work")
public class WorkDayController {
    @Resource
    private HolidayService holidayService;

    @ApiOperation(value = "导入指定年份节假日")
    @GetMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<?> create(String year) {
        log.debug("year: {}", year);
        holidayService.create(year);
        return CommonResult.success(ResultCode.SUCCESS.getMessage());
    }

    @ApiOperation(value = "查询指定日期后N天工作日")
    @GetMapping(value = "/work-day", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<String> getWorkDay(@RequestParam("startDate") String startDate,
                                           @RequestParam("intervalDays") String intervalDays) {
        log.debug("startDate: {},intervalDays: {}", startDate, intervalDays);
        String workDay = holidayService.getWorkDay(startDate, intervalDays);
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), workDay);
    }
}
