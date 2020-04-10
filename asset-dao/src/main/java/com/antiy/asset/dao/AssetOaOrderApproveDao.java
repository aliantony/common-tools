package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetOaOrder;
import com.antiy.asset.entity.AssetOaOrderApply;
import com.antiy.asset.entity.AssetOaOrderApprove;
import com.antiy.common.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单审批信息表 Mapper 接口
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */
public interface AssetOaOrderApproveDao extends IBaseDao<AssetOaOrderApprove> {

    /**
     * 批量插入
     */
    Integer insertBatch(List<AssetOaOrderApprove> assetOaOrders);

    /**
     * 通过订单id查询
     */
    List<AssetOaOrderApprove> getByOrderNumber(@Param("orderNumber") String orderNumber);
}
