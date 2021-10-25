package com.aircraft.codelab.labcore.service;

import java.util.Arrays;

/**
 * 2021-10-25
 *
 * @author tao.zhang
 * @since 1.0
 */
public enum TransEnum {
    /**
     * odb
     */
    FILE_ODB(20, "odb"),

    /**
     * val
     */
    FILE_VAL(30, "val"),
    ;

    private final int code;

    private final String message;

    TransEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static TransEnum getInstance(int code) {
        return Arrays.stream(TransEnum.values()).filter(x -> x.code == code).findFirst().orElse(null);
    }
}
