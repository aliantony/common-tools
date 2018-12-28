package com.antiy.asset.aop;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.DataEncoder;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.LogUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @Auther: zhangbing
 * @Date: 2018/11/21 16:30
 * @Description: 加解密AOP
 */
@Component
@Aspect
@Order(99)
public class DataEncodeAop implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private static final Logger logger = LogUtils.get();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private ObjectProcessor encodeObjectProcessor = new EncodeObjectProcessor();

    private ObjectProcessor decodeObjectProcessor = new DecodeObjectProcessor();

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        Object[] newArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg == null) {
                newArgs[i] = null;
                continue;
            }

            if (arg instanceof BasicRequest) {
                // 对入参进行编码/加密
                transfer(arg, decodeObjectProcessor);
                newArgs[i] = arg;
            } else if (arg instanceof String) {
                // 处理Controller的入参为String类型的参数
                newArgs[i] = transferString(point, i, arg);
            } else {
                newArgs[i] = arg;
            }
        }

        Object result = point.proceed(newArgs);

        // 解密返回为空，则进行加密
        if (result != null) {
            transfer(result, encodeObjectProcessor);
        }
        return result;
    }

    private void transfer(Object obj, ObjectProcessor processor) {
        if (obj != null) {
            Class clazz = obj.getClass();
            do {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    transferField(obj, field, processor);
                }
                clazz = clazz.getSuperclass();
                // 如果存在父类，则处理父类的属性
            } while (clazz != null && !Object.class.equals(clazz));
        }
    }

    private Object transferString(ProceedingJoinPoint point, int index, Object arg) {
        Annotation[][] parameterAnnotations = ((MethodSignature) point.getSignature()).getMethod()
                .getParameterAnnotations();
        Annotation[] annotations = parameterAnnotations[index];
        for (Annotation annotation : annotations) {
            if (Encode.class.equals(annotation.annotationType())) {
                return encode(decodeObjectProcessor, arg, (Encode) annotation);
            }
        }
        return arg;
    }

    private void transferField(Object obj, Field field, ObjectProcessor processor) {
        Object oldVal = getProperty(obj, field);
        if (oldVal == null) {
            return;
        }

        Encode annotation = field.getAnnotation(Encode.class);
        if (annotation != null) {
            Object newVal = encode(processor, oldVal, annotation);
            setProperty(obj, field, newVal);
        } else {
            // 如果当前对象不是基础类型或者String类型，则转换该对象
            transferFieldIfNeed(processor, oldVal);
        }
    }

    private void transferFieldIfNeed(ObjectProcessor processor, Object oldVal) {
        // 基础类型和String类型无需递归处理
        if (ClassUtils.isPrimitiveOrWrapper(oldVal.getClass()) || oldVal instanceof String) {
            return;
        }

        if (oldVal instanceof List) {
            // 转换列表每一项
            List list = (List) oldVal;
            for (Object item : list) {
                transfer(item, processor);
            }
        } else {
            // 转换当前对象
            transfer(oldVal, processor);
        }
    }

    private Object encode(ObjectProcessor processor, Object oldVal, Encode annotation) {
        Class<? extends DataEncoder> converterClass = annotation.encoder();
        DataEncoder dataEncoder = applicationContext.getBean(converterClass);
        String message = annotation.message();

        try {
            return processor.process(dataEncoder, oldVal);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            throw new RequestParamValidateException(message);
        }
    }

    private void setProperty(Object obj, Field field, Object newVal) {
        try {
            PropertyUtils.setProperty(obj, field.getName(), newVal);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }

    private Object getProperty(Object obj, Field field) {
        if (!PropertyUtils.isReadable(obj, field.getName())) {
            return null;
        }

        try {
            return PropertyUtils.getProperty(obj, field.getName());
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
    }
}

/**
 * process接口
 */
interface ObjectProcessor {
    Object process(DataEncoder dataEncoder, Object input);
}

class EncodeObjectProcessor implements ObjectProcessor {
    @Override
    public Object process(DataEncoder dataEncoder, Object input) {
        // TODO password需要从ThreadLocal取数据
        return dataEncoder.encode(input, null);
    }
}

class DecodeObjectProcessor implements ObjectProcessor {
    @Override
    // TODO password需要从ThreadLocal取数据
    public Object process(DataEncoder dataEncoder, Object input) {
        return dataEncoder.decode(input, null);
    }
}
