package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.vo.request.AssetSoftwareRelationRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetSoftwareRelation", description = "资产软件关系信息")
@RestController
@RequestMapping("/v1/asset/assetsoftwarerelation")
@Slf4j
public class AssetSoftwareRelationController {

    @Resource
    public IAssetSoftwareRelationService iAssetSoftwareRelationService;

    /**
     * 保存
     *
     * @param assetSoftwareRelation
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetSoftwareRelation") AssetSoftwareRelationRequest assetSoftwareRelation) throws Exception {
        iAssetSoftwareRelationService.saveAssetSoftwareRelation(assetSoftwareRelation);
        return ActionResponse.success();
    }

    /**
     * 修改
     *
     * @param assetSoftwareRelation
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.PUT)
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetSoftwareRelation") AssetSoftwareRelationRequest assetSoftwareRelation) throws Exception {
        iAssetSoftwareRelationService.updateAssetSoftwareRelation(assetSoftwareRelation);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     *
     * @param assetSoftwareRelation
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@RequestBody @ApiParam(value = "assetSoftwareRelation") AssetSoftwareRelationQuery assetSoftwareRelation) throws Exception {
        return ActionResponse.success(iAssetSoftwareRelationService.findPageAssetSoftwareRelation(assetSoftwareRelation));
    }

    /**
     * 通过ID查询
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@RequestBody @ApiParam(value = "assetSoftwareRelation") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetSoftwareRelationService.getById(query.getPrimaryKey()));
    }

    /**
     * 通过ID删除
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/delete/id", method = RequestMethod.DELETE)
    public ActionResponse deleteById(@RequestBody @ApiParam(value = "query") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetSoftwareRelationService.deleteById(query.getPrimaryKey()));
    }
}

