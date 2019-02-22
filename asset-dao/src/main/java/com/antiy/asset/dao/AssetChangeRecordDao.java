package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetChangeRecord;
import com.antiy.asset.vo.query.AssetChangeRecordQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 变更记录表
 Mapper 接口
 * </p>
 *
 * @author why
 * @since 2019-02-19
 */
public interface AssetChangeRecordDao extends IBaseDao<AssetChangeRecord> {

    AssetChangeRecord  getByDescTime(Integer id);

    /**
     * 查询条件
     * @param query
     * @return
     */
    List<String> findChangeValByBusinessId(AssetChangeRecordQuery query);
}
