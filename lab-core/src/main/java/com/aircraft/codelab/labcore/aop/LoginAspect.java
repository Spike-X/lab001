package com.aircraft.codelab.labcore.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 2021-10-17
 *
 * @author tao.zhang
 * @since 1.0
 */
@Aspect
@Component
public class LoginAspect {
    @Around("@annotation(auth)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, Auth auth) {
        // 获取注解中的值
        System.out.println("注解中的值 : " + auth.value());
        try {
            // 检验是否登录 true 已经登录  false 未登录
            boolean flag = false;
            if (flag == true) {
                return proceedingJoinPoint.proceed();
            } else {
                return "未登录";
            }
        } catch (Throwable throwable) {
            return null;
        }
    }
}
