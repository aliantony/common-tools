package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetPortProtocol;
import com.antiy.asset.vo.query.AssetPortProtocolQuery;
import com.antiy.common.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 端口协议 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetPortProtocolDao extends IBaseDao<AssetPortProtocol> {

    List<AssetPortProtocol> findListAssetPortProtocol(AssetPortProtocolQuery query) throws Exception;

    /**
     * 删除端口信息
     * @param releationIds 关联Id列表
     * @return
     */
    Integer deletePortProtocol(@Param("releationIds") List<Integer> releationIds);
}
