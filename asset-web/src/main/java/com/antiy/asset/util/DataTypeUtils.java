package com.antiy.asset.util;

import java.util.List;

import org.slf4j.Logger;

import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;

/**
 * @Auther: zhangbing
 * @Date: 2018/11/26 10:53
 * @Description: 数据类型转换
 */
public class DataTypeUtils {
    private static final Logger logger = LogUtils.get();

    public static Integer stringToInteger(String value) {
        Integer result = 0;
        try {
            result = Integer.valueOf(value);
        } catch (NumberFormatException e) {
            logger.error("字符串转换为整形出错", e);
            throw new BusinessException("字符串转换为整形出错");
        }
        return result;
    }

    public static Integer[] stringArrayToIntegerArray(String[] values) {
        Integer[] result = new Integer[values.length];
        for (int i = 0; i < values.length; i++) {
            try {
                result[i] = Integer.valueOf(values[i]);
            } catch (NumberFormatException e) {
                logger.error("字符串转换为整形出错", e);
                throw new BusinessException("字符串转换为整形出错");
            }
        }
        return result;
    }

    public static String[] integerArrayToStringArray(List values) {
        String[] result = new String[values.size()];
        for (int i = 0; i < values.size(); i++) {
            try {
                result[i] = String.valueOf(values.get(i));
            } catch (NumberFormatException e) {
                logger.error("整形转换为字符串出错", e);
                throw new BusinessException("整形转换为字符串出错");
            }
        }
        return result;
    }
}
