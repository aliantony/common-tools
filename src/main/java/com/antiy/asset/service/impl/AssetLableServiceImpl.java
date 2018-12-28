package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetLable;
import com.antiy.asset.dao.AssetLableDao;
import com.antiy.asset.service.IAssetLableService;
import com.antiy.asset.entity.vo.request.AssetLableRequest;
import com.antiy.asset.entity.vo.response.AssetLableResponse;
import com.antiy.asset.entity.vo.query.AssetLableQuery;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 标签信息表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-28
 */
@Service
@Slf4j
public class AssetLableServiceImpl extends BaseServiceImpl<AssetLable> implements IAssetLableService{


        @Resource
        private AssetLableDao assetLableDao;

        private BaseConverter<AssetLableRequest, AssetLable>  requestConverter;
        
        private BaseConverter<AssetLable, AssetLableResponse> responseConverter;

        @Override
        public Integer saveAssetLable(AssetLableRequest request) throws Exception {
            AssetLable assetLable = requestConverter.convert(request, AssetLable.class);
            return assetLableDao.insert(assetLable);
        }

        @Override
        public Integer updateAssetLable(AssetLableRequest request) throws Exception {
            AssetLable assetLable = requestConverter.convert(request, AssetLable.class);
            return assetLableDao.update(assetLable);
        }

        @Override
        public List<AssetLableResponse> findListAssetLable(AssetLableQuery query) throws Exception {
            return assetLableDao.findListAssetLable(query);
        }

        public Integer findCountAssetLable(AssetLableQuery query) throws Exception {
            return assetLableDao.findCount(query);
        }

        @Override
        public PageResult<AssetLableResponse> findPageAssetLable(AssetLableQuery query) throws Exception {
            return new PageResult<>(this.findCountAssetLable(query),this.findListAssetLable(query));
        }
}
