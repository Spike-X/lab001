/*
 * Copyright (c) 2020, Tao Zhang (zt_wmail500@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
