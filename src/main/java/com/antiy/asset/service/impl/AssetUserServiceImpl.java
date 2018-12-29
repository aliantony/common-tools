package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.dao.AssetUserDao;
import com.antiy.asset.service.IAssetUserService;
import com.antiy.asset.asset.entity.vo.request.AssetUserRequest;
import com.antiy.asset.asset.entity.vo.response.AssetUserResponse;
import com.antiy.asset.asset.entity.vo.query.AssetUserQuery;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 资产用户信息 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Service
@Slf4j
public class AssetUserServiceImpl extends BaseServiceImpl<AssetUser> implements IAssetUserService{


        @Resource
        private AssetUserDao assetUserDao;

        private BaseConverter<AssetUserRequest, AssetUser>  requestConverter;
        
        private BaseConverter<AssetUser, AssetUserResponse> responseConverter;

        @Override
        public Integer saveAssetUser(AssetUserRequest request) throws Exception {
            AssetUser assetUser = requestConverter.convert(request, AssetUser.class);
            return assetUserDao.insert(assetUser);
        }

        @Override
        public Integer updateAssetUser(AssetUserRequest request) throws Exception {
            AssetUser assetUser = requestConverter.convert(request, AssetUser.class);
            return assetUserDao.update(assetUser);
        }

        @Override
        public List<AssetUserResponse> findListAssetUser(AssetUserQuery query) throws Exception {
            return assetUserDao.findListAssetUser(query);
        }

        public Integer findCountAssetUser(AssetUserQuery query) throws Exception {
            return assetUserDao.findCount(query);
        }

        @Override
        public PageResult<AssetUserResponse> findPageAssetUser(AssetUserQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetUser(query),query.getCurrentPage(), this.findListAssetUser(query));
        }
}
