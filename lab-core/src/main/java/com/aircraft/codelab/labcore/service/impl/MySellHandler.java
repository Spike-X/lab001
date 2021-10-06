package com.aircraft.codelab.labcore.service.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 2021-10-06
 *
 * @author tao.zhang
 * @since 1.0
 */
public class MySellHandler implements InvocationHandler {
    private final Object target;

    public MySellHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object res;
        // 调用目标方法
        res = method.invoke(target, args);
        // 增强功能
        if (res != null) {
            float price = (Float) res;
            price = price + 25;
            res = price;
        }
        return res;
    }
}
