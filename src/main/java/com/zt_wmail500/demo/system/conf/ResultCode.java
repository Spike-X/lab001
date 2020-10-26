package com.zt_wmail500.demo.system.conf;

import lombok.Getter;

/**
 * @program: demo
 * @description: 响应码
 * @author: tao.zhang
 * @create: 2020-10-26 21:58
 **/
@Getter
public enum ResultCode {

    SUCCESS(1000, "操作成功"),

    FAILED(1001, "响应失败"),

    VALIDATE_FAILED(1002, "参数校验失败"),

    ERROR(5000, "未知错误");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

