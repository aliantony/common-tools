package com.antiy.asset.intergration.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.antiy.asset.intergration.CommandClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.vo.request.CommandRequest;
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
    public ActionResponse executeCommand(CommandRequest installRequest) {
        return (ActionResponse) baseClient.post(installRequest, new ParameterizedTypeReference<ActionResponse>() {
        }, commandUrl);
    }
}
