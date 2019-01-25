package com.antiy.asset.util;

import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;

import java.util.Objects;

/**
 * @Description: 数组类型转换
 */
public class ArrayTypeUtil {
    private static final Logger logger = LogUtils.get();

    public static Integer[] ObjectArrayToIntegerArray(Object[] objects) {
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

    public static String[] ObjectArrayToStringArray(Object[] objects) {
        String[] strings = new String[objects.length];
        for (int i = 0; i < objects.length; i++) {
            try {
                strings[i] = Objects.toString( objects[i]);
            } catch (Exception e) {
                logger.error("Object转换为整形出错", e);
                throw new BusinessException("Object转换为整形出错");
            }
        }
        return strings;
    }
}
