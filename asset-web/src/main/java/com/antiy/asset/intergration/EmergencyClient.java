package com.antiy.asset.intergration;

import com.antiy.common.base.ActionResponse;

import java.util.List;

/**
 * 资产告警远程调用
 * @author lvliang
 */
public interface EmergencyClient {

    /**
     * 获取资产告警数
     * @param assetIds
     * @return
     */
    ActionResponse queryEmergencyCount(List<Integer> assetIds);
}
