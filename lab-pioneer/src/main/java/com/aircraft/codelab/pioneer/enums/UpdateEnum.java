package com.aircraft.codelab.pioneer.enums;

/**
 * 2021-11-04
 *
 * @author tao.zhang
 * @since 1.0
 */
public enum UpdateEnum {
    PRICE_ADD(1),
    PRICE_SUB(-1),
    ;

    private final int code;

    UpdateEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
