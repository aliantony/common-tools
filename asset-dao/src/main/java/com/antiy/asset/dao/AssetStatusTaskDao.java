package com.antiy.asset.dao;

import java.util.List;

import com.antiy.asset.entity.AssetStatusTask;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 资产状态任务表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-05-22
 */
public interface AssetStatusTaskDao extends IBaseDao<AssetStatusTask> {

    /**
     * 获取所有的待处理任务
     * @return
     */
    List<AssetStatusTask> findTask() throws Exception;

}
