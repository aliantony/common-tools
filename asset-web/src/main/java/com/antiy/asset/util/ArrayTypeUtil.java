package com.antiy.asset.util;

import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Objects;

/**
 * @Description: 数组类型转换
 */
public class ArrayTypeUtil {
    private static final Logger logger = LogUtils.get();

    public static Integer[] objectArrayToIntegerArray(Object[] objects) {
        Integer[] integers = new Integer[objects.length];
        for (int i = 0; i < objects.length; i++) {
            try {
                integers[i] = (Integer) objects[i];
            } catch (Exception e) {
                logger.error("Object转换为整形出错", e);
                throw new BusinessException("Object转换为整形出错");
            }
        }
        return integers;
    }

    public static String[] objectArrayToStringArray(Object[] objects) {
        String[] strings = new String[objects.length];
        for (int i = 0; i < objects.length; i++) {
            try {
                strings[i] = Objects.toString( objects[i]);
            } catch (Exception e) {
                logger.error("Object转换为String出错", e);
                throw new BusinessException("Object转换为String出错");
            }
        }
        return strings;
    }
    public static Map.Entry[] objectArrayToEntryArray(Object[] objects) {
        Map.Entry[] strings = new Map.Entry[objects.length];
        for (int i = 0; i < objects.length; i++) {
            try {
                strings[i] = (Map.Entry)objects[i];
            } catch (Exception e) {
                logger.error("Object转换为String出错", e);
                throw new BusinessException("Object转换为String出错");
            }
        }
        return strings;
    }
    public static Long[] objectArrayToLongArray(Object[] objects) {
        Long[] strings = new Long[objects.length];
        for (int i = 0; i < objects.length; i++) {
            try {
                strings[i] =(Long) objects[i];
            } catch (Exception e) {
                logger.error("Object转换为Long形出错", e);
                throw new BusinessException("Object转换为Long出错");
            }
        }
        return strings;
    }
}
