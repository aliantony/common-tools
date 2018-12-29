package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.dto.AssetSoftwareDO;
import com.antiy.asset.entity.vo.query.AssetSoftwareQuery;
import com.antiy.asset.entity.vo.request.AssetSoftwareRequest;
import com.antiy.asset.entity.vo.response.AssetSoftwareResponse;
import com.antiy.asset.entity.AssetSoftware;


/**
 * <p>
 * 软件信息表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetSoftwareService extends IBaseService<AssetSoftware> {

        /**
         * 保存
         * @param request
         * @return
         */
        Integer saveAssetSoftware(AssetSoftwareRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetSoftware(AssetSoftwareRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        @Override
        public List<AssetSoftwareResponse> findListAssetSoftware(AssetSoftwareQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetSoftwareResponse> findPageAssetSoftware(AssetSoftwareQuery query) throws Exception;

}
