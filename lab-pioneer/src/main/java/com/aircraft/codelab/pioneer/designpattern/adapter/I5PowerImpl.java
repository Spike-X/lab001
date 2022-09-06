package com.aircraft.codelab.pioneer.designpattern.adapter;

/**
 * 2021-07-16
 *
 * @author tao.zhang
 * @since 1.0
 */
public class I5PowerImpl implements I5Power {
    @Override
    public int provide5Power() {
        System.out.println("我提供5V交流电压。");
        return 5;
    }
}
