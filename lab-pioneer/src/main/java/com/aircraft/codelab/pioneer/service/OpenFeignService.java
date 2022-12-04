package com.aircraft.codelab.pioneer.service;

//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 2022-09-20
 * feign远程调用
 *
 * @author tao.zhang
 * @since 1.0
 */
//@FeignClient(name = "open", url = "${remote.http.url}")
public interface OpenFeignService {
    @GetMapping(value = "/tomcat/actuator")
    String tomcatStatus();
}
