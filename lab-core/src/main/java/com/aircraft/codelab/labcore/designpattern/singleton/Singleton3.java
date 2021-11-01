package com.aircraft.codelab.labcore.designpattern.singleton;

/**
 * 2021-11-01
 * 懒汉式(dcl,即 double-checked locking)
 *
 * @author tao.zhang
 * @since 1.0
 */
public class Singleton3 {
    private Singleton3() {
    }

    private static volatile Singleton3 instance;

    public static Singleton3 getInstance() {
        if (instance == null) {
            synchronized (Singleton3.class) {
                if (instance == null) {
                    instance = new Singleton3();
                }
            }
        }
        return instance;
    }

    public void showMessage() {
        System.out.println("Hello World!");
    }
}
