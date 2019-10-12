package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.vo.query.AssetOperationRecordQuery;
import com.antiy.asset.vo.response.AssetOperationRecordBarResponse;
import com.antiy.asset.vo.response.NameValueVo;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.IBaseService;

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
}
