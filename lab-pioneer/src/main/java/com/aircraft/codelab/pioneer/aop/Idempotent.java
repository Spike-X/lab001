package com.aircraft.codelab.pioneer.aop;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Idempotent {
    int timeout() default 5;
    TimeUnit timeUnit() default TimeUnit.SECONDS;
    String message() default "重复请求，自动忽略";
}
