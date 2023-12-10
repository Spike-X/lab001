package com.aircraft.codelab.pioneer.cxf;

import com.aircraft.codelab.pioneer.service.cxf.weather.*;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * 2023-12-10
 * 天气查询
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@SpringBootTest
public class WeatherTest {

    @Test
    void downLoad() throws MalformedURLException {
        WeatherWS weatherWS = new WeatherWS(new URL("http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl"));
        WeatherWSSoap weatherWSSoap = weatherWS.getWeatherWSSoap();
        WeatherWSSoap weatherWSSoap12 = weatherWS.getWeatherWSSoap12();
        ArrayOfString result = weatherWSSoap12.getWeather("1977", "");
        List<String> weather = result.getString();
        log.info("查询天气结果: {}", JSON.toJSONString(weather));
    }
}
