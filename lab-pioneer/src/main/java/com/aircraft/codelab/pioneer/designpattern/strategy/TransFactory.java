package com.aircraft.codelab.pioneer.designpattern.strategy;

import com.aircraft.codelab.core.util.Assert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 2021-01-19
 *
 * @author tao.zhang
 * @since 1.0
 */
@Component
public class TransFactory {
    @Resource
    private final Map<String, TransService> map = new ConcurrentHashMap<>(3);

    public TransService getResult(String type) {
        TransService transService = map.get(type);
        Assert.notNull(transService, "输入参数:%s,错误!", type);
        return transService;
    }
}
