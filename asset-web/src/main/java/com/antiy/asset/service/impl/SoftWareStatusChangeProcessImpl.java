package com.antiy.asset.service.impl;

import static com.antiy.biz.file.FileHelper.logger;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.AssetFlowCategoryEnum;
import com.antiy.asset.vo.enums.SoftwareStatusEnum;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * @auther: zhangbing
 * @date: 2019/1/22 15:48
 * @description:
 */
@Service
public class SoftWareStatusChangeProcessImpl extends AbstractAssetStatusChangeProcessImpl {
    @Resource
    private AssetSoftwareDao assetSoftwareDao;

    @Override
    public ActionResponse changeStatus(AssetStatusReqeust assetStatusReqeust) throws Exception {
        //获取软件状态
        SoftwareStatusEnum softwareStatusEnum = super.getNextSoftwareStatus(assetStatusReqeust);

        ActionResponse actionResponse = super.changeStatus(assetStatusReqeust);
        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            return actionResponse;
        }

        // 软件表详情操作
        AssetSoftware assetSoftware = new AssetSoftware();
        assetSoftware.setId(DataTypeUtils.stringToInteger(assetStatusReqeust.getAssetId()));
        assetSoftware.setGmtModified(System.currentTimeMillis());
        assetSoftware.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetSoftware.setSoftwareStatus(softwareStatusEnum.getCode());

        // 判断软件退役分析流程中，指定资产信息是否影响基准
        if (null != assetStatusReqeust.getSchemeRequest()) {
            Map<String, Boolean> analyzeInfo = (Map<String, Boolean>) JSONArray
                .parse(assetStatusReqeust.getSchemeRequest().getExtension());

            // 如果是软件资产退役,并且不会影响基准，则会直接到退役状态
            if (AssetFlowCategoryEnum.SOFTWARE_IMPL_RETIRE.equals(assetStatusReqeust.getAssetFlowCategoryEnum())
                && analyzeInfo != null && !analyzeInfo.get("baseline")) {
                assetSoftware.setSoftwareStatus(SoftwareStatusEnum.RETIRE.getCode());
            }
        }

        assetSoftwareDao.update(assetSoftware);

        LogHandle.log(assetSoftware.toString(), AssetEventEnum.SOFT_ASSET_STATUS_CHANGE.getName(),
            AssetEventEnum.SOFT_ASSET_STATUS_CHANGE.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.SOFT_ASSET_STATUS_CHANGE.getName() + " {}", assetSoftware.toString());
        return ActionResponse.success(assetSoftwareDao.update(assetSoftware));
    }
}
