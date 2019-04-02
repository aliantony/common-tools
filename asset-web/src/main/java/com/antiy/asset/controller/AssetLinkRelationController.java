package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetLinkRelationService;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.vo.response.AssetLinkRelationResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.encoder.Encode;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 *
 * @author zhangyajun
 * @since 2019-04-02
 */
@Api(value = "AssetLinkRelation", description = "通联关系表")
@RestController
@RequestMapping("/v1/asset/assetlinkrelation")
public class AssetLinkRelationController {

    @Resource
    public IAssetLinkRelationService iAssetLinkRelationService;

    /**
     * 保存
     *
     * @param assetLinkRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetLinkRelation") @RequestBody AssetLinkRelationRequest assetLinkRelationRequest) throws Exception {
        return ActionResponse.success(iAssetLinkRelationService.saveAssetLinkRelation(assetLinkRelationRequest));
    }

    /**
     * 修改
     *
     * @param assetLinkRelationRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetLinkRelation") AssetLinkRelationRequest assetLinkRelationRequest) throws Exception {
        return ActionResponse.success(iAssetLinkRelationService.updateAssetLinkRelation(assetLinkRelationRequest));
    }

    /**
     * 批量查询
     *
     * @param assetLinkRelationQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetLinkRelationResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetLinkRelation") AssetLinkRelationQuery assetLinkRelationQuery) throws Exception {
        return ActionResponse.success(iAssetLinkRelationService.queryPageAssetLinkRelation(assetLinkRelationQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetLinkRelationResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetLinkRelationService.queryAssetLinkRelationById(queryCondition));
    }

    /**
     * 通过ID删除
     *
     * @param baseRequest
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "主键封装对象") BaseRequest baseRequest) throws Exception {
        return ActionResponse.success(iAssetLinkRelationService.deleteAssetLinkRelationById(baseRequest));
    }

    @ApiOperation(value = "通过资产Id查询可用的IP地址", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/query/ip", method = RequestMethod.GET)
    public ActionResponse queryAssetIpAddress(@Encode @ApiParam(value = "资产Id") String assetId,
                                              @ApiParam(value = "是否可用,true表示可用的资产IP,false表示全部IP,默认为true") Boolean enable) throws Exception {
        ParamterExceptionUtils.isBlank(assetId, "资产Id不能为空");
        return ActionResponse
            .success(iAssetLinkRelationService.queryIpAddressByAssetId(assetId, enable == null ? true : enable));
    }
}
