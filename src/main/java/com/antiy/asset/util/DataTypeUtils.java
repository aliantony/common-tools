package com.antiy.asset.util;

import org.slf4j.Logger;

import com.antiy.asset.exception.BusinessException;
import com.antiy.asset.utils.LogUtils;

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
}
