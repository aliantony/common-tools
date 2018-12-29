package com.antiy.asset.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import com.antiy.common.base.ActionResponse;
import javax.annotation.Resource;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;

import com.antiy.asset.service.IAssetLabelRelationService;
import com.antiy.asset.entity.AssetLabelRelation;
import com.antiy.asset.entity.vo.request.AssetLabelRelationRequest;
import com.antiy.asset.entity.vo.response.AssetLabelRelationResponse;
import com.antiy.asset.entity.vo.query.AssetLabelRelationQuery;



/**
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Api(value = "AssetLabelRelation", description = "资产标签关系表")
@RestController
@RequestMapping("/v1/assetLabelRelation")
@Slf4j
public class AssetLabelRelationController {

    @Resource
    public IAssetLabelRelationService iAssetLabelRelationService;

    /**
     * 保存
     * @param assetLabelRelation
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetLabelRelation")AssetLabelRelationRequest assetLabelRelation)throws Exception{
        iAssetLabelRelationService.saveAssetLabelRelation(assetLabelRelation);
        return ActionResponse.success();
    }

    /**
     * 修改
     * @param assetLabelRelation
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.PUT)
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetLabelRelation")AssetLabelRelationRequest assetLabelRelation)throws Exception{
        iAssetLabelRelationService.updateAssetLabelRelation(assetLabelRelation);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     * @param assetLabelRelation
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@RequestBody @ApiParam(value = "assetLabelRelation") AssetLabelRelationQuery assetLabelRelation)throws Exception{
        return ActionResponse.success(iAssetLabelRelationService.findPageAssetLabelRelation(assetLabelRelation));
    }

    /**
     * 通过ID查询
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@RequestBody @ApiParam(value = "assetLabelRelation") QueryCondition query)throws Exception{
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetLabelRelationService.getById(query.getPrimaryKey()));
    }

    /**
     * 通过ID删除
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/delete/id", method = RequestMethod.DELETE)
    public ActionResponse deleteById(@RequestBody @ApiParam(value = "query") QueryCondition query)throws Exception{
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetLabelRelationService.deleteById(query.getPrimaryKey()));
    }
}

