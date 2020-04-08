package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetLendRelation;
import com.antiy.asset.vo.request.AssetLendRelationRequest;
import com.antiy.asset.vo.response.AssetLendRelationResponse;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2020-04-07
 */
public interface AssetLendRelationDao extends IBaseDao<AssetLendRelation> {
    /**
     * 查询详情
     * @param uniqueId
     * @return
     */
    AssetLendRelationResponse queryInfo(String uniqueId);

    Integer returnConfirm(AssetLendRelationRequest assetLendRelationRequest);

    List<AssetLendRelationResponse> queryHistory(String assetId);
}
