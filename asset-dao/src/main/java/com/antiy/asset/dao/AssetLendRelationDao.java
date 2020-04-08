package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetLendRelation;
import com.antiy.asset.vo.response.AssetLendRelationResponse;
import com.antiy.common.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

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

    Integer returnConfirm(@Param("uniqueId")String uniqueId,@Param("returnTime") long returnTime);
}
