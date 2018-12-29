package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.service.IAssetSoftwareService;
import com.antiy.asset.entity.vo.request.AssetSoftwareRequest;
import com.antiy.asset.entity.vo.response.AssetSoftwareResponse;
import com.antiy.asset.entity.vo.query.AssetSoftwareQuery;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 软件信息表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Service
@Slf4j
public class AssetSoftwareServiceImpl extends BaseServiceImpl<AssetSoftware> implements IAssetSoftwareService{


        @Resource
        private AssetSoftwareDao assetSoftwareDao;

        private BaseConverter<AssetSoftwareRequest, AssetSoftware>  requestConverter;
        
        private BaseConverter<AssetSoftware, AssetSoftwareResponse> responseConverter;

        @Override
        public Integer saveAssetSoftware(AssetSoftwareRequest request) throws Exception {
            AssetSoftware assetSoftware = requestConverter.convert(request, AssetSoftware.class);
            return assetSoftwareDao.insert(assetSoftware);
        }

        @Override
        public Integer updateAssetSoftware(AssetSoftwareRequest request) throws Exception {
            AssetSoftware assetSoftware = requestConverter.convert(request, AssetSoftware.class);
            return assetSoftwareDao.update(assetSoftware);
        }

        @Override
        public List<AssetSoftwareResponse> findListAssetSoftware(AssetSoftwareQuery query) throws Exception {
            return assetSoftwareDao.findListAssetSoftware(query);
        }

        public Integer findCountAssetSoftware(AssetSoftwareQuery query) throws Exception {
            return assetSoftwareDao.findCount(query);
        }

        @Override
        public PageResult<AssetSoftwareResponse> findPageAssetSoftware(AssetSoftwareQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetSoftware(query),query.getCurrentPage(), this.findListAssetSoftware(query));
        }
}
