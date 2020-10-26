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
        return new ResultInfo<>(ResultCode.FAILED, e.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultInfo<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return new ResultInfo<>(ResultCode.VALIDATE_FAILED, objectError.getDefaultMessage());
    }
}
