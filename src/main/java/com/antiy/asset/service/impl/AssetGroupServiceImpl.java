package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.dao.AssetGroupDao;
import com.antiy.asset.service.IAssetGroupService;
import com.antiy.asset.entity.vo.request.AssetGroupRequest;
import com.antiy.asset.entity.vo.response.AssetGroupResponse;
import com.antiy.asset.entity.vo.query.AssetGroupQuery;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 资产组表 服务实现类
 * </p>
 *
 * @author yangzihan
 * @since 2018-12-28
 */
@Service
@Slf4j
public class AssetGroupServiceImpl extends BaseServiceImpl<AssetGroup> implements IAssetGroupService{


        @Resource
        private AssetGroupDao assetGroupDao;

        private BaseConverter<AssetGroupRequest, AssetGroup>  requestConverter;
        
        private BaseConverter<AssetGroup, AssetGroupResponse> responseConverter;

        @Override
        public Integer saveAssetGroup(AssetGroupRequest request) throws Exception {
            AssetGroup assetGroup = requestConverter.convert(request, AssetGroup.class);
            return assetGroupDao.insert(assetGroup);
        }

        @Override
        public Integer updateAssetGroup(AssetGroupRequest request) throws Exception {
            AssetGroup assetGroup = requestConverter.convert(request, AssetGroup.class);
            return assetGroupDao.update(assetGroup);
        }

        @Override
        public List<AssetGroupResponse> findListAssetGroup(AssetGroupQuery query) throws Exception {
            return assetGroupDao.findListAssetGroup(query);
        }

        public Integer findCountAssetGroup(AssetGroupQuery query) throws Exception {
            return assetGroupDao.findCount(query);
        }

        @Override
        public PageResult<AssetGroupResponse> findPageAssetGroup(AssetGroupQuery query) throws Exception {
            return new PageResult<>(this.findCountAssetGroup(query),this.findListAssetGroup(query));
        }
}
