package com.zt_wmail500.demo.webserviceTest;

import com.zt_wmail500.demo.business.service.WeatherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @program: demo
 * @description: webService接口测试
 * @author: tao.zhang
 * @create: 2020-07-04 15:01
 **/
@DisplayName("天气测试")
@SpringBootTest
public class WebServiceTest {

    @Resource
    private WeatherService weatherService;

    @Test
    public void weatherTest() {
        this.weatherService.getWeather();
    }

}
