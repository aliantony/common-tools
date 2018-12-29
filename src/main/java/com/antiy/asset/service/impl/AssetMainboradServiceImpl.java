package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import java.util.List;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetMainborad;
import com.antiy.asset.dao.AssetMainboradDao;
import com.antiy.asset.service.IAssetMainboradService;
import com.antiy.asset.entity.dto.AssetMainboradDTO;
import com.antiy.asset.entity.vo.request.AssetMainboradRequest;
import com.antiy.asset.entity.vo.response.AssetMainboradResponse;
import com.antiy.asset.entity.vo.query.AssetMainboradQuery;


import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 主板表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Service
@Slf4j
public class AssetMainboradServiceImpl extends BaseServiceImpl<AssetMainborad> implements IAssetMainboradService{


        @Resource
        private AssetMainboradDao assetMainboradDao;
        @Resource
        private BaseConverter<AssetMainboradRequest, AssetMainborad>  requestConverter;
        @Resource
        private BaseConverter<AssetMainborad, AssetMainboradResponse> responseConverter;

        @Override
        public Integer saveAssetMainborad(AssetMainboradRequest request) throws Exception {
            AssetMainborad assetMainborad = requestConverter.convert(request, AssetMainborad.class);
            return assetMainboradDao.insert(assetMainborad);
        }

        @Override
        public Integer updateAssetMainborad(AssetMainboradRequest request) throws Exception {
            AssetMainborad assetMainborad = requestConverter.convert(request, AssetMainborad.class);
            return assetMainboradDao.update(assetMainborad);
        }

        @Override
        public List<AssetMainboradResponse> findListAssetMainborad(AssetMainboradQuery query) throws Exception {
            List<AssetMainboradDTO> assetMainboradDTO = assetMainboradDao.findListAssetMainborad(query);
            //TODO
            //需要将assetMainboradDTO转达成AssetMainboradResponse
            List<AssetMainboradResponse> assetMainboradResponse = new ArrayList<AssetMainboradResponse>();
            return assetMainboradResponse;
        }

        public Integer findCountAssetMainborad(AssetMainboradQuery query) throws Exception {
            return assetMainboradDao.findCount(query);
        }

        @Override
        public PageResult<AssetMainboradResponse> findPageAssetMainborad(AssetMainboradQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetMainborad(query),query.getCurrentPage(), this.findListAssetMainborad(query));
        }
}
