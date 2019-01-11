package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetPeripheralEquipmentService;
import com.antiy.asset.vo.query.AssetPeripheralEquipmentQuery;
import com.antiy.asset.vo.request.AssetPeripheralEquipmentRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 *
 * @author zhangyajun
 * @since 2019-01-11
 */
@Api(value = "AssetPeripheralEquipment", description = "输入设备表")
@RestController
@RequestMapping("/v1/asset/assetperipheralequipment")
public class AssetPeripheralEquipmentController {
    private static final Logger logger = LogUtils.get();

    @Resource
    public IAssetPeripheralEquipmentService iAssetPeripheralEquipmentService;

    /**
     * 保存
     * @param assetPeripheralEquipmentRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetPeripheralEquipment") @RequestBody AssetPeripheralEquipmentRequest assetPeripheralEquipmentRequest)throws Exception{
        iAssetPeripheralEquipmentService.saveAssetPeripheralEquipment(assetPeripheralEquipmentRequest);
        return ActionResponse.success();
    }

    /**
     * 修改
     * @param assetPeripheralEquipmentRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetPeripheralEquipment")AssetPeripheralEquipmentRequest assetPeripheralEquipmentRequest)throws Exception{
        iAssetPeripheralEquipmentService.updateAssetPeripheralEquipment(assetPeripheralEquipmentRequest);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     * @param assetPeripheralEquipmentQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetPeripheralEquipment") AssetPeripheralEquipmentQuery assetPeripheralEquipmentQuery)throws Exception{
        return ActionResponse.success(iAssetPeripheralEquipmentService.findPageAssetPeripheralEquipment(assetPeripheralEquipmentQuery));
    }

    /**
     * 通过ID查询
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "assetPeripheralEquipment") @PathVariable("id") Integer id)throws Exception{
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetPeripheralEquipmentService.getById(id));
    }

    /**
     * 通过ID删除
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "id") @PathVariable("id") Integer id)throws Exception{
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetPeripheralEquipmentService.deleteById(id));
    }
}

