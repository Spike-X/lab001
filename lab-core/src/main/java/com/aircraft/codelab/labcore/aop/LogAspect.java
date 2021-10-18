package com.aircraft.codelab.labcore.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
public class LogAspect {
    // 抽取公共的切入点表达式
    @Pointcut("execution(* com.aircraft.codelab.labcore.aop.MathCalculator.*(..))")
    public void pointCut() {
    }

//    @Pointcut("execution(* com.aircraft.codelab.labcore.controller.*.*(..))")
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

    @Around(value = "pointCut()")
    public Object surroundInform(ProceedingJoinPoint proceedingJoinPoint) {
        System.out.println("@Around环绕通知开始...");
        try {
            Object o = proceedingJoinPoint.proceed();
            System.out.println("方法环绕proceed，结果是 :" + o);
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }
}
