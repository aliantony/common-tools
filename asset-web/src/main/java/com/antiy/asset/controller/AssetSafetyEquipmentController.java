package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetSafetyEquipmentService;
import com.antiy.asset.vo.query.AssetSafetyEquipmentQuery;
import com.antiy.asset.vo.request.AssetSafetyEquipmentRequest;
import com.antiy.asset.vo.response.AssetSafetyEquipmentResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-04
 */
@Api(value = "AssetSafetyEquipment", description = "安全设备详情表")
@RestController
@RequestMapping("/api/v1/asset/safetyequipment")
public class AssetSafetyEquipmentController {
    private static final Logger         logger = LogUtils.get();

    @Resource
    public IAssetSafetyEquipmentService iAssetSafetyEquipmentService;

    /**
     * 保存
     *
     * @param assetSafetyEquipmentRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = String.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:safetyequipment:saveSingle')")
    public ActionResponse saveSingle(@ApiParam(value = "assetSafetyEquipment") @RequestBody AssetSafetyEquipmentRequest assetSafetyEquipmentRequest) throws Exception {
        return ActionResponse
            .success(iAssetSafetyEquipmentService.saveAssetSafetyEquipment(assetSafetyEquipmentRequest));
    }

    /**
     * 修改
     *
     * @param assetSafetyEquipmentRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:safetyequipment:updateSingle')")
    public ActionResponse updateSingle(@ApiParam(value = "assetSafetyEquipment") AssetSafetyEquipmentRequest assetSafetyEquipmentRequest) throws Exception {
        return ActionResponse
            .success(iAssetSafetyEquipmentService.updateAssetSafetyEquipment(assetSafetyEquipmentRequest));
    }

    /**
     * 批量查询
     *
     * @param assetSafetyEquipmentQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSafetyEquipmentResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:safetyequipment:queryList')")
    public ActionResponse queryList(@ApiParam(value = "assetSafetyEquipment") AssetSafetyEquipmentQuery assetSafetyEquipmentQuery) throws Exception {
        return ActionResponse
            .success(iAssetSafetyEquipmentService.findPageAssetSafetyEquipment(assetSafetyEquipmentQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSafetyEquipmentResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('asset:safetyequipment:queryById')")
    public ActionResponse queryById(@ApiParam(value = "assetSafetyEquipment") QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isNull(queryCondition.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetSafetyEquipmentService.getById(queryCondition.getPrimaryKey()));
    }

    /**
     * 通过ID删除
     *
     * @param baseRequest
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('asset:safetyequipment:deleteById')")
    public ActionResponse deleteById(@ApiParam(value = "id") @RequestBody BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isNull(baseRequest.getStringId(), "ID不能为空");
        return ActionResponse.success(iAssetSafetyEquipmentService.deleteById(baseRequest.getStringId()));
    }
}
