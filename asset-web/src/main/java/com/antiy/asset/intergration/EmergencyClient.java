package com.antiy.asset.intergration;

import java.util.List;

import com.antiy.asset.entity.IdCount;
import com.antiy.asset.vo.query.AlarmAssetIdQuery;
import com.antiy.asset.vo.response.AlarmAssetIdResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.ObjectQuery;
import com.antiy.common.base.PageResult;

/**
 * 资产告警远程调用
 * @author lvliang
 */
public interface EmergencyClient {

    /**
     * 获取资产告警数
     * @param objectQuery
     * @return
     */
    ActionResponse<PageResult<IdCount>> queryEmergencyCount(ObjectQuery objectQuery);

    PageResult<IdCount> queryInvokeEmergencyCount(ObjectQuery objectQuery);

    /**
     * 查询告警的资产个数
     */
    ActionResponse<AlarmAssetIdResponse> queryEmergecyAllCount();
}
