package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetRunRecord;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 系统运行记录表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2020-03-10
 */
public interface AssetRunRecordDao extends IBaseDao<AssetRunRecord> {

    AssetRunRecord getLatestRunRecordByStartTime() throws Exception;

    /**
     * 获取运行时间
     * @return
     * @throws Exception
     */
    Long getRunDayTime() throws Exception;

}
