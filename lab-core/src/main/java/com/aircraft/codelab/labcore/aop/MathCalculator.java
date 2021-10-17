package com.aircraft.codelab.labcore.aop;

import org.springframework.stereotype.Component;

/**
 * 2021-10-06
 *
 * @author tao.zhang
 * @since 1.0
 */
@Component
public class MathCalculator {
    @Auth
    public String div(int i, int j) {
        System.out.println("MathCalculator...div...计算中");
        return String.valueOf(i / j);
    }
}
