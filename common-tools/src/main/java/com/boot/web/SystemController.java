package com.boot.web;

import com.boot.annotation.ResponseResult;
import com.boot.common.SystemHardwareInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program common-tools
 * @description 
 * @author wangqian
 * created on 2020-06-09
 * @version  1.0.0
 */
@Api(value = "系统信息api", description ="系统信息api")
@RestController
@ResponseResult
public class SystemController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 系统硬件信息页面
     *
     * @author antony
     * @Date 2018/12/24 22:43
     */
    @ApiOperation("获取系统信息")
    @GetMapping("/systemInfo")
    public SystemHardwareInfo systemInfo() {
        StopWatch watch = new StopWatch();
        watch.start();
        SystemHardwareInfo systemHardwareInfo = new SystemHardwareInfo();
        systemHardwareInfo.copyTo();
        watch.stop();
        System.out.println(watch.getTotalTimeMillis());
        return systemHardwareInfo;
    }
}
