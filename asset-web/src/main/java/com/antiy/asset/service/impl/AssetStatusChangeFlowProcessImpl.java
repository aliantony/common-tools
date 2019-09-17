package com.antiy.asset.service.impl;

import javax.annotation.Resource;

import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetStatusChangeProcessService;
import com.antiy.asset.vo.request.AssetStatusJumpRequest;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.BusinessExceptionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.intergration.BaseLineClient;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @auther: zhangyajun
 * @date: 2019/1/23 15:38
 * @description: 硬件资产状态跃迁
 */
@Service("assetStatusChangeFlowProcessImpl")
public class AssetStatusChangeFlowProcessImpl implements IAssetStatusChangeProcessService {
    private Logger         logger = LogUtils.get(this.getClass());
    @Resource
    private AssetDao       assetDao;
    @Resource
    private AssetLinkRelationDao assetLinkRelationDao;
    @Resource
    private AssetOperationRecordDao assetOperationRecordDao;

    @Resource
    private AesEncoder     aesEncoder;
    @Resource
    private BaseLineClient baseLineClient;
    @Resource
    private ActivityClient activityClient;
    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public ActionResponse changeStatus(AssetStatusReqeust assetStatusReqeust) throws Exception {
        return null;
    }

    @Override
    public ActionResponse changeStatus(AssetStatusJumpRequest statusJumpRequest) throws Exception {
        // 1.校验参数信息,当前流程的资产是否都满足当前状态(所有资产状态与页面状态一致,当前资产的可执行操作与本次操作一致),待整改有两种情况
        List<Integer> assetIdList = statusJumpRequest.getAssetIdList().stream().map(DataTypeUtils::stringToInteger).collect(Collectors.toList());
        List<Asset> assetInDB = null;
        if (CollectionUtils.isNotEmpty(assetIdList)) {
            assetInDB = assetDao.findByIds(assetIdList);
        }
        BusinessExceptionUtils.isEmpty(assetInDB, "处理请求错误,请稍后重试");
        assetInDB.forEach(e -> {
            if (!e.getStatus().equals(statusJumpRequest.getAssetFlowEnum().getCurrentAssetStatus().getCode())) {
                throw new BusinessException("当前选中的资产已被其他人员操作,请刷新页面后重试");
            }
        });
        // 2.提交至工作流
        // 3.更新资产状态
        // 4.插入操作记录

        return null;

    }

    // @Override
    // public ActionResponse changeStatus(AssetStatusReqeust assetStatusReqeust) throws Exception {
    //     AssetStatusEnum assetStatusEnum = super.getNextAssetStatus(assetStatusReqeust);
    //     Asset asset = new Asset();
    //     ActionResponse actionResponse = super.changeStatus(assetStatusReqeust);
    //     if (null == actionResponse
    //         || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
    //         return actionResponse;
    //     }
    //     // 检查资产主表的首次入网时间，为空时写入入网时间
    //
    //     Asset currentAsset = assetDao.getById(assetStatusReqeust.getAssetId());
    //     if (currentAsset != null && currentAsset.getFirstEnterNett() == null
    //         && assetStatusEnum.getCode().equals(AssetStatusEnum.NET_IN.getCode())) {
    //         asset.setFirstEnterNett(System.currentTimeMillis());
    //     }
    //     asset.setAssetStatus(assetStatusEnum.getCode());
    //     asset.setId(DataTypeUtils.stringToInteger(assetStatusReqeust.getAssetId()));
    //     asset.setGmtModified(System.currentTimeMillis());
    //     asset.setModifyUser(LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : null);
    //
    //     // 如果是带入网并且选择拒绝，则会调用流程引擎的待验证接口
    //     ParamterExceptionUtils.isNull(assetStatusReqeust.getAssetStatus(), "硬件当前状态不能为空");
    //     if (!assetStatusReqeust.getAgree()
    //         && AssetStatusEnum.WAIT_NET.getCode().equals(assetStatusReqeust.getAssetStatus().getCode())) {
    //         ActionResponse actionResponseVerify = baseLineClient
    //             .updateAssetVerify(aesEncoder.encode(asset.getStringId(), LoginUserUtil.getLoginUser().getUsername()));
    //         if (null == actionResponseVerify
    //             || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponseVerify.getHead().getCode())) {
    //             logger.warn("调用基准待验证接口失败,回滚资产状态,{}", assetStatusReqeust);
    //             asset.setAssetStatus(assetStatusReqeust.getAssetStatus().getCode());
    //             assetDao.update(asset);
    //             return actionResponseVerify;
    //         }
    //     }
    //
    //     // 更新资产状态
    //     assetDao.update(asset);
    //
    //     // 入网才下发基准
    //     new Thread(() -> {
    //         if (assetStatusEnum.getCode().equals(AssetStatusEnum.NET_IN.getCode())) {
    //             logger.info("入网资产，执行下发基准");
    //             // String encodeAssetId = aesEncoder.encode(assetStatusReqeust.getAssetId(),
    //             // LoginUserUtil.getLoginUser().getUsername());
    //             baseLineClient.distributeBaseline(assetStatusReqeust.getAssetId());
    //         }
    //     }).start();
    //
    //     return ActionResponse.success();
    // }
}
