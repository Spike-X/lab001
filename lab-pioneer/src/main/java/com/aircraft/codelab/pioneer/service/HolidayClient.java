package com.aircraft.codelab.pioneer.service;

import com.aircraft.codelab.pioneer.pojo.dto.HolidayDto;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Var;

/**
 * 2022-12-17
 * 第三方假期api
 *
 * @author tao.zhang
 * @since 1.0
 */
@BaseRequest(baseURL = "https://timor.tech")
public interface HolidayClient {
    @Get(
            url = "/api/holiday/year/{date}",
            // 作者对接口有限制,故进行UA伪装
            headers = {
                    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36"
            }
    )
    HolidayDto holidayAllYear(@Var("date") String date);
}
