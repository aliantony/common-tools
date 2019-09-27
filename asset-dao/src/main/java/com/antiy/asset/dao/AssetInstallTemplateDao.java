package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetInstallTemplate;
import com.antiy.asset.vo.response.AssetInstallTemplateOsResponse;
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
     *
     * @param assetId
     * @return
     * @throws Exception
     */
    AssetTemplateRelationResponse queryTemplateByAssetId(Integer assetId) throws Exception;

    /**
     * 模板查询-所有装机模板操作系统名称(去重)
     *
     * @return
     */
    List<AssetInstallTemplateOsResponse> queryTemplateOs();

    /**
     * 模板查询-所有装机模板操作系统状态(去重)
     *
     * @return
     */
    List<Integer> queryTemplateStatus();

    /**
     * 查询模板编号是否存在
     *
     * @param numberCode
     * @return
     */
    Integer queryNumberCode(String numberCode);

    /**
     * 模板创建/编辑-查询操作系统 匹配知识库
     *
     * @return
     */
    List<AssetInstallTemplateOsResponse> queryOs();
}
