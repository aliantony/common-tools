package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetOaOrderApply;
import com.antiy.common.base.IBaseDao;

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
    AssetOaOrderApply getByOrderNumber(String orderNumber);
}
