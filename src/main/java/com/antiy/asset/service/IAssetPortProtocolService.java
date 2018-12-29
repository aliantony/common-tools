package com.antiy.asset.service;

import com.antiy.asset.entity.AssetPortProtocol;
import com.antiy.asset.entity.vo.query.AssetPortProtocolQuery;
import com.antiy.asset.entity.vo.request.AssetPortProtocolRequest;
import com.antiy.asset.entity.vo.response.AssetPortProtocolResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;

;


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
         * @param request
         * @return
         */
        Integer saveAssetPortProtocol(AssetPortProtocolRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetPortProtocol(AssetPortProtocolRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetPortProtocolResponse> findListAssetPortProtocol(AssetPortProtocolQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetPortProtocolResponse> findPageAssetPortProtocol(AssetPortProtocolQuery query) throws Exception;

}
