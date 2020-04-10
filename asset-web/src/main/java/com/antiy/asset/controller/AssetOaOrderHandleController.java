package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetOaOrderHandleService;
import com.antiy.asset.vo.query.AssetOaOrderHandleQuery;
import com.antiy.asset.vo.request.AssetOaOrderHandleRequest;
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
@Api(value = "AssetOaOrderHandle", description = "订单处理关联资产表")
@RestController
@RequestMapping("/api/v1/asset/assetoaorderhandle")
public class AssetOaOrderHandleController {
    private static final Logger logger = LogUtils.get();

    @Resource
    public IAssetOaOrderHandleService iAssetOaOrderHandleService;

    /**
     * 保存
     * @param assetOaOrderHandleRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetOaOrderHandle") @RequestBody AssetOaOrderHandleRequest assetOaOrderHandleRequest)throws Exception{
        iAssetOaOrderHandleService.saveAssetOaOrderHandle(assetOaOrderHandleRequest);
        return ActionResponse.success();
    }

    /**
     * 修改
     * @param assetOaOrderHandleRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetOaOrderHandle") @RequestBody AssetOaOrderHandleRequest assetOaOrderHandleRequest)throws Exception{
        iAssetOaOrderHandleService.updateAssetOaOrderHandle(assetOaOrderHandleRequest);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     * @param assetOaOrderHandleQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetOaOrderHandle") @RequestBody AssetOaOrderHandleQuery assetOaOrderHandleQuery)throws Exception{
        return ActionResponse.success(iAssetOaOrderHandleService.findPageAssetOaOrderHandle(assetOaOrderHandleQuery));
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
    public ActionResponse queryById(@ApiParam(value = "assetOaOrderHandle") @RequestParam Integer id)throws Exception{
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetOaOrderHandleService.getById(id));
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
        return ActionResponse.success(iAssetOaOrderHandleService.deleteById(id));
    }
}

