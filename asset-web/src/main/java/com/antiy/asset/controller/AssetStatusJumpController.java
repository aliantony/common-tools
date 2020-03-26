package com.antiy.asset.controller;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.service.IAssetStatusJumpService;
import com.antiy.asset.vo.query.NoRegisterRequest;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.AssetCorrectIInfoResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    private Logger logger = LogUtils.get(this.getClass());

    @Resource
    private IAssetService assetService;
    @Resource
    private AssetDao assetDao;
    @Resource
    private AssetSoftwareDao softwareDao;
    @Resource
    private AssetOperationRecordDao operationRecordDao;
    @Resource
    private ActivityClient activityClient;

    @Resource
    private IAssetSoftwareRelationService softwareRelationService;

    @Resource
    private IAssetStatusJumpService assetStatusJumpService;

    /**
     * 资产状态跃迁
     *
     * @param statusJumpRequest
     * @return actionResponse
     */
    @ApiOperation(value = "资产状态跃迁", notes = "传入实体对象信息")
    @PreAuthorize("hasAuthority('asset:statusjump')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"),})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ActionResponse statusJump(@ApiParam(value = "statusJumpRequest") @NotNull @RequestBody(required = false) AssetStatusJumpRequest statusJumpRequest) throws Exception {
        return assetStatusJumpService.changeStatus(statusJumpRequest);
    }

    /**
     * 资产不予登记
     *
     * @param assetStatusChangeRequest
     * @return actionResponse
     */
    @ApiOperation(value = "资产不予登记", notes = "传入实体对象信息")
    @PreAuthorize("hasAuthority('asset:noRegister')")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"),})
    @RequestMapping(value = "/noRegister", method = RequestMethod.POST)
    public ActionResponse assetNoRegister(@ApiParam(value = "assetStatusChangeRequest") @RequestBody(required = false) List<NoRegisterRequest> registerRequestList) throws Exception {
        Integer count = assetService.assetNoRegister(registerRequestList);
        return ActionResponse.success(count);
    }
    /**
     * 继续入网
     *
     * @param assetStatusChangeRequest
     * @return actionResponse
     */
    @ApiOperation(value = "资产继续入网", notes = "传入资产id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/netIn", method = RequestMethod.POST)
    public ActionResponse assetContinueNetIn(@ApiParam(value = "queryCondition") @RequestBody QueryCondition queryCondition) throws Exception {
        Integer result=assetStatusJumpService.continueNetIn(queryCondition.getPrimaryKey());
        return ActionResponse.success(result);
    }

    @ApiOperation(value = "准入实施", response = ActionResponse.class)
    @RequestMapping(value = "/entryExecution", method = RequestMethod.POST)
    public ActionResponse entryExecution(@RequestBody AssetEntryRequest request) {
        ParamterExceptionUtils.isEmpty(request.getAssetActivityRequests(), "资产参数不能为空");
        return ActionResponse.success(assetStatusJumpService.entryExecution(request));
    }

    /**
     * 整改详情
     */
    @ApiOperation(value = "整改详情", notes = "传入资产id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/correctiong/info", method = RequestMethod.POST)
    public ActionResponse assetCorrectingInfo(@ApiParam(value = "assetCorrectingRequest") @RequestBody AssetCorrectRequest assetCorrectRequest) throws Exception {
        AssetCorrectIInfoResponse result=assetStatusJumpService.assetCorrectingInfo(assetCorrectRequest);
        return ActionResponse.success(result);
    }

}
