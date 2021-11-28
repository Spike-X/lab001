package com.aircraft.codelab.labcore.designpattern.singleton;

/**
 * 2021-11-01
 * 懒汉式
 *
 * @author tao.zhang
 * @since 1.0
 */
public class Singleton2 {
    private Singleton2() {
    }

    private static Singleton2 instance;

    public static synchronized Singleton2 getInstance() {
        if (instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }

    public void showMessage() {
        System.out.println("Hello World!");
    }
}
