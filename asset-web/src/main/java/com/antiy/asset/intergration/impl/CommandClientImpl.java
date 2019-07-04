package com.antiy.asset.intergration.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.antiy.asset.intergration.CommandClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.vo.request.BaseRequestOuter;
import com.antiy.common.base.ActionResponse;

/**
 * 指令实现类
 * @author zhangyajun
 * @create 2019-05-15 11:38
 **/
@Component
public class CommandClientImpl implements CommandClient {
    @Value("${api.adater.command}")
    private String     commandUrl;

    @Resource
    private BaseClient baseClient;

    @Override
    public ActionResponse executeCommand(BaseRequestOuter baseRequestOuter) {
        return (ActionResponse) baseClient.post(baseRequestOuter, new ParameterizedTypeReference<ActionResponse>() {
        }, commandUrl);
    }
}
