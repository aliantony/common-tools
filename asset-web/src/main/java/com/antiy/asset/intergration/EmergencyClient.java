package com.antiy.asset.intergration;

import java.util.List;

import com.antiy.asset.entity.IdCount;
import com.antiy.common.base.ActionResponse;

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
    ActionResponse<List<IdCount>> queryEmergencyCount(List<Integer> assetIds);
}
