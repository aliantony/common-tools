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

    /**
     * 通过状态码比较枚举是否一致
     *
     * @param code     状态码
     * @param codeEnum 枚举实例
     * @return
     */
    public static boolean equals(int code, CodeEnum codeEnum) {
        if (codeEnum.getCode().equals(code)) {
            return true;
        }
        return false;
    }
}
