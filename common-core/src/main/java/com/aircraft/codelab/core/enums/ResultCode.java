package com.aircraft.codelab.core.enums;

import com.aircraft.codelab.core.service.IReturnCode;

import java.util.Arrays;

/**
 * 2020-11-01
 * 统一响应码枚举
 *
 * @author tao.zhang
 * @since 1.0
 */
public enum ResultCode implements IReturnCode {
    /**
     * 请求成功
     */
    SUCCESS("200", "请求成功"),

    /**
     * 请求错误
     */
    BAD_REQUEST("400", "请求错误，请修正请求"),

    /**
     * 用户未登录
     */
    UNAUTHORIZED("401", "用户未登录"),

    /**
     * 未授权的请求
     */
    FORBIDDEN("403", "未授权的请求"),

    /**
     * 请求资源未找到
     */
    NOT_FOUND("404", "请求资源未找到"),

    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR("500", "服务器内部错误"),

    /**
     * 请求失败
     */
    FAILURE("1001", "请求失败"),

    /**
     * 参数校验失败
     */
    VALIDATE_FAILURE("1002", "参数校验失败"),
    ;

    private final String code;

    private final String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static ResultCode getInstance(String code) {
        return Arrays.stream(ResultCode.values())
                .filter(s -> s.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found the enum code"));
    }
}
