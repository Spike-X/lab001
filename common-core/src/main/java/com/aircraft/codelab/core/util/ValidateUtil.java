package com.aircraft.codelab.core.util;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 2021-08-19
 *
 * @author tao.zhang
 * @since 1.0
 */
public class ValidateUtil {
    private ValidateUtil() {
    }

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验实体 默认分组
     *
     * @param bean object
     * @param <T>  object
     */
    public static <T> void validate(T bean) {
        Set<ConstraintViolation<T>> validateSet = VALIDATOR.validate(bean);
        if (validateSet.size() > 0) {
            throw new ConstraintViolationException(validateSet);
        }
    }

    /**
     * 校验实体 自定义分组
     *
     * @param bean   object
     * @param groups 分组
     * @param <T>    object
     */
    public static <T> void validate(T bean, Class<?>... groups) {
        Set<ConstraintViolation<T>> validateSet = VALIDATOR.validate(bean, groups);
        if (validateSet.size() > 0) {
            throw new ConstraintViolationException(validateSet);
        }
    }

    /**
     * 校验指定实体属性 自定义分组
     *
     * @param bean      object
     * @param filedName 属性名
     * @param groups    分组
     * @param <T>       object
     */
    public static <T> void validate(T bean, String filedName, Class<?>... groups) {
        Set<ConstraintViolation<T>> validateSet = VALIDATOR.validateProperty(bean, filedName, groups);
        if (validateSet.size() > 0) {
            throw new ConstraintViolationException(validateSet);
        }
    }
}
