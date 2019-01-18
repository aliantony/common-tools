package com.antiy.asset.controller;

import javax.annotation.Resource;

import com.antiy.asset.vo.response.AssetCategoryModelResponse;
import com.antiy.common.encoder.Encode;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.springframework.web.bind.annotation.*;

import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.common.base.ActionResponse;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetCategoryModel", description = "品类型号表")
@RestController
@RequestMapping("/api/v1/asset/assetcategorymodel")
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
        return iAssetCategoryModelService.saveAssetCategoryModel(assetCategoryModel);
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
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCategoryModelResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetCategoryModel") AssetCategoryModelQuery assetCategoryModel) throws Exception {
        return ActionResponse.success(iAssetCategoryModelService.findPageAssetCategoryModel(assetCategoryModel));
    }

    /**
     * 通过ID查询
     *
     * @param id 主键
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCategoryModelResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ActionResponse queryById(@PathVariable  @ApiParam(value = "assetCategoryModel") @Encode Integer id) throws Exception {
        ParamterExceptionUtils.isNull(id,"id不能为空");
        return ActionResponse.success(iAssetCategoryModelService.getById(id));
    }

    /**
     * 通过ID删除
     *
     * @param id 主键,isConfirm 二次确认
     * @return  -1 表示存在资产，不能删除 -2 表示存在子品类，需要确认 -3 是系统内置品类，不能删除 >=0 表示删除的品类数
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ActionResponse deleteById(@PathVariable @ApiParam(value = "id") @Encode Integer id,@RequestBody @ApiParam(value = "二次确认") Boolean isConfirm) throws Exception {
        ParamterExceptionUtils.isNull(id,"id不能为空");
        ParamterExceptionUtils.isNull(isConfirm,"二次确认不能为空");
        return iAssetCategoryModelService.delete(id,isConfirm);
    }
}
