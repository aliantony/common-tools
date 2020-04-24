package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetOaOrderResultService;
import com.antiy.asset.vo.query.AssetOaOrderResultQuery;
import com.antiy.asset.vo.request.AssetOaOrderResultRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author shenliang
 * @since 2020-04-09
 */
@Api(value = "AssetOaOrderResult", description = "出借订单表")
@RestController
@RequestMapping("/api/v1/asset/assetoaorderresult")
public class AssetOaOrderResultController {
    private static final Logger logger = LogUtils.get();

    @Resource
    public IAssetOaOrderResultService iAssetOaOrderRefuseService;

    /**
     * 保存
     *
     * @param assetOaOrderRefuseRequest
     * @return actionResponse
     */
    @ApiOperation(value = "出借订单拒绝接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/refuse", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetOaOrderRefuse") @RequestBody AssetOaOrderResultRequest assetOaOrderRefuseRequest) throws Exception {
        iAssetOaOrderRefuseService.saveAssetOaOrderRefuse(assetOaOrderRefuseRequest);
        return ActionResponse.success();
    }

    /**
     * 修改
     *
     * @param assetOaOrderRefuseRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetOaOrderRefuse") @RequestBody AssetOaOrderResultRequest assetOaOrderRefuseRequest) throws Exception {
        iAssetOaOrderRefuseService.updateAssetOaOrderRefuse(assetOaOrderRefuseRequest);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     *
     * @param assetOaOrderRefuseQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetOaOrderRefuse") @RequestBody AssetOaOrderResultQuery assetOaOrderRefuseQuery) throws Exception {
        return ActionResponse.success(iAssetOaOrderRefuseService.findPageAssetOaOrderRefuse(assetOaOrderRefuseQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/queryById", method = RequestMethod.POST)
    public ActionResponse queryById(@ApiParam(value = "assetOaOrderRefuse") @RequestBody QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isNull(queryCondition.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetOaOrderRefuseService.getById(Integer.parseInt(queryCondition.getPrimaryKey())));
    }

    /**
     * 通过ID删除
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "id") @RequestBody QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isNull(queryCondition.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetOaOrderRefuseService.deleteById(Integer.parseInt(queryCondition.getPrimaryKey())));
    }
}

