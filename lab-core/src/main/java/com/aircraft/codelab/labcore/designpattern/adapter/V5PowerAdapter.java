package com.aircraft.codelab.labcore.designpattern.adapter;

import lombok.Data;

/**
 * 2021-07-16
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
public class V5PowerAdapter implements I5Power {
    private I220Power i220Power;

    public V5PowerAdapter(I220Power i220Power) {
        this.i220Power = i220Power;
    }

    @Override
    public int provide5Power() {
        int i = i220Power.provide220Power();
        System.out.println("适配器：我悄悄的适配了电压。");
        return 5;
    }
}
