package com.antiy.asset.util;

import org.apache.commons.compress.utils.Lists;
import org.springframework.cglib.beans.BeanCopier;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Bean转换
 *
 * @author lvliang
 * @date 2018/12/20
 */
public class BeanConvert {

    public static Object convert(Object o1, Class<?> clazz) throws IllegalAccessException, InstantiationException {
        Field[] fields = o1.getClass().getDeclaredFields();
        Object o = clazz.newInstance();
        List<Field> list = Arrays.asList(clazz.getDeclaredFields());
        List<String> fieldNames = Lists.newArrayList();
        list.forEach(f -> {
            if (!"serialVersionUID".equals(f.getName())) {
                fieldNames.add(f.getName());
            }
        });
        for (Field field : fields) {
            if (fieldNames.contains(field.getName())) {
                if (field.getName().startsWith("is")) {
                    ReflectionUtils.invokeSetterMethodWithoutIs(o, field.getName(),
                            ReflectionUtils.invokeGetterMethodWithoutIs(o1, field.getName()));
                } else {
                    ReflectionUtils.invokeSetterMethod(o, field.getName(),
                            ReflectionUtils.invokeGetterMethod(o1, field.getName()));
                }

            }
        }
        return o;
    }

    /**
     * 实体转换
     *
     * @param c1 被转换的类的字节码
     * @param c2 目标对象的字节码
     * @param o1 被转换对象
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object convert(Class c1, Class c2, Object o1) throws IllegalAccessException, InstantiationException {
        BeanCopier copier = BeanCopier.create(c1, c2, false);
        Object o2 = c2.newInstance();
        copier.copy(o1, o2, null);
        return o2;
    }


    /**
     * 实体转换
     *
     * @param target 被转换的对象
     * @param c2     目标对象的字节码
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object convertBean(Object target, Class<?> c2) {
        if (target == null) {
            return null;
        }
        BeanCopier copier = BeanCopier.create(target.getClass(), c2, false);
        Object o2 = null;
        try {
            o2 = c2.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        copier.copy(target, o2, null);
        return o2;
    }

    /**
     * 实体转换
     *
     * @param target 被转换的对象
     * @param c2     目标对象的字节码
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static List<Object> convert(List<?> target, Class<?> c2) {
        if (target == null || target.isEmpty()) {
            return null;
        }
        BeanCopier copier = BeanCopier.create(target.get(0).getClass(), c2, false);
        List<Object> list = Lists.newArrayList();
        Object o2 = null;
        for (Object o : target) {
            try {
                o2 = c2.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            copier.copy(o, o2, null);
            list.add(o2);
        }
        return list;
    }
}
