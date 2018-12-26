package com.antiy.asset.aop;

import com.antiy.asset.base.QueryCondition;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

import com.antiy.asset.base.BasicRequest;

/**
 * @Auther: zhangbing
 * @Date: 2018/11/22 10:34
 * @Description: 参数特殊校验AOP 定义
 */
public abstract class AbstractParamValidateAop {
    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object checkParamAround(ProceedingJoinPoint point) throws Throwable {
        Object[] objects = point.getArgs();
        if (null != objects) {
            for (Object object : objects) {
                if (object instanceof BasicRequest || object instanceof QueryCondition) {
                    validate(object);
                }
            }
        }
        return point.proceed();
    }

    /**
     * 具体实现校验
     * @param param
     * @param <T>
     */
    public abstract <T> void validate(T param);
}
