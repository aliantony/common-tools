package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import java.util.List;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.dao.AssetUserDao;
import com.antiy.asset.service.IAssetUserService;
import com.antiy.asset.entity.dto.AssetUserDTO;
import com.antiy.asset.entity.vo.request.AssetUserRequest;
import com.antiy.asset.entity.vo.response.AssetUserResponse;
import com.antiy.asset.entity.vo.query.AssetUserQuery;


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
        @Resource
        private BaseConverter<AssetUserRequest, AssetUser>  requestConverter;
        @Resource
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
            List<AssetUserDTO> assetUserDTO = assetUserDao.findListAssetUser(query);
            //TODO
            //需要将assetUserDTO转达成AssetUserResponse
            List<AssetUserResponse> assetUserResponse = new ArrayList<AssetUserResponse>();
            return assetUserResponse;
        }

        public Integer findCountAssetUser(AssetUserQuery query) throws Exception {
            return assetUserDao.findCount(query);
        }

        @Override
        public PageResult<AssetUserResponse> findPageAssetUser(AssetUserQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetUser(query),query.getCurrentPage(), this.findListAssetUser(query));
        }
}
