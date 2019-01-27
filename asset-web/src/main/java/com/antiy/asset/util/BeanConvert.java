package com.antiy.asset.util;

import com.antiy.common.encoder.Encode;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Bean转换
 *
 * @author lvliang
 * @date 2018/12/20
 */
public class BeanConvert {
    private static final String SETTER_PREFIX = "set";
    private static final String GETTER_PREFIX = "get";

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
     * @param c2 目标对象的字节码
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T convertBean(Object target, Class<T> c2) {
        if (target == null) {
            return null;
        }
        BeanCopier copier = BeanCopier.create(target.getClass(), c2, true);
        List<String> rule = getRules(target.getClass().getDeclaredFields(), c2.getDeclaredFields());
        T o2 = null;
        try {
            o2 = c2.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        copy(copier, target, o2, rule);
        return o2;
    }

    /**
     * 实体转换
     *
     * @param target 被转换的对象
     * @param c2 目标对象的字节码
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> List<T> convert(List<?> target, Class<T> c2) {
        if (target == null || target.isEmpty()) {
            return null;
        }
        BeanCopier copier = BeanCopier.create(target.get(0).getClass(), c2, true);
        List<String> rule = getRules(target.get(0).getClass().getDeclaredFields(), c2.getDeclaredFields());
        List<T> list = Lists.newArrayList();
        T o2 = null;
        for (Object o : target) {
            try {
                o2 = c2.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            copy(copier, o, o2, rule);
            list.add(o2);
        }
        return list;
    }

    private static void copy(BeanCopier copier, Object o, Object o2, List<String> rule) {
        copier.copy(o, o2, new Converter() {
            @Override
            public Object convert(Object o, Class aClass, Object o1) {
                if (o != null && rule.contains(o1) && !"".equals(o)) {
                    if (String.class.equals(aClass)) {
                        return String.valueOf(o);
                    } else if (Integer.class.equals(aClass)) {
                        return Integer.parseInt(o.toString());
                    }
                } else if (o == null || "".equals(o)) {
                    return null;
                }
                return o;
            }
        });
    }

    public static List<String> getRules(Field[] f1, Field[] f2) {
        if (f1 == null && f2 == null) {
            return Lists.newArrayList();
        }
        List<Field> fs = new ArrayList(Arrays.asList(f1));
        fs.addAll(Arrays.asList(f2));
        List<String> rule = Lists.newArrayList();
        fs.stream().forEach(field -> {
            Encode encode = field.getAnnotation(Encode.class);
            if (encode != null) {
                if (!rule.contains(SETTER_PREFIX + StringUtils.capitalize(field.getName()))) {
                    rule.add(SETTER_PREFIX + StringUtils.capitalize(field.getName()));
                }
            }
        });
        return rule;
    }
}
