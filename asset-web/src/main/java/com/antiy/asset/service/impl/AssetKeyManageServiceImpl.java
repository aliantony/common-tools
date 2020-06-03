package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetKeyManageDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetKeyManage;
import com.antiy.asset.service.IAssetKeyManageService;
import com.antiy.asset.templet.ImportResult;
import com.antiy.asset.templet.KeyEntity;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.KeyStatusEnum;
import com.antiy.asset.vo.enums.KeyUserType;
import com.antiy.asset.vo.query.AssetKeyManageQuery;
import com.antiy.asset.vo.query.KeyPullQuery;
import com.antiy.asset.vo.request.AssetKeyManageRequest;
import com.antiy.asset.vo.response.AssetKeyManageResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.asset.vo.response.KeyPullDownResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BusinessData;
import com.antiy.common.base.LoginUser;
import com.antiy.common.base.PageResult;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.net.ssl.KeyManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author chenchaowu
 * @Package com.antiy.asset.service.impl
 * @date 2020/4/7 15:23
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class AssetKeyManageServiceImpl implements IAssetKeyManageService {

    @Resource
    private AssetKeyManageDao keyManageDao;

    @Resource
    private AssetDao assetDao;

    @Resource
    private BaseConverter<AssetKeyManage, AssetKeyManageResponse> converter;

    @Resource
    private BaseConverter<AssetKeyManageRequest, AssetKeyManage> converter2;

    /**
     * key模糊查询
     *
     * @param query
     * @return
     */
    @Override
    public PageResult<AssetKeyManageResponse> findPageAssetKeys(AssetKeyManageQuery query) {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(), this.findList(query));
    }

    private Integer findCount(AssetKeyManageQuery query) {
        return keyManageDao.queryCount(query);
    }

    private List<AssetKeyManageResponse> findList(AssetKeyManageQuery query) {
        List<AssetKeyManage> responseList = keyManageDao.queryList(query);

        if (responseList.isEmpty()) {
            return new ArrayList<>();
        }
        List<AssetKeyManageResponse> responses = converter.convert(responseList, AssetKeyManageResponse.class);
        for (AssetKeyManageResponse response : responses) {
            response.setStateName(KeyStatusEnum.getEnumName(response.getRecipState()));
            response.setTypeName(KeyUserType.getEnumName(response.getKeyUserType()));
        }

        return responses;
    }

    /**
     * key登记
     *
     * @param request
     * @return
     */
    @Override
    public Integer keyRegister(AssetKeyManageRequest request) throws Exception {

        ParamterExceptionUtils.isNull(request.getKeyNum(), "KEY编号为空!");
        ParamterExceptionUtils.isNull(request.getRecipState(), "领用状态为空!");

        if (request.getRecipState().equals(KeyStatusEnum.KEY_RECIPIENTS.getStatus())) {
            ParamterExceptionUtils.isNull(request.getKeyUserType(), "使用者类型为空!");
            ParamterExceptionUtils.isNull(request.getKeyUserId(), "使用者ID不能为空!");
            ParamterExceptionUtils.isNull(request.getUserNumName(), "使用者不能为空!");
            ParamterExceptionUtils.isNull(request.getRecipTime(), "领用时间不能为空!");
        }

        LoginUser user = LoginUserUtil.getLoginUser();
        AssetKeyManage keyManage = converter2.convert(request, AssetKeyManage.class);

        /**
         * 1、key编号 校验 64位字符，仅英文、数字
         * 2、key编号重复校验
         * 3、去重已领过key的用户
         */
        if (!keyManage.getKeyNum().matches("^[A-Za-z0-9]+$")) {
            throw new BusinessException("key编号校验失败，需仅由数字及字母组成!");
        }
        if (keyManage.getKeyNum().length() > 64) {
            throw new BusinessException("key编号校验失败，64位长度限制!");
        }

        AssetKeyManageRequest query = new AssetKeyManageRequest();
        query.setKeyNum(keyManage.getKeyNum());
        if (keyManageDao.keyNumCountVerify(query) > 0) {
            throw new BusinessException("key编号重复!");
        }

        if (KeyStatusEnum.KEY_RECIPIENTS.getStatus().equals(keyManage.getRecipState())) {
            query.setKeyUserType(keyManage.getKeyUserType());
            query.setKeyUserId(keyManage.getKeyUserId());
            if (keyManageDao.keyNameCountVerify(query) > 0) {
                throw new BusinessException("使用者重复!");
            }
        } else {
            keyManage.setKeyUserType(null);
            keyManage.setKeyUserId(null);
            keyManage.setUserNumName(null);
            keyManage.setRecipTime(null);
        }

        keyManage.setCreateUser(user.getId());
        keyManage.setModifiedUser(user.getId());
        keyManage.setGmtCreate(System.currentTimeMillis());
        keyManage.setGmtModified(System.currentTimeMillis());
        keyManage.setIsDelete(1);
        keyManageDao.keyRegister(keyManage);

        // 对资产表中的key进行同步
        if (KeyStatusEnum.KEY_RECIPIENTS.getStatus().equals(keyManage.getRecipState()) && keyManage.getKeyUserType() == 1) {
            assetKeyUpdate(keyManage);
        }

        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.KEY_REGISTER.getName(),
                keyManage.getId(), keyManage.getKeyNum(), keyManage,
                BusinessModuleEnum.KEY_MANAGE, BusinessPhaseEnum.KEY_REGISTER));

        return keyManage.getId();
    }

    /**
     * key领用
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Integer keyRecipients(AssetKeyManageRequest request) throws Exception {
        ParamterExceptionUtils.isNull(request.getKeyUserType(), "使用者类型为空!");
        ParamterExceptionUtils.isNull(request.getKeyUserId(), "使用者ID不能为空!");
        ParamterExceptionUtils.isNull(request.getUserNumName(), "使用者不能为空!");
        ParamterExceptionUtils.isNull(request.getRecipTime(), "领用时间不能为空!");

        LoginUser user = LoginUserUtil.getLoginUser();
        AssetKeyManage keyManage = converter2.convert(request, AssetKeyManage.class);

        keyManage.setRecipState(KeyStatusEnum.KEY_RECIPIENTS.getStatus());
        keyManage.setCreateUser(user.getId());
        keyManage.setModifiedUser(user.getId());
        keyManage.setGmtCreate(System.currentTimeMillis());
        keyManage.setGmtModified(System.currentTimeMillis());
        keyManage.setIsDelete(1);

        AssetKeyManageRequest query = new AssetKeyManageRequest();
        query.setKeyNum(keyManage.getKeyNum());
        query.setKeyUserType(keyManage.getKeyUserType());
        query.setKeyUserId(keyManage.getKeyUserId());
        if (keyManageDao.keyNameCountVerify(query) > 0) {
            throw new BusinessException("使用者重复!");
        }

        keyManageDao.keyRecipients(keyManage);

        // 对资产表中的key进行同步
        if (keyManage.getKeyUserType() == 1) {
            assetKeyUpdate(keyManage);
        }

        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.KEY_RECIPIENTS.getName(),
                keyManage.getId(), keyManage.getKeyNum(), keyManage,
                BusinessModuleEnum.KEY_MANAGE, BusinessPhaseEnum.KEY_USE));

        return keyManage.getId();
    }

    private Integer assetKeyUpdate(AssetKeyManage keyManage) throws Exception {

        Asset asset = assetDao.getById(keyManage.getKeyUserId().toString());
        if (asset != null) {
            asset = new Asset();
            asset.setId(keyManage.getKeyUserId());
//            asset.setKey(keyManage.getKeyNum());
            asset.setKey("");
            assetDao.update(asset);
        }

        return asset.getId();
    }

    /**
     * key归还
     *
     * @param request
     * @return
     */
    @Override
    public Integer keyReturn(AssetKeyManageRequest request) throws Exception {
        AssetKeyManage keyManage = keyManageDao.queryId(request.getId());
        if (keyManage == null) {
            throw new BusinessException("KEY不存在！");
        }

        // 资产中key同步处理
        if (keyManage.getKeyUserType() == 1) {
            AssetKeyManage assetKeyManage = new AssetKeyManage();
            assetKeyManage.setKeyUserId(keyManage.getKeyUserId());
            assetKeyUpdate(assetKeyManage);
        }
        keyManage.setKeyUserId(null);
        keyManage.setUserNumName(null);
        keyManage.setRecipTime(null);
        keyManage.setRecipState(KeyStatusEnum.KEY_NO_RECIPIENTS.getStatus());
        keyManage.setKeyUserType(null);

        keyManageDao.keyReturn(keyManage);

        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.KEY_RETURN.getName(),
                keyManage.getId(), keyManage.getKeyNum(), keyManage,
                BusinessModuleEnum.KEY_MANAGE, BusinessPhaseEnum.KEY_BACK));

        return keyManage.getId();
    }

    /**
     * key冻结 or 解冻
     *
     * @param request
     * @return
     */
    @Override
    public Integer keyFreeze(AssetKeyManageRequest request, Integer keyStatus) {
        AssetKeyManage keyManage = new AssetKeyManage();
        keyManage.setId(request.getId());
        keyManage.setRecipState(keyStatus);
        Integer result = keyManageDao.keyFreeze(keyManage);

        AssetKeyManage assetKeyManage = keyManageDao.queryId(request.getId());

        LogUtils.recordOperLog(new BusinessData(keyStatus == 3 ? AssetEventEnum.KEY_FREEZE.getName() : AssetEventEnum.KEY_UNFREEZE.getName(),
                assetKeyManage.getId(), assetKeyManage.getKeyNum(), assetKeyManage,
                BusinessModuleEnum.KEY_MANAGE, BusinessPhaseEnum.KEY_FROZEN));

        return result;
    }

    /**
     * key删除
     *
     * @param request
     * @return
     */
    @Override
    public Integer keyRemove(AssetKeyManageRequest request) {
        return keyManageDao.keyRemove(request.getId());
    }

    @Override
    public void exportTemplate() {
        List<KeyEntity> dataList = initData();
        ExcelUtils.exportTemplateToClient(KeyEntity.class, "Key信息模板.xlsx", "key信息",
                "备注:必填项必须填写，否则会插入失败，请不要删除示例，保持模版原样从第七行开始填写。", dataList);
    }

    private List<KeyEntity> initData() {
        List<KeyEntity> dataList = new ArrayList<>();
        KeyEntity computeDeviceEntity = new KeyEntity();
        computeDeviceEntity.setKeyNum("cd002548");
        dataList.add(computeDeviceEntity);
        return dataList;
    }


    @Override
    @Transactional
    public String importKey(MultipartFile file) throws Exception {

        ImportResult<KeyEntity> result = ExcelUtils.importExcelFromClient(KeyEntity.class, file, 5, 0, 2000);
        if (Objects.isNull(result.getDataList())) {
            return result.getMsg();
        }
        List<KeyEntity> resultDataList = result.getDataList();
        if (resultDataList.isEmpty() && StringUtils.isBlank(result.getMsg())) {
            return "导入失败，模板中无数据！" + result.getMsg();
        }

        StringBuilder sb = new StringBuilder(result.getMsg());

        if (result.getMsg().contains("导入失败")) {
            return sb.toString();
        }

        int success = 0;
        int a = 6;
        StringBuilder builder = new StringBuilder();
        List<AssetKeyManage> add = new ArrayList<>();
        List<AssetKeyManage> update = new ArrayList<>();
        for (KeyEntity entity : resultDataList) {
            AssetKeyManage asset = new AssetKeyManage();
            asset.setKeyNum(entity.getKeyNum());
            AssetKeyManageQuery assetKeyManageQuery = new AssetKeyManageQuery();
            assetKeyManageQuery.setKeyNum(entity.getKeyNum());
            Integer count = keyManageDao.queryCount(assetKeyManageQuery);
            if (count > 0) {
                asset.setModifiedUser(LoginUserUtil.getLoginUser().getId());
                asset.setGmtModified(System.currentTimeMillis());
                update.add(asset);
            } else {
                asset.setRecipState(KeyStatusEnum.KEY_NO_RECIPIENTS.getStatus());
                asset.setGmtCreate(System.currentTimeMillis());
                asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
                add.add(asset);
            }
            a++;
        }

        try {
            if (!add.isEmpty()) {
                keyManageDao.insertBatch(add);
                add.forEach(keyManage -> {
                    LogUtils.recordOperLog(new BusinessData(AssetEventEnum.KEY_IMPORT.getName(),
                            keyManage.getId(), keyManage.getKeyNum(), keyManage,
                            BusinessModuleEnum.KEY_MANAGE, BusinessPhaseEnum.KEY_IMPORT));
                });
            }
            if (!update.isEmpty()) {
                keyManageDao.updateBatch(update);
                update.forEach(keyManage -> {
                    LogUtils.recordOperLog(new BusinessData(AssetEventEnum.KEY_IMPORT.getName(),
                            keyManage.getId(), keyManage.getKeyNum(), keyManage,
                            BusinessModuleEnum.KEY_MANAGE, BusinessPhaseEnum.KEY_IMPORT));
                });
            }
            success = add.size() + update.size();
        } catch (DuplicateKeyException exception) {
            throw new BusinessException("请勿重复提交！");
        }

        String res = "导入成功" + success + "条";
        StringBuilder stringBuilder = new StringBuilder(res);

        return stringBuilder.append(builder).append(sb).toString();
    }

    @Override
    public List<KeyPullDownResponse> assetMapList(KeyPullQuery query) throws Exception {

        LoginUser user = LoginUserUtil.getLoginUser();
        List<String> areaIds = user.getAreaIdsOfCurrentUser();
        if (areaIds.isEmpty()) {
            return Lists.newArrayList();
        }
        query.setAreaIds(user.getAreaIdsOfCurrentUser());

        return keyManageDao.assetMapList(query);
    }

    @Override
    public List<KeyPullDownResponse> userMapList(KeyPullQuery query) throws Exception {
        List<Integer> userIds = keyManageDao.getAssetUserIds(2);
        query.setAssetUserIds(userIds);
        return keyManageDao.userMapList(query);
    }
}
