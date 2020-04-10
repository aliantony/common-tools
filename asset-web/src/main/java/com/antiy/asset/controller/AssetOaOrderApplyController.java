package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetOaOrderApplyService;
import com.antiy.asset.vo.query.AssetOaOrderApplyQuery;
import com.antiy.asset.vo.request.AssetOaOrderApplyRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 *
 * @author shenliang
 * @since 2020-04-07
 */
@Api(value = "AssetOaOrderApply", description = "订单申请信息表")
@RestController
@RequestMapping("/api/v1/asset/assetoaorderapply")
public class AssetOaOrderApplyController {
    private static final Logger logger = LogUtils.get();

    @Resource
    public IAssetOaOrderApplyService iAssetOaOrderApplyService;

    /**
     * 保存
     * @param assetOaOrderApplyRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetOaOrderApply") @RequestBody AssetOaOrderApplyRequest assetOaOrderApplyRequest)throws Exception{
        iAssetOaOrderApplyService.saveAssetOaOrderApply(assetOaOrderApplyRequest);
        return ActionResponse.success();
    }

    /**
     * 修改
     * @param assetOaOrderApplyRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetOaOrderApply") @RequestBody AssetOaOrderApplyRequest assetOaOrderApplyRequest)throws Exception{
        iAssetOaOrderApplyService.updateAssetOaOrderApply(assetOaOrderApplyRequest);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     * @param assetOaOrderApplyQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetOaOrderApply") @RequestBody AssetOaOrderApplyQuery assetOaOrderApplyQuery)throws Exception{
        return ActionResponse.success(iAssetOaOrderApplyService.findPageAssetOaOrderApply(assetOaOrderApplyQuery));
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
    public ActionResponse queryById(@ApiParam(value = "assetOaOrderApply") @RequestParam Integer id)throws Exception{
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetOaOrderApplyService.getById(id));
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
    public ActionResponse deleteById(@ApiParam(value = "id") @RequestParam Integer id)throws Exception{
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetOaOrderApplyService.deleteById(id));
    }
}

