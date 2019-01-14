package com.antiy.asset.controller;

import javax.annotation.Resource;

import com.antiy.asset.vo.request.AssetCategoryDeleteRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetCategoryModel", description = "品类型号表")
@RestController
@RequestMapping("/v1/asset/assetcategorymodel")
public class AssetCategoryModelController {

    @Resource
    public IAssetCategoryModelService iAssetCategoryModelService;

    /**
     * 保存
     *
     * @param assetCategoryModel
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody(required = false) @ApiParam(value = "assetCategoryModel") AssetCategoryModelRequest assetCategoryModel) throws Exception {
        return ActionResponse.success(iAssetCategoryModelService.saveAssetCategoryModel(assetCategoryModel));
    }

    /**
     * 修改
     *
     * @param assetCategoryModel
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@RequestBody(required = false) @ApiParam(value = "assetCategoryModel") AssetCategoryModelRequest assetCategoryModel) throws Exception {
        return ActionResponse.success(iAssetCategoryModelService.updateAssetCategoryModel(assetCategoryModel));
    }

    /**
     * 批量查询
     *
     * @param assetCategoryModel
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetCategoryModel") AssetCategoryModelQuery assetCategoryModel) throws Exception {
        return ActionResponse.success(iAssetCategoryModelService.findPageAssetCategoryModel(assetCategoryModel));
    }

    /**
     * 通过ID查询
     *
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "assetCategoryModel") QueryCondition query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetCategoryModelService.getById(Integer.parseInt(query.getPrimaryKey())));
    }

    /**
     * 通过ID删除
     *
     * @param request 主键封装对象
     * @return  -1 表示存在资产，不能删除 -2 表示存在子品类，需要确认 -3 是系统内置品类，不能删除 >=0 表示删除的品类数
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "query") AssetCategoryDeleteRequest request) throws Exception {
        ParamterExceptionUtils.isBlank(request.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetCategoryModelService.delete(Integer.parseInt(request.getPrimaryKey()),request.getConfirm()));
    }
}
