package com.aircraft.codelab.pioneer.aop.jdk;

/**
 * 2021-10-06
 *
 * @author tao.zhang
 * @since 1.0
 */
public class UsbImpl implements UsbSell {
    @Override
    public Float sell(int amount) {
        System.out.println("目标类中,执行了sell目标方法");
        return 85.0f;
    }
}
