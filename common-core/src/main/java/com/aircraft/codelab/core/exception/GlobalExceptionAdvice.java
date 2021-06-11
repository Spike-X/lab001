package com.aircraft.codelab.core.exception;

import com.aircraft.codelab.core.entities.CommonResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * 2020-11-03
 * 统一异常捕获
 *
 * @author tao.zhang
 * @since 1.0
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(ApiException.class)
    public CommonResult<String> handleException(ApiException e) {
        if (e.getReturnCode() != null) {
            return CommonResult.failed(e.getReturnCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<String> handleException(MethodArgumentNotValidException e) {
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
    public CommonResult<String> handleException(ConstraintViolationException e) {
        String message;
        message = e.getConstraintViolations().stream()
                .map(x -> String.format(Locale.ROOT, "%s value '%s' %s", x.getPropertyPath(), x.getInvalidValue(), x.getMessage()))
                .collect(Collectors.joining(","));
        return CommonResult.failed(message);
    }
}
