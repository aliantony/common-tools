package com.antiy.asset.component;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.SysArea;
import com.antiy.common.enums.ModuleEnum;

/**
 * @author zhangyajun
 * @create 2020-03-03 21:14
 **/
@Component
public class WrappedRedisUtil {
    @Resource
    private RedisUtil redisUtil;

    public String bindAreaName(String areaId) throws Exception {
        String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class, areaId);
        SysArea sysArea = redisUtil.getObject(key, SysArea.class);
        return sysArea.getShortName();
    }
}
