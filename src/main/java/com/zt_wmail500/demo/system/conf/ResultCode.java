/*
 * *
 *  * Copyright (c) 2020, Tao Zhang (zt_wmail500@163.com).
 *  * <p>
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * <p>
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  * <p>
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

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

