package com.aircraft.codelab.core.annotation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;

/**
 * 2022-10-03
 * 日期校验实现类
 *
 * @author tao.zhang
 * @since 1.0
 */
public class TimeFormatValidator implements ConstraintValidator<TimeFormat, String> {
    private String pattern;

    @Override
    public void initialize(TimeFormat constraintAnnotation) {
        pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isNotBlank(s)) {
            try {
                DateTimeFormatter.ofPattern(pattern).parse(s);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
