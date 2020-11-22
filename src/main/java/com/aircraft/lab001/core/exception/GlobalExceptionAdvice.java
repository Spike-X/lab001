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

package com.aircraft.lab001.core.exception;

import com.aircraft.lab001.core.common.CommonResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @description: 统一异常捕获
 * @author: spikeX
 * @create: 2020-11-03
 **/
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(ApiException.class)
    public CommonResult<String> APIExceptionHandler(ApiException e) {
        if (e.getReturnCode() != null) {
            return CommonResult.failed(e.getReturnCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<?> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return CommonResult.failed(message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResult<?> ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String message;
        List<String> list = new ArrayList<>();
        violations.forEach(x -> list.add(x.getInvalidValue() + " 请求参数错误," + x.getMessage()));
        message = String.join(",", list);
//        message = violations.stream().map(ConstraintViolation::getMessage)
//                .collect(Collectors.joining(","));
        return CommonResult.failed(message);
    }
}
