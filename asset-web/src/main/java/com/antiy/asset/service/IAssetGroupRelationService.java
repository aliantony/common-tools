package com.antiy.asset.service;

import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.common.base.IBaseService;

/**
 * <p> 资产与资产组关系表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetGroupRelationService extends IBaseService<AssetGroupRelation> {

    /**
     * 资产组是否存在绑定资产
     * @param primaryKey
     * @return
     */
    Integer hasRealtionAsset(String primaryKey);
}
