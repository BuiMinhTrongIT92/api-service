package com.trong.apiservice.configuration.aspect;

import com.trong.apiservice.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before(value = "execution(* com.trong.apiservice.controller.*.*(..)) && @annotation(usingAspect)", argNames = "joinPoint, usingAspect")
    public void beforeExecuteRequest(JoinPoint joinPoint, UsingAspect usingAspect) {
        if (usingAspect.isUse()) {
            RequestAttributes s = RequestContextHolder.getRequestAttributes();
            String methodName = ((MethodInvocationProceedingJoinPoint) joinPoint).getSignature().getName();
            log.info("REQUEST FROM CLIENT: {}, CONTROLLER METHOD: {}, INFO REQUEST: {}", null != s ? ((ServletRequestAttributes) s).getRequest().getRequestURL() : "", methodName, CommonUtils.objectToString(joinPoint.getArgs()));
        }
    }

    @AfterReturning(pointcut = "execution(* com.trong.apiservice.controller.*.*(..)) && @annotation(usingAspect)", returning = "result")
    public void responseToMEDPRO(UsingAspect usingAspect, Object result) {
        if (usingAspect.isUse()) {
            log.info("RESPONSE RETURN CLIENT, INFO RESPONSE: {}", CommonUtils.objectToString(result));
        }
    }
}
