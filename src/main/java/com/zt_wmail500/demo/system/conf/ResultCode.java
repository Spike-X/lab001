package com.zt_wmail500.demo.system.conf;

/**
 * @program: demo
 * @description: 响应码
 * @author: tao.zhang
 * @create: 2020-10-26 21:58
 **/
public enum ResultCode {

    SUCCESS(1000, "操作成功"),
    FAILED(1001, "响应失败"),
    VALIDATE_FAILED(1002, "参数校验失败"),
    FAIL(400, "Failure!"),
    NO_PERMISSION(403, "Need Authorities!"),//没有权限
    LOGIN_NO(402, "Need Login!"),//未登录
    LOGIN_FAIL(401, "Login Failure!"),//登录失败
    LOGIN_SUCCESS(200, "Login Success!"),//登录成功
    LOGOUT_SUCCESS(200, "Logout Success!"),//退出登录
    SESSION_EXPIRES(101, "Session Expires!"),//会话到期
    ERROR(5000, "未知错误"),
    ;

    private final int code;

    private final String message;

    ResultCode(int code, String message) {
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

