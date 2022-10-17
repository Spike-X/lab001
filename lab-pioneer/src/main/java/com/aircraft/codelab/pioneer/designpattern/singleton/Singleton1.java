package com.aircraft.codelab.pioneer.designpattern.singleton;

/**
 * 2021-11-01
 * 饿汉式
 *
 * @author tao.zhang
 * @since 1.0
 */
public class Singleton1 {
    private Singleton1() {
    }

    private static final Singleton1 instance = new Singleton1();

    public static Singleton1 getInstance() {
        return instance;
    }

    public void showMessage() {
        System.out.println("Hello World!");
    }
}
