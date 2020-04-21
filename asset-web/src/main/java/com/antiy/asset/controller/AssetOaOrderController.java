package com.antiy.asset.controller;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.service.IAssetOaOrderService;
import com.antiy.asset.vo.query.AssetOaOrderQuery;
import com.antiy.asset.vo.request.AssetOaOrderRequest;
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
@Api(value = "AssetOaOrder", description = "OA订单管理")
@RestController
@RequestMapping("/api/v1/asset/assetoaorder")
public class AssetOaOrderController {
    private static final Logger logger = LogUtils.get();

    @Resource
    public IAssetOaOrderService iAssetOaOrderService;

    /**
     * 保存
     * @param assetOaOrderRequest
     * @return actionResponse
     */
    @ApiOperation(value = "订单保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetOaOrder") @RequestBody AssetOaOrderRequest assetOaOrderRequest)throws Exception{
        logger.info("订单保存接口，assetOaOrderRequest:{}", JSON.toJSONString(assetOaOrderRequest));
        iAssetOaOrderService.saveAssetOaOrder(assetOaOrderRequest);
        return ActionResponse.success();
    }

    /**
     * 修改
     * @param assetOaOrderRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetOaOrder") @RequestBody AssetOaOrderRequest assetOaOrderRequest)throws Exception{
        iAssetOaOrderService.updateAssetOaOrder(assetOaOrderRequest);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     * @param assetOaOrderQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetOaOrder") @RequestBody AssetOaOrderQuery assetOaOrderQuery)throws Exception{
        return ActionResponse.success(iAssetOaOrderService.findPageAssetOaOrder(assetOaOrderQuery));
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
    public ActionResponse queryById(@ApiParam(value = "assetOaOrder") @RequestBody AssetOaOrderQuery assetOaOrderQuery)throws Exception{
        ParamterExceptionUtils.isNull(assetOaOrderQuery.getId(), "ID不能为空");
        return ActionResponse.success(iAssetOaOrderService.getDetailById(assetOaOrderQuery.getId()));
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
        return ActionResponse.success(iAssetOaOrderService.deleteById(id));
    }
}

