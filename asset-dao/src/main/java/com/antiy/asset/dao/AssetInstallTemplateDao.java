package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetInstallTemplate;
import com.antiy.asset.vo.response.AssetTemplateRelationResponse;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p> 装机模板 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-09-16
 */
public interface AssetInstallTemplateDao extends IBaseDao<AssetInstallTemplate> {

    public List<AssetInstallTemplate> findByAssetIds(List<String> ids);
    /**
     * 通过资产ID查询
     * @param assetId
     * @return
     * @throws Exception
     */
    AssetTemplateRelationResponse queryTemplateByAssetId(Integer assetId) throws Exception;

}
