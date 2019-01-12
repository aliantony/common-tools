package com.antiy.asset.util;

/**
 * @Description: 数组类型转换
 */
public class ArrayTypeUtil {
    public static Integer[] ObjectArrayToIntegerArray(Object[] objects) {
        Integer[] integers = new Integer[objects.length];
        for (int i = 0; i < objects.length; i++) {
            integers[i] = (Integer) objects[i];
        }
        return integers;
    }
}
