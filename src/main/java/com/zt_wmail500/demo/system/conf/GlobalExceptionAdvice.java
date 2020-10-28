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

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: demo
 * @description: 全局异常
 * @author: tao.zhang
 * @create: 2020-10-26 21:24
 **/
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(APIException.class)
    public ResultInfo<String> APIExceptionHandler(APIException e) {
        return new ResultInfo<>(ResultCode.FAILED, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultInfo<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return new ResultInfo<>(ResultCode.VALIDATE_FAILED, objectError.getDefaultMessage());
    }
}
