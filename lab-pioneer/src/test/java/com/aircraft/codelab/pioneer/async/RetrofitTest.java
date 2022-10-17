package com.aircraft.codelab.pioneer.async;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 2022-02-20
 *
 * @author tao.zhang
 * @since 1.0
 */
@SpringBootTest
public class RetrofitTest {
    @Resource
    private RetrofitCallBackService retrofitCallBackService;

    @Test
    void downLoad() {
        retrofitCallBackService.downLoadFile();
    }
}
