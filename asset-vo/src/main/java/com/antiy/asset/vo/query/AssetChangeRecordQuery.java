package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
/**
 * <p>
 * AssetChangeRecord 查询条件
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetChangeRecordQuery extends ObjectQuery {

    /**
     *  业务主键Id
     */
    private Integer businessId;
    /**
     *  1--硬件资产，2--软件资产
     */
    private Integer type;

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}