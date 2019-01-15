package com.antiy.asset.aop;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.antiy.common.aop.AbstractParamValidateAop;
import com.antiy.common.exception.RequestParamValidateException;

/**
 * @auther: zhangbing
 * @date: 2019/1/15 09:40
 * @description: 最基础的验证参数的接口, 采用hibernate jsr注解进行验证
 */
@Component
@Aspect
@Order(10)
public class BasicValidateAop extends AbstractParamValidateAop {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public <T> void validate(T param) {
        Set<ConstraintViolation<T>> violations = validator.validate(param);
        Iterator<ConstraintViolation<T>> iterator = violations.iterator();
        if (iterator.hasNext()) {
            throw new RequestParamValidateException(iterator.next().getMessage());
        }
    }
}
