package com.antiy.asset.service.impl;

import com.antiy.asset.cache.AssetBaseDataCache;
import com.antiy.asset.convert.UserSelectResponseConverter;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetDepartmentDao;
import com.antiy.asset.dao.AssetUserDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.service.IAssetUserService;
import com.antiy.asset.templet.AssetUserEntity;
import com.antiy.asset.templet.ImportResult;
import com.antiy.asset.util.ExcelUtils;
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
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.BusinessExceptionUtils;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * <p> 资产用户信息 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class AssetUserServiceImpl extends BaseServiceImpl<AssetUser> implements IAssetUserService {
    private static final Logger                         logger = LogUtils.get(AssetUserServiceImpl.class);

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
    @Resource
    private AssetDepartmentDao                          assetDepartmentDao;
    @Resource
    private AssetBaseDataCache                          assetBaseDataCache;

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
        // 更新缓存
        assetBaseDataCache.put(AssetBaseDataCache.ASSET_USER, assetUser);
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
        // 更新缓存
        assetBaseDataCache.update(AssetBaseDataCache.ASSET_USER, assetUser);
        return assetUserDao.update(assetUser);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AssetUserResponse> findListAssetUser(AssetUserQuery query) {
        List<AssetUser> assetUser = assetUserDao.queryUserList(query);
        if (CollectionUtils.isNotEmpty(assetUser)) {
            assetUser.stream().forEach(a -> {
                try {
                    if (StringUtils.isNotBlank(a.getAddress())) {
                        String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                            a.getAddress());
                        SysArea sysArea = redisUtil.getObject(key, SysArea.class);
                        if (Objects.isNull(sysArea)) {
                            a.setAddress(null);
                        } else {
                            a.setAddressName(sysArea.getFullName());
                        }
                    } else {
                        a.setAddress(null);
                    }
                } catch (Exception e) {
                    LogUtils.warn(logger, "获取用户详细地址失败:{}", e);
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
        // 更新缓存
        assetBaseDataCache.remove(AssetBaseDataCache.ASSET_USER, DataTypeUtils.integerToString(id));
        return ActionResponse.success();
    }

    @Override
    public void exportTemplate() {
        List<AssetUserEntity> dataList = initData();
        ExcelUtils.exportTemplateToClient(AssetUserEntity.class, "人员身份信息模板.xlsx", "人员身份",
            "备注：时间填写规范统一为XXXX/XX/XX(不用补0)，必填项必须填写，否则会插入失败，请不要删除示例，保持模版原样从第七行开始填写。", dataList);
    }

    private List<AssetUserEntity> initData() {
        List<AssetUserEntity> dataList = new ArrayList<>();
        AssetUserEntity computeDeviceEntity = new AssetUserEntity();
        computeDeviceEntity.setName("李四");
        computeDeviceEntity.setAddress("北京");
        computeDeviceEntity.setDepartmentName("销售部");
        computeDeviceEntity.setDetailAddress("北京市黄梅口124号");
        computeDeviceEntity.setEmail("www@163.com");
        computeDeviceEntity.setMobile("13888888888");
        computeDeviceEntity.setPosition("经理");
        computeDeviceEntity.setQq("996666410");
        computeDeviceEntity.setWeixin("13888888888");
        dataList.add(computeDeviceEntity);
        return dataList;
    }

    @Override
    @Transactional
    public String importUser(MultipartFile file) throws Exception {

        ImportResult<AssetUserEntity> result = ExcelUtils.importExcelFromClient(AssetUserEntity.class, file, 5, 0,
            2000);
        if (Objects.isNull(result.getDataList())) {
            return result.getMsg();
        }
        List<AssetUserEntity> resultDataList = result.getDataList();
        if (resultDataList.size() == 0 && StringUtils.isBlank(result.getMsg())) {
            return "导入失败，模板中无数据！" + result.getMsg();
        }

        StringBuilder sb = new StringBuilder(result.getMsg());

        if (result.getMsg().contains("导入失败")) {
            return sb.toString();
        }

        int success = 0;
        int error = 0;
        int a = 6;
        StringBuilder builder = new StringBuilder();
        List<AssetUser> assets = new ArrayList<>();
        for (AssetUserEntity entity : resultDataList) {

            String areaId = null;
            List<SysArea> areas = LoginUserUtil.getLoginUser().getAreas();
            List<String> areasStrings = new ArrayList<>();
            for (SysArea area : areas) {
                areasStrings.add(area.getFullName());
                if (area.getFullName().equals(entity.getAddress())) {
                    areaId = area.getStringId();
                }
            }

            if (!areasStrings.contains(entity.getAddress())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("当前用户没有此地址，或已被注销！");
                continue;
            }
            AssetUser asset = new AssetUser();
            asset.setGmtCreate(System.currentTimeMillis());
            asset.setAddress(areaId);
            asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
            asset.setDepartmentId(assetDepartmentDao.getIdByName(entity.getDepartmentName()));
            asset.setName(entity.getName());
            asset.setDepartmentName(entity.getDepartmentName());
            asset.setAddress(entity.getAddress());
            asset.setDetailAddress(entity.getDetailAddress());
            asset.setEmail(entity.getEmail());
            asset.setMobile(entity.getMobile());
            asset.setQq(entity.getQq());
            asset.setPosition(entity.getPosition());
            asset.setWeixin(entity.getWeixin());
            asset.setStatus(1);
            assets.add(asset);
            a++;
        }

        if (error == 0) {
            try {
                assetUserDao.insertBatch(assets);
                success = assets.size();
            } catch (DuplicateKeyException exception) {
                throw new BusinessException("请勿重复提交！");
            }

        }
        String res = "导入成功" + success + "条";
        if (error > 0) {
            res = "导入失败，";
        }

        StringBuilder stringBuilder = new StringBuilder(res);

        // 写入业务日志

        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_EXPORT_USERS.getName(), 0, "", null,
            BusinessModuleEnum.ASSET_USER, BusinessPhaseEnum.PEOPLE_IDENTIFY_IMPORT));
        LogUtils.info(logger, AssetEventEnum.ASSET_EXPORT_USERS.getName() + " {}", assets.toString());

        return stringBuilder.append(builder).append(sb).toString();
    }

}
