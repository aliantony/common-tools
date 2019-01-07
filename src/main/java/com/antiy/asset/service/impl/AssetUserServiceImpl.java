package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetUserDao;
import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.service.IAssetUserService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.query.AssetUserQuery;
import com.antiy.asset.vo.request.AssetUserRequest;
import com.antiy.asset.vo.response.AssetUserResponse;
import com.antiy.asset.vo.response.UserNameResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 资产用户信息 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetUserServiceImpl extends BaseServiceImpl<AssetUser> implements IAssetUserService {


    @Resource
    private AssetUserDao assetUserDao;
    @Resource
    private BaseConverter<AssetUserRequest, AssetUser> requestConverter;
    @Resource
    private BaseConverter<AssetUser, UserNameResponse> userNameResponseConverter;

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
        List<AssetUser> assetUser = assetUserDao.findListAssetUser(query);
        return convert(assetUser);
    }

    public Integer findCountAssetUser(AssetUserQuery query) throws Exception {
        return assetUserDao.findCount(query);
    }

    private List<AssetUserResponse> convert(List<AssetUser> assetUsers) {
        if (assetUsers.size() > 0) {
            List assetUserResponses = BeanConvert.convert(assetUsers, AssetUserResponse.class);
            setListID(assetUsers, assetUserResponses);
            return assetUserResponses;
        }
        return new ArrayList<>();
    }

    private void setListID(List<AssetUser> assetUsers, List<AssetUserResponse> assetUserResponses) {
        for (int i = 0; i < assetUserResponses.size(); i++) {
            setID(assetUsers.get(i), assetUserResponses.get(i));
        }
    }

    private void setID(AssetUser assetUser, AssetUserResponse assetUserResponse) {
        assetUserResponse.setId(assetUser.getId());
    }

    @Override
    public PageResult<AssetUserResponse> findPageAssetUser(AssetUserQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetUser(query), query.getCurrentPage(), this.findListAssetUser(query));
    }

    @Override
    public List<UserNameResponse> findUserName() throws Exception {
        return userNameResponseConverter.convert(assetUserDao.findUserName(), UserNameResponse.class);
    }
}
