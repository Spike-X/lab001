package com.aircraft.codelab.pioneer.proxy;

import com.aircraft.codelab.pioneer.aop.jdk.UsbSellHandler;
import com.aircraft.codelab.pioneer.aop.jdk.UsbImpl;
import com.aircraft.codelab.pioneer.aop.jdk.UsbSell;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 2021-10-06
 *
 * @author tao.zhang
 * @since 1.0
 */
public class JdkProxy {
    @Test
    void jdkProxyTest() {
        // 创建目标对象
        UsbSell factory = new UsbImpl();
        // 创建InvocationHandler对象
        InvocationHandler handler = new UsbSellHandler(factory);
        // 创建代理对象
        UsbSell proxy = (UsbSell) Proxy.newProxyInstance(factory.getClass().getClassLoader(),
                factory.getClass().getInterfaces(),
                handler);
        System.out.println("proxy: " + proxy.getClass().getName());
        // 通过代理执行方法
        Float price = proxy.sell(1);
        System.out.println("通过动态代理对象,调用方法:" + price);
    }
}
