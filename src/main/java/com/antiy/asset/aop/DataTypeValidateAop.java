package com.antiy.asset.aop;

import java.lang.reflect.Field;

import javax.validation.Valid;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.antiy.asset.exception.BusinessException;
import com.antiy.asset.exception.RequestParamValidateException;
import com.antiy.asset.utils.LogUtils;
import com.antiy.asset.validation.FieldValidator;
import com.antiy.asset.validation.ObjectValidator;
import com.antiy.asset.validation.Validate;

/**
 * @Auther: zhangbing
 * @Date: 2018/11/22 10:35
 * @Description: 基础校验AOP
 */
@Component
@Aspect
@Order(11)
public class DataTypeValidateAop extends AbstractParamValidateAop implements ApplicationContextAware {
    private ApplicationContext  context;
    private static final Logger logger = LogUtils.get();

    @Override
    public <T> void validate(T request) {
        validateObject(request);
    }

    private void validateObject(Object request) {
        if (request == null) {
            return;
        }
        Class clazz = request.getClass();
        do {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                validateField(request, field);
            }
            clazz = clazz.getSuperclass();
            // 如果存在父类，则校验父类存在的Validate注解
        } while (clazz != null && !Object.class.equals(clazz));

        if (request instanceof ObjectValidator) {
            ((ObjectValidator) request).validate();
        }
    }

    /**
     * 对单个字段进行校验
     */
    private <T> void validateField(T request, Field field) {
        if (field.isAnnotationPresent(Valid.class)) {
            // 如果字段使用了@Valid，则校验该字段的成员变量
            validateObject(getFieldValue(field, request));
        } else {
            // 如果字段存在@Validate注解，则获取对应的FieldValidator进行校验
            Validate annotation = field.getAnnotation(Validate.class);
            if (annotation == null) {
                return;
            }

            Class<? extends FieldValidator>[] validatorsCls = annotation.validators();
            for (Class<? extends FieldValidator> validatorCls : validatorsCls) {
                FieldValidator validator = context.getBean(validatorCls);
                Object val = getFieldValue(field, request);
                if (!validator.validate(val)) {
                    throw new RequestParamValidateException(annotation.message());
                }
            }
        }

    }

    /**
     * 获取字段的值，如果为private，则先设置为可见的，获取后再设置回原来的可见性
     */
    private Object getFieldValue(Field field, Object obj) {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            // Should not happen
            logger.error(e.getMessage(), e);
            throw new BusinessException(e);
        } finally {
            field.setAccessible(accessible);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }
}
