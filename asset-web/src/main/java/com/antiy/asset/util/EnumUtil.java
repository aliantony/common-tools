package com.antiy.asset.util;

import com.antiy.asset.vo.enums.CodeEnum;

/**
 * 枚举工具类
 *
 * @author zhangyajun
 * @create 2019-01-14 11:53
 **/
public class EnumUtil {
    //返回的对象实现CodeEnum接口
    public static <T extends CodeEnum> T getByCode(Class<T> enumClass, Integer code) {
        for (T each : enumClass.getEnumConstants()) {
            if(each.getCode().equals(code)){
                return each;
            }
        }
        return null;
    }
}
