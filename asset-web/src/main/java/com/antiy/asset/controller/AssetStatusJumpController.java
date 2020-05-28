package com.antiy.asset.controller;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.service.IAssetStatusJumpService;
import com.antiy.asset.vo.query.AssetStatusJudgeRequest;
import com.antiy.asset.vo.query.NoRegisterRequest;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.AssetCorrectIInfoResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

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
     * 状态判断
     */
    @ApiOperation(value = "资产状态扭转判断", notes = "传入实体对象信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Boolean.class),})
    @RequestMapping(value = "/status/judge", method = RequestMethod.POST)
    public ActionResponse statusJudge(@ApiParam(value = "statusJumpRequest") @NotNull @RequestBody(required = false) AssetStatusJudgeRequest statusJumpRequest) throws Exception {
       Boolean flag= assetStatusJumpService.statusJudge(statusJumpRequest);
        return ActionResponse.success(flag);
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
     * 整改
     *
     */
    @ApiOperation(value = "整改", notes = "传入资产id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/netToCorrect", method = RequestMethod.POST)
    public ActionResponse netToCorrect(@ApiParam(value = "queryCondition") @RequestBody AssetNetToCorrectRequest assetNetToCorrectRequest) throws Exception {
        List<String> assetIds = assetNetToCorrectRequest.getAssetInfoList().stream().map(t -> t.getAssetId()).collect(Collectors.toList());
        Integer result=assetStatusJumpService.netToCorrect(assetIds);
        return ActionResponse.success(result);
    }
    @ApiOperation(value = "准入实施", response = ActionResponse.class)
    @RequestMapping(value = "/entryExecution", method = RequestMethod.POST)
    public ActionResponse entryExecution(@RequestBody AssetEntryRequest request) {
        ParamterExceptionUtils.isEmpty(request.getAssetActivityRequests(), "资产参数不能为空");
        return ActionResponse.success(assetStatusJumpService.entryExecution(request));
    }

    /**
     * 漏洞整改详情
     */
    @ApiOperation(value = "漏洞整改详情", notes = "传入资产id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/correctiong/info", method = RequestMethod.POST)
    public ActionResponse assetCorrectingInfo(@ApiParam(value = "assetCorrectingRequest") @RequestBody AssetCorrectRequest assetCorrectRequest) throws Exception {
        AssetCorrectIInfoResponse result=assetStatusJumpService.assetCorrectingInfo(assetCorrectRequest);
        return ActionResponse.success(result);
    }

    /**
     * 配置整改详情
     */
    @ApiOperation(value = "配置整改详情", notes = "传入资产id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/correctiong/baseLine/info", method = RequestMethod.POST)
    public ActionResponse assetCorrectingBaseLineInfo(@ApiParam(value = "assetCorrectingRequest") @RequestBody AssetCorrectRequest assetCorrectRequest) throws Exception {
        AssetCorrectIInfoResponse result=assetStatusJumpService.assetCorrectingOfbaseLine(assetCorrectRequest);
        return ActionResponse.success(result);
    }
    /**
     * 继续入网
     */
    @ApiOperation(value = "继续入网", notes = "传入资产id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/continue/net/in", method = RequestMethod.POST)
    public ActionResponse continueNetIn(@RequestBody ActivityHandleRequest activityHandleRequest) throws Exception {
        Integer  result=assetStatusJumpService.continueNetIn(activityHandleRequest);
        return ActionResponse.success(result);
    }
}
