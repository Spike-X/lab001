package com.aircraft.codelab.core.annotation;

import cn.hutool.core.date.DatePattern;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 2022-10-03
 * 日期校验
 *
 * @author tao.zhang
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Constraint(validatedBy = {TimeFormatValidator.class})
@Repeatable(TimeFormat.List.class)
public @interface TimeFormat {
    String message() default "不合法的日期格式";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * "yyyy-MM-dd HH:mm:ss","yyyy-MM-dd" and so on...
     */
    String pattern() default DatePattern.NORM_DATETIME_PATTERN;

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        TimeFormat[] value();
    }
}
