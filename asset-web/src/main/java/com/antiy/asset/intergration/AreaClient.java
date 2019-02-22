package com.antiy.asset.intergration;

import com.antiy.common.base.ActionResponse;

/**
 * 区域查询接口
 * @author zhangyajun
 * @create 2019-02-21 15:47
 **/
public interface AreaClient {
    ActionResponse queryAreaById(String id);

    Object getInvokeResult(String id);
}
