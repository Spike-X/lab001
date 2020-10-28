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
 * @description: 将结果转换为封装后的对象
 * @author: tao.zhang
 * @create: 2020-10-27 21:08
 **/
public class ResultInfoBuilder {

    public static <T> ResultInfo<T> success() {
        return new ResultInfo<T>(ResultCode.SUCCESS);
    }

    public static <T> ResultInfo<T> success(ResultCode resultCode) {
        return new ResultInfo<T>(resultCode);
    }

    public static <T> ResultInfo<T> success(ResultCode resultCode, T data) {
        return new ResultInfo<T>(resultCode, data);
    }
}
