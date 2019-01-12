package com.antiy.asset.util;

import java.lang.reflect.*;

import org.apache.commons.lang.StringUtils;

/**
 * 反射工具类 用于调用getter/setter方法，访问私有方法，获取私有变量，获取泛型类型
 */
public class ReflectionUtils {

    private static final String SETTER_PREFIX = "set";

    private static final String GETTER_PREFIX = "get";

    /**
     * 反射调用get方法
     *
     * @param target 目标对象
     * @param propertyName 属性名
     * @return
     */
    public static Object invokeGetterMethod(Object target, String propertyName) throws IllegalAccessException,
                                                                                InstantiationException {
        String[] names = StringUtils.split(propertyName);
        String methodName;
        for (int i = 0; i < names.length; i++) {
            methodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
            target = invokeMethod(target, methodName, new Class[] {}, new Object[] {});
        }
        return target;
    }

    /**
     * 反射调用get方法(去掉is)
     *
     * @param target 目标对象
     * @param propertyName 属性名
     * @return
     */
    public static Object invokeGetterMethodWithoutIs(Object target, String propertyName) throws IllegalAccessException,
                                                                                         InstantiationException {
        String[] names = StringUtils.split(propertyName);
        String methodName;
        for (int i = 0; i < names.length; i++) {
            methodName = GETTER_PREFIX + StringUtils.capitalize(names[i].replace("is", ""));
            target = invokeMethod(target, methodName, new Class[] {}, new Object[] {});
        }
        return target;
    }

    /**
     * 反射调用set方法
     *
     * @param target 目标对象
     * @param propertyName 属性名
     * @return
     */
    public static void invokeSetterMethod(Object target, String propertyName, Object val) throws IllegalAccessException,
                                                                                          InstantiationException {
        String[] names = StringUtils.split(propertyName);
        String methodName = "";
        for (int i = 0; i < names.length; i++) {
            if (i < names.length - 1) {
                methodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
                target = invokeMethod(target, methodName, new Class[] {}, new Object[] {});
            } else {
                methodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
                invokeMethodByName(target, methodName, new Object[] { val });
            }
        }
    }

    /**
     * 反射调用set方法(去掉is)
     *
     * @param target 目标对象
     * @param propertyName 属性名
     * @return
     */
    public static void invokeSetterMethodWithoutIs(Object target, String propertyName,
                                                   Object val) throws IllegalAccessException, InstantiationException {
        String[] names = StringUtils.split(propertyName);
        String methodName = "";
        for (int i = 0; i < names.length; i++) {
            if (i < names.length - 1) {
                methodName = GETTER_PREFIX + StringUtils.capitalize(names[i].replace("is", ""));
                target = invokeMethod(target, methodName, new Class[] {}, new Object[] {});
            } else {
                methodName = SETTER_PREFIX + StringUtils.capitalize(names[i].replace("is", ""));
                invokeMethodByName(target, methodName, new Object[] { val });
            }
        }
    }

    /**
     * 给属性设置值,无视private/protected修饰符
     *
     * @param target 目标对象
     * @param fieldName 属性名
     * @param val 属性值
     */
    public static void setFieldValue(Object target, String fieldName, Object val) {
        Field field = getAccessibleField(target, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + target + "]");
        }
        try {
            field.set(target, val);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取属性值,无视private/protected修饰符
     *
     * @param target 目标对象
     * @param fieldName 属性名
     * @return
     */
    public static Object getFieldValue(Object target, String fieldName) {
        Field field = getAccessibleField(target, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + target + "]");
        }
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射调用方法
     *
     * @param target 目标对象
     * @param methodName 目标方法名
     * @param paramterTypes 方法参数类型
     * @param args 参数
     * @return
     */
    public static Object invokeMethod(Object target, String methodName, Class<?>[] paramterTypes, Object[] args) {
        Method method = getAccessibleMethod(target, methodName, paramterTypes);
        if (method == null) {
            throw new IllegalArgumentException("could not find method " + methodName + " on target " + target);
        }
        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射调用方法
     *
     * @param target 目标对象
     * @param methodName 目标方法名
     * @param args 参数
     * @return
     */
    public static Object invokeMethodByName(Object target, String methodName, Object[] args) {
        Method method = getAccessibleMethodByName(target, methodName);
        if (method == null) {
            throw new IllegalArgumentException("could not find method " + methodName + " on target " + target);
        }
        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取可访问方法
     *
     * @param target 目标对象
     * @param methodName 目标对象的方法名
     * @param paramterTypes 方法参数类型
     * @return
     */
    public static Method getAccessibleMethod(Object target, String methodName, Class<?>[] paramterTypes) {
        if (target == null) {
            throw new NullPointerException("目标类型不能为空!");
        }
        if (methodName == null || "".equals(methodName)) {
            throw new NullPointerException("目标方法不能为空!");
        }
        for (Class<?> targetClass = target.getClass(); targetClass != null; targetClass = targetClass.getSuperclass()) {
            try {
                Method method = targetClass.getDeclaredMethod(methodName, paramterTypes);
                // 如果是private方法，设置其可访问
                setAccessible(method);
                return method;
            } catch (NoSuchMethodException e) {
                continue;
            }
        }
        return null;
    }

    /**
     * 通过方法名获取方法
     *
     * @param target 目标对象
     * @param methodName 方法名
     * @return
     */
    private static Method getAccessibleMethodByName(Object target, String methodName) {
        if (target == null) {
            throw new NullPointerException("目标类型不能为空!");
        }
        if (methodName == null || "".equals(methodName)) {
            throw new NullPointerException("目标属性不能为空!");
        }
        for (Class<?> targetClass = target.getClass(); targetClass != null; targetClass = targetClass.getSuperclass()) {
            Method[] methods = targetClass.getDeclaredMethods();
            for (Method method : methods) {
                if (methodName.equals(method.getName())) {
                    // 如果是private方法，设置其可访问
                    setAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 获取可访问属性
     *
     * @param target 目标对象
     * @param fieldName 目标对象的属性名
     * @return
     */
    public static Field getAccessibleField(Object target, String fieldName) {
        if (target == null) {
            throw new NullPointerException("目标类型不能为空!");
        }
        if (fieldName == null || "".equals(fieldName)) {
            throw new NullPointerException("目标属性不能为空!");
        }

        for (Class<?> targetClass = target.getClass(); targetClass != null; targetClass = targetClass.getSuperclass()) {
            Field[] fields = targetClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(fieldName)) {
                    setAccessible(field);
                    return field;
                }
            }
        }
        return null;
    }

    /**
     * 设置属性为可访问 改变private/protected的方法为public
     *
     * @param field 属性
     * @return
     */
    public static Field setAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || Modifier.isProtected(field.getModifiers())) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
        }
        return field;
    }

    /**
     * 设置方法可访问 改变private/protected的方法为public
     *
     * @param method 方法
     * @return
     */
    public static Method setAccessible(Method method) {
        if (!Modifier.isPublic(method.getModifiers()) || Modifier.isProtected(method.getModifiers())) {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
        }
        return method;
    }

    /**
     * 获取泛型真实类型
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Class<T> getClassGenericType(Class<T> clazz) {
        return getClassGenericType(clazz, 0);
    }

    /**
     * 获取泛型类型
     *
     * @param clazz
     * @param index
     * @return
     */
    public static Class getClassGenericType(Class clazz, int index) {

        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }
}
