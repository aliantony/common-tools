package com.antiy.asset.service.impl;

import static com.antiy.biz.file.FileHelper.logger;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.antiy.asset.convert.UserSelectResponseConverter;
import com.antiy.asset.dao.AssetUserDao;
import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.service.IAssetUserService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.query.AssetUserQuery;
import com.antiy.asset.vo.request.AssetUserRequest;
import com.antiy.asset.vo.response.AssetUserResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * <p> 资产用户信息 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetUserServiceImpl extends BaseServiceImpl<AssetUser> implements IAssetUserService {

    @Resource
    private AssetUserDao                                assetUserDao;
    @Resource
    private BaseConverter<AssetUserRequest, AssetUser>  requestConverter;
    @Resource
    private BaseConverter<AssetUser, AssetUserResponse> responseConverter;
    @Resource
    private UserSelectResponseConverter                 userSelectResponseConverter;
    @Resource
    private AesEncoder                                  aesEncoder;

    @Override
    @Transactional
    public String saveAssetUser(AssetUserRequest request) throws Exception {
        AssetUser assetUser = requestConverter.convert(request, AssetUser.class);
        // assetUser.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetUser.setGmtCreate(System.currentTimeMillis());
        assetUserDao.insert(assetUser);
        // 写入业务日志
        LogHandle.log(assetUser.toString(), AssetEventEnum.ASSET_USER_INSERT.getName(),
            AssetEventEnum.ASSET_USER_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_USER_INSERT.getName() + " {}", assetUser.toString());
        return aesEncoder.encode(assetUser.getStringId(), LoginUserUtil.getLoginUser().getUsername());
    }

    @Override
    @Transactional
    public Integer updateAssetUser(AssetUserRequest request) throws Exception {
        AssetUser assetUser = requestConverter.convert(request, AssetUser.class);
        assetUser.setId(DataTypeUtils.stringToInteger(request.getId()));
        // assetUser.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetUser.setGmtCreate(System.currentTimeMillis());
        // 写入业务日志
        LogHandle.log(assetUser.toString(), AssetEventEnum.ASSET_USER_UPDATE.getName(),
            AssetEventEnum.ASSET_USER_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_USER_UPDATE.getName() + " {}", assetUser.toString());
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
        return responseConverter.convert(assetUsers, AssetUserResponse.class);
    }

    @Override
    public PageResult<AssetUserResponse> findPageAssetUser(AssetUserQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetUser(query), query.getCurrentPage(),
            this.findListAssetUser(query));
    }

    @Override
    public List<SelectResponse> queryUserInAsset() throws Exception {
        return userSelectResponseConverter.convert(assetUserDao.findUserInAsset(), SelectResponse.class);
    }

    @Override
    @Transactional
    public void importUser(List<AssetUser> assetUserList) {
        assetUserList.stream().forEach(user -> {
            // user.setCreateUser(LoginUserUtil.getLoginUser().getId());
            user.setGmtCreate(System.currentTimeMillis());
            user.setStatus(1);
            user.setMemo("");
        });
        assetUserDao.insertBatch(assetUserList);
    }
}
