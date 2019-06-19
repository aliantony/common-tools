package com.antiy.asset.service.impl;

import static com.antiy.biz.file.FileHelper.logger;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.antiy.asset.convert.UserSelectResponseConverter;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetUserDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.service.IAssetUserService;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.query.AssetUserQuery;
import com.antiy.asset.vo.request.AssetUserRequest;
import com.antiy.asset.vo.response.AssetUserResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.BusinessExceptionUtils;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * <p> 资产用户信息 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Transactional(rollbackFor = Exception.class)
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
    @Resource
    private RedisUtil                                   redisUtil;
    @Resource
    private AssetDao                                    assetDao;

    @Override
    public String saveAssetUser(AssetUserRequest request) throws Exception {
        AssetUser assetUser = requestConverter.convert(request, AssetUser.class);
        assetUser.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetUser.setGmtCreate(System.currentTimeMillis());
        assetUserDao.insert(assetUser);
        // 记录操作日志和运行日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_USER_INSERT.getName(), assetUser.getId(),
            assetUser.getName(), assetUser, BusinessModuleEnum.ASSET_USER, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.ASSET_USER_INSERT.getName() + " {}", assetUser);
        return aesEncoder.encode(assetUser.getStringId(), LoginUserUtil.getLoginUser().getUsername());
    }

    @Override
    public Integer updateAssetUser(AssetUserRequest request) throws Exception {
        AssetUser assetUser = requestConverter.convert(request, AssetUser.class);
        assetUser.setId(DataTypeUtils.stringToInteger(request.getId()));
        assetUser.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetUser.setGmtCreate(System.currentTimeMillis());
        // 记录操作日志和运行日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_USER_UPDATE.getName(), assetUser.getId(),
            assetUser.getName(), assetUser, BusinessModuleEnum.ASSET_USER, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.ASSET_USER_UPDATE.getName() + " {}", assetUser);
        return assetUserDao.update(assetUser);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AssetUserResponse> findListAssetUser(AssetUserQuery query) throws Exception {
        List<AssetUser> assetUser = assetUserDao.queryUserList(query);
        if (CollectionUtils.isNotEmpty(assetUser)) {
            assetUser.stream().forEach(a -> {
                try {
                    if (StringUtils.isNotBlank(a.getAddress())) {
                        String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                                DataTypeUtils.stringToInteger(a.getAddress()));
                        SysArea sysArea = redisUtil.getObject(key, SysArea.class);
                        a.setAddressName(sysArea.getFullName());
                    } else {
                        a.setAddress(null);
                    }
                } catch (Exception e) {
                    logger.warn("获取用户详细地址失败", e);
                }
            });
        }
        return convert(assetUser);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Integer findCountAssetUser(AssetUserQuery query) throws Exception {
        return assetUserDao.findCount(query);
    }

    private List<AssetUserResponse> convert(List<AssetUser> assetUsers) {
        return responseConverter.convert(assetUsers, AssetUserResponse.class);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageResult<AssetUserResponse> findPageAssetUser(AssetUserQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetUser(query), query.getCurrentPage(),
            this.findListAssetUser(query));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<SelectResponse> queryUserInAsset() throws Exception {
        return userSelectResponseConverter.convert(assetUserDao.findUserInAsset(), SelectResponse.class);
    }

    @Override
    public void importUser(List<AssetUser> assetUserList) {
        assetUserDao.insertBatch(assetUserList);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<AssetUser> findExportListAssetUser(AssetUserQuery assetUser) {
        return assetUserDao.findExportListAssetUser(assetUser);
    }

    @Override
    public ActionResponse deleteUserById(Integer id) throws Exception {
        HashMap<String, Object> param = new HashMap<>(1);
        param.put("responsibleUserId", id);
        List<Asset> assets = assetDao.getByWhere(param);
        BusinessExceptionUtils.isTrue(CollectionUtils.isEmpty(assets), "该人员已经是资产使用者,不能注销");
        AssetUser userInfo = assetUserDao.getById(id);
        assetUserDao.deleteById(id);
        // 记录操作日志和运行日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_USER_DELETE.getName(), id,
            userInfo != null ? userInfo.getName() : null, id, BusinessModuleEnum.ASSET_USER, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.ASSET_USER_DELETE.getName() + " {}", id);
        return ActionResponse.success();
    }
}
