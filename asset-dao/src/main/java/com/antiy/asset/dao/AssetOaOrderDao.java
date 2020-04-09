package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetOaOrder;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * OA订单表 Mapper 接口
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */
public interface AssetOaOrderDao extends IBaseDao<AssetOaOrder> {

    /**
     * 根据编号查找
     */
    AssetOaOrder getByNumber(String number) throws Exception;

}
