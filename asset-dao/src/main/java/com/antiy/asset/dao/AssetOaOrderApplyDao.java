package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetOaOrderApply;
import com.antiy.common.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * <p>
 * 订单申请信息表 Mapper 接口
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */
public interface AssetOaOrderApplyDao extends IBaseDao<AssetOaOrderApply> {

    /**
     * 通过订单id查询
     */
    AssetOaOrderApply getByOrderNumber(@Param("orderNumber") String orderNumber);


    /**
     * 通过资产编号查询ip和mac
     */
    HashMap<String, Object> getIpAndMacByAssetNumber(@Param("assetNumber") String assetNumber);
}
