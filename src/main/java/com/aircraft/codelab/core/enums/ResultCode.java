/*
 * Copyright (c) 2020, Tao Zhang (zt_wmail500@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aircraft.codelab.core.enums;

import com.aircraft.codelab.core.service.IReturnCode;

/**
 * @description: 统一响应码枚举
 * @author: spikeX
 * @create: 2020-11-01
 **/
public enum ResultCode implements IReturnCode {

    SUCCESS(200, "请求成功"),
    BAD_REQUEST(400, "请求错误，请修正请求"),
    UNAUTHORIZED(401, "用户未登录"),
    FORBIDDEN(403, "未授权的请求"),
    NOT_FOUND(404, "请求资源未找到"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),

    FAILURE(1001, "请求失败"),
    VALIDATE_FAILURE(1002, "参数校验失败"),
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
