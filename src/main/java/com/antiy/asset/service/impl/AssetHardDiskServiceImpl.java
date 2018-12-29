package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import java.util.List;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetHardDisk;
import com.antiy.asset.dao.AssetHardDiskDao;
import com.antiy.asset.service.IAssetHardDiskService;
import com.antiy.asset.entity.dto.AssetHardDiskDTO;
import com.antiy.asset.entity.vo.request.AssetHardDiskRequest;
import com.antiy.asset.entity.vo.response.AssetHardDiskResponse;
import com.antiy.asset.entity.vo.query.AssetHardDiskQuery;


import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 硬盘表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Service
@Slf4j
public class AssetHardDiskServiceImpl extends BaseServiceImpl<AssetHardDisk> implements IAssetHardDiskService{


        @Resource
        private AssetHardDiskDao assetHardDiskDao;
        @Resource
        private BaseConverter<AssetHardDiskRequest, AssetHardDisk>  requestConverter;
        @Resource
        private BaseConverter<AssetHardDisk, AssetHardDiskResponse> responseConverter;

        @Override
        public Integer saveAssetHardDisk(AssetHardDiskRequest request) throws Exception {
            AssetHardDisk assetHardDisk = requestConverter.convert(request, AssetHardDisk.class);
            return assetHardDiskDao.insert(assetHardDisk);
        }

        @Override
        public Integer updateAssetHardDisk(AssetHardDiskRequest request) throws Exception {
            AssetHardDisk assetHardDisk = requestConverter.convert(request, AssetHardDisk.class);
            return assetHardDiskDao.update(assetHardDisk);
        }

        @Override
        public List<AssetHardDiskResponse> findListAssetHardDisk(AssetHardDiskQuery query) throws Exception {
            List<AssetHardDiskDTO> assetHardDiskDTO = assetHardDiskDao.findListAssetHardDisk(query);
            //TODO
            //需要将assetHardDiskDTO转达成AssetHardDiskResponse
            List<AssetHardDiskResponse> assetHardDiskResponse = new ArrayList<AssetHardDiskResponse>();
            return assetHardDiskResponse;
        }

        public Integer findCountAssetHardDisk(AssetHardDiskQuery query) throws Exception {
            return assetHardDiskDao.findCount(query);
        }

        @Override
        public PageResult<AssetHardDiskResponse> findPageAssetHardDisk(AssetHardDiskQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetHardDisk(query),query.getCurrentPage(), this.findListAssetHardDisk(query));
        }
}
