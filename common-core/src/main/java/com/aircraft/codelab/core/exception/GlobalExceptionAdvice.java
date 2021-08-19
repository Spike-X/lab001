package com.aircraft.codelab.core.exception;

import com.aircraft.codelab.core.entities.CommonResult;
import com.aircraft.codelab.core.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
    /**
     * ApiException
     *
     * @param error ApiException
     * @return CommonResult
     */
    @ExceptionHandler(ApiException.class)
    public CommonResult<String> handleException(ApiException error) {
        log.warn("ApiException:", error);
        if (error.getReturnCode() != null) {
            return CommonResult.failed(error.getReturnCode());
        }
        return CommonResult.failed(error.getMessage());
    }

    /**
     * MethodArgumentNotValidException
     *
     * @param error MethodArgumentNotValidException
     * @return CommonResult
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<String> handleException(MethodArgumentNotValidException error) {
        log.warn("MethodArgumentNotValidException:", error);
        BindingResult bindingResult = error.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return CommonResult.failed(message);
    }

    /**
     * ConstraintViolationException
     *
     * @param error ConstraintViolationException
     * @return CommonResult
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResult<String> handleException(ConstraintViolationException error) {
        log.warn("ConstraintViolationException:", error);
        String message;
        message = error.getConstraintViolations().stream()
                .map(constraint -> String.format(Locale.ROOT, "%s value '%s' %s", constraint.getPropertyPath(), constraint.getInvalidValue(), constraint.getMessage()))
                .collect(Collectors.joining(","));
        return CommonResult.failed(message);
    }

    /**
     * Exception
     *
     * @param error Exception
     * @return CommonResult
     */
    @ExceptionHandler(Exception.class)
    public CommonResult<String> handleException(Exception error) {
        log.error("Exception:", error);
        return CommonResult.failed(ResultCode.INTERNAL_SERVER_ERROR);
    }
}
