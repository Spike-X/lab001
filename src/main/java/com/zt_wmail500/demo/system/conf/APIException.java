package com.zt_wmail500.demo.system.conf;

import lombok.Getter;

/**
 * @program: demo
 * @description: 自定义异常
 * @author: tao.zhang
 * @create: 2020-10-26 21:32
 **/
@Getter //只要getter方法，无需setter
public class APIException extends RuntimeException {
    private final int code;
    private final String msg;

    public APIException() {
        this(1001, "接口错误");
    }

    public APIException(String msg) {
        this(1001, msg);
    }

    public APIException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}

