package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetPortProtocol;
import com.antiy.asset.entity.vo.query.AssetPortProtocolQuery;
import com.antiy.asset.entity.vo.response.AssetPortProtocolResponse;

/**
 * <p>
 * 端口协议 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-28
 */
public interface AssetPortProtocolDao extends IBaseDao<AssetPortProtocol> {

    List<AssetPortProtocolResponse> findListAssetPortProtocol(AssetPortProtocolQuery query) throws Exception;
}
