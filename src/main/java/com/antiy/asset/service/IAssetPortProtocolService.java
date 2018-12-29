package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;

import com.antiy.asset.entity.vo.query.AssetPortProtocolQuery;
import com.antiy.asset.entity.vo.request.AssetPortProtocolRequest;
import com.antiy.asset.entity.vo.response.AssetPortProtocolResponse;
import com.antiy.asset.entity.AssetPortProtocol;


/**
 * <p>
 * 端口协议 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetPortProtocolService extends IBaseService<AssetPortProtocol> {

        /**
         * 保存
         * @param assetPortProtocolRequest
         * @return
         */
        Integer saveAssetPortProtocol(AssetPortProtocolRequest assetPortProtocolRequest) throws Exception;

        /**
         * 修改
         * @param assetPortProtocolRequest
         * @return
         */
        Integer updateAssetPortProtocol(AssetPortProtocolRequest assetPortProtocolRequest) throws Exception;

        /**
         * 查询对象集合
         * @param assetPortProtocolQuery
         * @return
         */
        List<AssetPortProtocolResponse> findListAssetPortProtocol(AssetPortProtocolQuery assetPortProtocolQuery) throws Exception;

        /**
         * 批量查询
         * @param assetPortProtocolQuery
         * @return
         */
        PageResult<AssetPortProtocolResponse> findPageAssetPortProtocol(AssetPortProtocolQuery assetPortProtocolQuery) throws Exception;

}
