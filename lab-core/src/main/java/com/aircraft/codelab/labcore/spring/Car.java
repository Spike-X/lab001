package com.aircraft.codelab.labcore.spring;

/**
 * 2022-07-14
 *
 * @author tao.zhang
 * @since 1.0
 */
public class Car {
    public Car() {
        System.out.println("car constructor");
    }

    public void init() {
        System.out.println("car init");
    }

    public void destroy() {
        System.out.println("car destroy");
    }
}
