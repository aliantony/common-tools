package com.antiy.asset.aop;

import java.lang.reflect.Method;

import org.apache.commons.lang.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * 日志记录的aop,同时也是处理异常的aop
 */
@Aspect
@Component
@Order(1)
public class AssetLogAop {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetLogAop.class);

    private static final Gson   gson   = new Gson();

    @Around("@annotation(com.antiy.asset.aop.AssetLog)")
    public Object aroundCmerchantLog(ProceedingJoinPoint point) throws Throwable {
        // 创建一个计时器
        StopWatch watch = new StopWatch();
        // 计时器开始
        watch.start();
        Object[] params = point.getArgs(); // 参数
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        AssetLog assetLog = method.getAnnotation(AssetLog.class);
        String className = point.getTarget().getClass().getName();
        String methodName = method.getName();
        Object returnObj = null;
        Long time = null;
        try {
            returnObj = point.proceed();
            watch.stop();
            time = watch.getTime();
        } catch (Exception e) {
            LOGGER.error("调用的className为:{},调用方法{},请求参数为:{},返回结果为:{},日志描述:{},日志操作操作类型:{},花费的时间:{}. 发生异常", className,
                methodName, gson.toJson(params == null ? "" : params), "", assetLog.description(),
                assetLog.operationType(), time, e);
            throw e;
        }
        LOGGER.info("调用的className为:{},调用方法{},请求参数为:{},返回结果为:{},日志描述:{},日志操作操作类型:{},花费的时间:{}.", className, methodName,
            gson.toJson(params == null ? "" : params), gson.toJson(returnObj == null ? "" : returnObj),
            assetLog.description(), assetLog.operationType(), time);
        return returnObj;
    }
}
