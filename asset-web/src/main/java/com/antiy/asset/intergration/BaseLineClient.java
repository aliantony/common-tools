package com.antiy.asset.intergration;

import java.util.List;

import com.antiy.asset.vo.query.ConfigRegisterRequest;
import com.antiy.common.base.ActionResponse;

/**
 * @author: zhangbing
 * @date: 2019/4/4 16:22
 * @description:
 */
public interface BaseLineClient {

    /**
     * 硬件登记调用配置注册接口
     * @param request
     * @return
     */
    ActionResponse configRegister(List<ConfigRegisterRequest> request);


}
