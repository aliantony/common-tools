package com.antiy.asset.util;

import com.antiy.common.utils.SnowFlake;

/**
 * 雪花算法工具类
 * @author zhangyajun
 * @create 2020-03-03 15:59
 **/

public class SnowFlakeUtil {
    private static final SnowFlake SNOW_FLAKE = new SnowFlake(0, 0);

    private SnowFlakeUtil() {
    }

    public static SnowFlake getSnowFlake() {
        return SNOW_FLAKE;
    }

    public static String getSnowId() {
        return String.valueOf(SNOW_FLAKE.nextId());
    }
}
