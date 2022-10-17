package com.aircraft.codelab.pioneer.designpattern.singleton;

/**
 * 2021-11-01
 * 懒汉式 静态内部类
 *
 * @author tao.zhang
 * @since 1.0
 */
public class Singleton4 {
    private Singleton4() {
    }

    private static class SingletonHolder {
        private static final Singleton4 instance = new Singleton4();
    }

    public static Singleton4 getInstance() {
        return SingletonHolder.instance;
    }

    public void showMessage() {
        System.out.println("Hello World!");
    }
}
