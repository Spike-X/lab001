package com.aircraft.codelab.pioneer.designpattern.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 2021-01-19
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Service("odb")
public class OdbppServiceImpl implements TransService {

    @Override
    public boolean transform() {
        log.info("odb转换成功");
        return true;
    }
}
