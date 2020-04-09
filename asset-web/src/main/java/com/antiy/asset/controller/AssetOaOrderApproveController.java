package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetOaOrderApproveService;
import com.antiy.asset.vo.query.AssetOaOrderApproveQuery;
import com.antiy.asset.vo.request.AssetOaOrderApproveRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 *
 * @author shenliang
 * @since 2020-04-07
 */
@Api(value = "AssetOaOrderApprove", description = "订单审批信息表")
@RestController
@RequestMapping("/v1/asset/assetoaorderapprove")
public class AssetOaOrderApproveController {
    private static final Logger logger = LogUtils.get();

    @Resource
    public IAssetOaOrderApproveService iAssetOaOrderApproveService;

    /**
     * 保存
     * @param assetOaOrderApproveRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetOaOrderApprove") @RequestBody AssetOaOrderApproveRequest assetOaOrderApproveRequest)throws Exception{
        iAssetOaOrderApproveService.saveAssetOaOrderApprove(assetOaOrderApproveRequest);
        return ActionResponse.success();
    }

    /**
     * 修改
     * @param assetOaOrderApproveRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetOaOrderApprove") @RequestBody AssetOaOrderApproveRequest assetOaOrderApproveRequest)throws Exception{
        iAssetOaOrderApproveService.updateAssetOaOrderApprove(assetOaOrderApproveRequest);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     * @param assetOaOrderApproveQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetOaOrderApprove") @RequestBody AssetOaOrderApproveQuery assetOaOrderApproveQuery)throws Exception{
        return ActionResponse.success(iAssetOaOrderApproveService.findPageAssetOaOrderApprove(assetOaOrderApproveQuery));
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
    @RequestMapping(value = "/queryById", method = RequestMethod.POST)
    public ActionResponse queryById(@ApiParam(value = "assetOaOrderApprove") @PathVariable("id") Integer id)throws Exception{
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetOaOrderApproveService.getById(id));
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
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "id") @PathVariable("id") Integer id)throws Exception{
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetOaOrderApproveService.deleteById(id));
    }
}

