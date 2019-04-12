package com.antiy.asset.service.impl;

import static com.antiy.asset.util.Constants.SYSTEM_OS_REDIS_KEY;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.service.IRedisService;
import com.antiy.asset.vo.redis.CategoryOsResponse;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.RespBasicCode;

/**
 * @author: zhangbing
 * @date: 2019/4/12 14:59
 * @description:
 */
@Service
public class RedisServiceImpl implements IRedisService {

    @Resource
    private RedisUtil             redisUtil;

    @Resource
    private OperatingSystemClient operatingSystemClient;

    @Override
    public List<CategoryOsResponse> getAllSystemOs() throws Exception {
        // TODO 直接从redis获取，如果redis没有，则记录日志,此处没有判断缓存穿透这种情况情况
        List<CategoryOsResponse> result = redisUtil.getObjectList(SYSTEM_OS_REDIS_KEY, CategoryOsResponse.class);
        if (CollectionUtils.isEmpty(result)) {
            // 远程调用返回数据
            ActionResponse actionResponse = operatingSystemClient.getOperatingSystem();
            if (null != actionResponse
                && RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                return (List<CategoryOsResponse>) actionResponse.getBody();
            }
        }
        return result;
    }
}
