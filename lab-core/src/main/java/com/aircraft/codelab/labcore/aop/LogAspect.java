package com.aircraft.codelab.labcore.aop;

import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @Around(value = "pointCut()")
    public Object surroundInform(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("@Around环绕通知开始...");
        LocalDateTime startTime = null;
        if (log.isDebugEnabled()) {
            startTime = LocalDateTime.now();
        }

        Object o = proceedingJoinPoint.proceed();
        if (log.isDebugEnabled()) {
            LocalDateTime endTime = LocalDateTime.now();

            try {
                RequestAttributes ra = RequestContextHolder.getRequestAttributes();
                ServletRequestAttributes sra = (ServletRequestAttributes) ra;
                HttpServletRequest request = sra.getRequest();
                LoginInfo loginInfo = WebUtils.getLoginInfoFromRequest(request);
                String userId = loginInfo == null ? "未登录用户" : loginInfo.getSubject().get("id") + "";
                String userName = loginInfo == null ? "未登录用户" : loginInfo.getSubject().get("name") + "";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                log.debug("客户端IP:{}, 调用用户id: {}, 调用用户名: {}, " +
                                "requestURI: {}, method: {}, 方法参数名: {}, " +
                                "方法参数: {}, 调用开始时间: {}, 调用结束时间: {}, 时间差(毫秒): {}",
                        new Object[]{ServletUtil.getClientIP(request), userId, userName, request.getRequestURI(), request.getMethod(),
                                String.join(",", ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames()),
                                this.methodArgsConvert(proceedingJoinPoint.getArgs()), startTime.format(formatter),
                                endTime.format(formatter), Duration.between(startTime, endTime).toMillis()});
            } catch (Exception var12) {
            }
        }
        return o;
    }

    private String methodArgsConvert(Object[] os) {
        List<String> list = new ArrayList();
        Object[] var3 = os;
        int var4 = os.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            Object o = var3[var5];
            list.add(o == null ? "null" : o.toString());
        }

        return String.join(",", list);
    }
}
