package com.antiy.asset.intergration;

import com.antiy.asset.vo.request.SoftwareInstallRequest;
import com.antiy.common.base.ActionResponse;

/**
 * @auther: zhangyajun
 * @date: 2019/5/15 10：07
 * @description: 指令client
 */
public interface CommandClient {

    /**
     * 软件安装指令
     * @param installRequest
     * @return
     */
    ActionResponse InstallSoftwareAuto(SoftwareInstallRequest installRequest);
}
