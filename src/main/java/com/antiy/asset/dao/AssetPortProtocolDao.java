package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetPortProtocol;
import com.antiy.asset.entity.dto.AssetPortProtocolDTO;
import com.antiy.asset.entity.vo.query.AssetPortProtocolQuery;

/**
 * <p>
 * 端口协议 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetPortProtocolDao extends IBaseDao<AssetPortProtocol> {

    List<AssetPortProtocolDTO> findListAssetPortProtocol(AssetPortProtocolQuery query) throws Exception;
}
