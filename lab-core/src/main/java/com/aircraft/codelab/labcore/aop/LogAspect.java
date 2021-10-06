package com.aircraft.codelab.labcore.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

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
    @Pointcut("execution(public int com.aircraft.codelab.labcore.aop.MathCalculator.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        System.out.println("" + joinPoint.getSignature().getName() + "@Before。。。参数列表是：{" + Arrays.asList(joinPoint.getArgs()) + "}");
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
}
