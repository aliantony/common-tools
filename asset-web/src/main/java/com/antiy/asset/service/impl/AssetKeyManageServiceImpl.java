package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetKeyManageDao;
import com.antiy.asset.entity.AssetKeyManage;
import com.antiy.asset.service.IAssetKeyManageService;
import com.antiy.asset.vo.enums.KeyStatusEnum;
import com.antiy.asset.vo.enums.KeyUserType;
import com.antiy.asset.vo.query.AssetKeyManageQuery;
import com.antiy.asset.vo.request.AssetKeyManageRequest;
import com.antiy.asset.vo.response.AssetKeyManageResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.LoginUser;
import com.antiy.common.base.PageResult;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

        if (responseList.isEmpty() || responseList == null) {
            return new ArrayList<>();
        }
        List<AssetKeyManageResponse> responses = converter.convert(responseList, AssetKeyManageResponse.class);
        for (AssetKeyManageResponse response : responses){
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
    public Integer keyRegister(AssetKeyManageRequest request) {

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

        keyManage.setRecipState(KeyStatusEnum.KEY_RECIPIENTS.getStatus());
        keyManage.setCreateUser(user.getId());
        keyManage.setModifiedUser(user.getId());
        keyManage.setGmtCreate(System.currentTimeMillis());
        keyManage.setGmtModified(System.currentTimeMillis());
        keyManage.setIsDelete(0);
        keyManageDao.keyRegister(keyManage);

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
        keyManage.setIsDelete(0);
        return keyManageDao.keyRecipients(request);
    }

    /**
     * key归还
     *
     * @param request
     * @return
     */
    @Override
    public Integer keyReturn(AssetKeyManageRequest request) {
        AssetKeyManage keyManage = keyManageDao.queryId(request.getId());
        if (keyManage == null) {
            throw new BusinessException("KEY不存在！");
        }
        keyManage.setKeyUserType(null);
        keyManage.setKeyUserId(null);
        keyManage.setUserNumName(null);
        keyManage.setRecipTime(null);
        keyManage.setRecipState(KeyStatusEnum.KEY_RECIPIENTS.getStatus());
        return keyManageDao.keyReturn(keyManage);
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
        return keyManageDao.keyFreeze(keyManage);
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
}
