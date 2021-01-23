package com.aircraft.codelab.labcore.service;

/**
 * 2020-11-01
 * 响应码接口
 *
 * @author tao.zhang
 * @since 1.0
 */
public interface IReturnCode {
    /**
     * 获取响应码
     *
     * @return int
     */
    int getCode();

    /**
     * 获取响应信息
     *
     * @return String
     */
    String getMessage();
}
