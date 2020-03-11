package com.antiy.asset.service;

import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.vo.query.AssetSchemeQuery;
import com.antiy.asset.vo.response.AssetOperationRecordResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;

/**
 * <p> 资产操作记录表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-07
 */
public interface IAssetOperationRecordService extends IBaseService<AssetOperationRecord> {



    /**
     *  v1.1 版本 资产详情页，资产动态查询接口
     * @param id 资产id
     * @return 响应信息
     */
    ActionResponse queryAssetAllStatusInfo(String id);

    /**
     * v1.1 版本 资产动态上一步备注信息查询批量接口
     */
    ActionResponse batchQueryAssetPreStatusInfo(List<String> ids);

    List<AssetResponse> queryAssetSchemListByAssetIds(AssetSchemeQuery assetSchemeQuery);

    /**
     * 资产退役/报废/入网审批资产信息列表
     * @param assetSchemeQuery
     * @return
     */
    PageResult<AssetResponse> queryCheckList(AssetSchemeQuery assetSchemeQuery);

    AssetOperationRecordResponse queryCheckSchemeByTaskId(Integer taskId);
}
