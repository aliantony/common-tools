package com.antiy.asset.dao;

import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.asset.vo.query.AssetGroupRelationQuery;
import com.antiy.asset.vo.request.RemoveAssociateAssetRequest;
import com.antiy.common.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

/**
 * <p> 资产与资产组关系表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetGroupRelationDao extends IBaseDao<AssetGroupRelation> {

    List<AssetGroupRelation> findListAssetGroupRelation(AssetGroupRelationQuery query) throws Exception;

    /**
     * 查询资产所有的资产组
     * @param id
     * @return
     */
    List<AssetGroup> queryByAssetId(Integer id);

    /**
     * 通过资产组ID查询资产组详情
     * @param query
     * @return
     */
    List<AssetGroupRelation> findAssetDetailByAssetGroupId(AssetGroupRelationQuery query);

    /**
     * 删除资产的资产组关系
     * @param id
     * @return
     */
    Integer deleteByAssetId(Integer id);

    /**
     * 通过资产组ID删除关系
     * @param id
     * @return
     */
    Integer deleteByAssetGroupId(Integer id);

    /**
     * 插入资产
     * @param assetGroupRelations
     * @return
     */
    Integer insertBatch(List<AssetGroupRelation> assetGroupRelations);

    /**
     * 通过资产组ID查询资产组详情
     * @param id
     * @return
     */
    List<String> findAssetNameByAssetGroupId(Integer id);

    /**
     * 通过资产组ID查询资产组详情
     * @param id
     * @return
     */
    Integer findCountDetailByGroupId(Integer id) throws Exception;

    /**
     * 通过资产ID查询资产组名称
     * @param id
     * @return
     */
    List<String> findAssetGroupNameByAssetId(String id) throws Exception;

    /**
     * 批量删除关联资产
     * @param request
     * @return
     */
    Integer batchDeleteById(RemoveAssociateAssetRequest request);

    List<String> findAssetIdByAssetGroupId(String groupId);

    /**
     * 通过资产组主键查询关联资产数量
     * @param groupId
     * @return
     * @throws Exception
     */
    Integer existRelateAssetInGroup(Serializable groupId) throws Exception;

    /**
     * 资产组是否存在绑定资产
     * @param primaryKey
     * @return
     */
    Integer hasRealtionAsset(@Param("primaryKey") String primaryKey);

    /**
     * 通过资产id查询关系list
     * @param assetId
     * @return
     */
    List<AssetGroupRelation> listRelationByAssetId(Integer assetId);

    /**
     * 根据关系主键id批量删除
     * @param idList 不能为空
     * @return
     */
    Integer deleteBatch(List<Integer> idList);
}
