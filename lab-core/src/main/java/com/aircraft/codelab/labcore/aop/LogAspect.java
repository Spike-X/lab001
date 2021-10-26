package com.aircraft.codelab.labcore.aop;

import cn.hutool.extra.servlet.ServletUtil;
import com.aircraft.codelab.core.service.DatePattern;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

/**
 * 2021-10-06
 * 切面类
 *
 * @author tao.zhang
 * @since 1.0
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    // 抽取公共的切入点表达式
    @Pointcut("execution(* com.aircraft.codelab.labcore.aop.MathCalculator.*(..))")
    public void pointCut() {
    }

//    @Pointcut("execution(public * com.aircraft.codelab.labcore.controller.*.*(..))")
//    public void pointCut() {
//    }

//    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) && execution(public * com.aircraft.codelab..controller..*.*(..))")
//    public void pointCut() {
//    }

    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Optional.ofNullable(attributes).ifPresent(a -> {
            HttpServletRequest request = a.getRequest();
            System.out.println("请求路径 : " + request.getRequestURL());
            System.out.println("请求方式 : " + request.getMethod());
        });
        System.out.println("方法名 : " + joinPoint.getSignature().getName());
        System.out.println("类路径 : " + joinPoint.getSignature().getDeclaringTypeName());
        System.out.println("参数 : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result) {
        System.out.println("" + joinPoint.getSignature().getName() + "@AfterReturning正常返回。。。运行结果：{" + result + "}");
    }

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void logException(Exception exception) {
        System.out.println("@AfterThrowing异常。。。异常信息：{" + exception + "}");
    }

    @After("com.aircraft.codelab.labcore.aop.LogAspect.pointCut()")
    public void logEnd() {
        System.out.println("@After。。。运行结束");
    }

    @Around("pointCut()")
    public Object surroundNotice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Optional.ofNullable(attributes).ifPresent(attribute -> {
            HttpServletRequest request = attribute.getRequest();

            log.info("clientIP:{}, userInfo: {}, userName: {}, requestURI: {}, requestMethod: {}, parameterNames: {}, parameterValues: {}",
                    ServletUtil.getClientIP(request), "userId", "userName", request.getRequestURI(), request.getMethod(),
                    String.join(",", ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames()),
                    Arrays.toString(proceedingJoinPoint.getArgs()));
        });
        LocalDateTime startTime = LocalDateTime.now();
        Object result = proceedingJoinPoint.proceed();
        LocalDateTime endTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_MS_PATTERN);
        log.info("startTime: {}, endTime: {}, duration(ms): {}", startTime.format(formatter), endTime.format(formatter),
                Duration.between(startTime, endTime).toMillis());
        return result;
    }
}
