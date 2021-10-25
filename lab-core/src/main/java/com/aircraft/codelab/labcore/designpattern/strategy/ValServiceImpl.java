package com.aircraft.codelab.labcore.designpattern.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 2021-01-19
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Service("val")
public class ValServiceImpl implements TransService {

    @Override
    public boolean transform() {
        log.info("val转换成功");
        return true;
    }
}
