package com.antiy.asset.controller;

import com.antiy.asset.service.impl.AssetStatusChangeFlowProcessImpl;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.impl.AssetStatusChangeFactory;
import com.antiy.asset.service.impl.AssetStatusChangeProcessImpl;
import com.antiy.asset.service.impl.SoftWareStatusChangeProcessImpl;
import com.antiy.asset.vo.enums.AssetFlowCategoryEnum;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.LogUtils;

import io.swagger.annotations.*;

/**
 * 资产状态跃迁统一接口
 *
 * @author zhangyajun
 * @since 2019-01-04
 */
@Api(value = "statusjump", description = "资产状态跃迁")
@RestController
@RequestMapping("/api/v1/asset/statusjump")
public class AssetStatusJumpController {
    private static final Logger logger = LogUtils.get();

    /**
     * 资产状态跃迁
     *
     * @param assetStatusReqeust
     * @return actionResponse
     */
    @ApiOperation(value = "资产状态跃迁", notes = "传入实体对象信息")
    @PreAuthorize("hasAuthority('asset:statusjump')")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ActionResponse statusJump(@ApiParam(value = "assetStatusReqeust") @RequestBody(required = false) AssetStatusReqeust assetStatusReqeust) throws Exception {
        if (assetStatusReqeust.getAssetFlowCategoryEnum().getCode()
            .equals(AssetFlowCategoryEnum.SOFTWARE_REGISTER.getCode())) {
            return AssetStatusChangeFactory.getStatusChangeProcess(SoftWareStatusChangeProcessImpl.class)
                .changeStatus(assetStatusReqeust);
        } else {
            return AssetStatusChangeFactory.getStatusChangeProcess(AssetStatusChangeFlowProcessImpl.class)
                .changeStatus(assetStatusReqeust);
        }
    }
}
