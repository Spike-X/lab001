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
 * @description: 统一返回对象实体
 * @author: tao.zhang
 * @create: 2020-10-26 21:42
 **/
public class CommonResult<T> {
    /**
     * 状态码
     */
    private int code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    protected CommonResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResult<T> success() {
        return success(ResultCode.SUCCESS.getMessage());
    }

    public static <T> CommonResult<T> success(String message) {
        return success(message,null);
    }

    public static <T> CommonResult<T> success(String message, T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(),message, data);
    }

    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED, null);
    }

    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<>(ResultCode.FAILED.getCode(), message, null);
    }

    public static <T> CommonResult<T> failed(IReturnCode returnCode) {
        return failed(returnCode, null);
    }

    public static <T> CommonResult<T> failed(IReturnCode returnCode, T data) {
        return new CommonResult<>(returnCode.getCode(),returnCode.getMessage(), data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
