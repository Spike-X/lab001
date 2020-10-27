package com.zt_wmail500.demo.system.conf;

/**
 * @program: demo
 * @description: 自定义异常
 * @author: tao.zhang
 * @create: 2020-10-26 21:32
 **/
public class APIException extends RuntimeException {

    private final int code;

    private final String message;

    public APIException() {
        this(ResultCode.FAILED.getCode(), "接口错误");
    }

    public APIException(String message) {
        this(ResultCode.FAILED.getCode(), message);
    }

    public APIException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

