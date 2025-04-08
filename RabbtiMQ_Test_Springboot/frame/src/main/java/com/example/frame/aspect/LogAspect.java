package com.example.frame.aspect;

import com.alibaba.fastjson.JSON;
import com.example.frame.annotation.Systemlog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class LogAspect {
    /**
     * 设置切入点
     */
    @Pointcut("@annotation(com.example.frame.annotation.Systemlog)")
    public void pt() {

    }

    /**
     * 环绕通知
     *
     * @param joinPoint ： 可以拿到被增强方法的信息
     * @return
     * @throws Throwable
     */
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret;
        try {
            handleBefore(joinPoint);

            //这是目标方法执行完成，上一行是目标方法未执行，下一行是目标方法已经执行
            ret = joinPoint.proceed();

            handleAfter(ret);
        } finally {
            log.info("=======================end=======================" + System.lineSeparator());
        }

        return ret;
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        // 获取当前请求的属性
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取被增强方法的注解对象
        Systemlog systemlog = getSystemlog(joinPoint);

        log.info("======================Start======================");
        log.info("访问IP    : {}", request.getRemoteHost());
        log.info("请求URL   : {}", request.getRequestURL());
        log.info("请求方式   : {}", request.getMethod());
        log.info("接口描述   : {}", systemlog.description());
        log.info("请求类名   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("传入参数   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private void handleAfter(Object ret) {
        log.info("返回参数   : {}", JSON.toJSONString(ret));
    }

    /**
     * 获取注解对象
     */
    private Systemlog getSystemlog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod().getAnnotation(Systemlog.class);
    }

}
