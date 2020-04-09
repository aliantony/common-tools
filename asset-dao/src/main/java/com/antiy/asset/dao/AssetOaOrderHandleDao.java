package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetOaOrderHandle;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 订单处理关联资产表 Mapper 接口
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */
public interface AssetOaOrderHandleDao extends IBaseDao<AssetOaOrderHandle> {

    /**
     * 批量插入
     */
    Integer insertBatch(List<AssetOaOrderHandle> assetOaOrders);
}
