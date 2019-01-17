package com.antiy.asset.aop;

import java.lang.annotation.*;

import com.antiy.asset.vo.enums.AssetLogOperationType;

/**
 * @auther: zhangbing
 * @date: 2019/1/17 16:19
 * @description:
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface AssetLog {
    /**
     * 日志的描述
     * @return
     */
    String description() default "";

    /**
     * 日志的操作类型
     * @return
     */
    AssetLogOperationType operationType() default AssetLogOperationType.QUERY;
}
