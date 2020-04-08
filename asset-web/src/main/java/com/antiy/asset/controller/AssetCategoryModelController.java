package com.antiy.asset.controller;

import javax.annotation.Resource;

import com.antiy.asset.vo.response.AssetCategoryModelNodeResponse;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.common.base.ActionResponse;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetCategoryModel", description = "资产类型管理")
@RestController
@RequestMapping("/api/v1/asset/categorymodel")
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
    // @PreAuthorize(value = "hasAuthority('asset:categorymodel:saveSingle')")
    public ActionResponse saveSingle(@RequestBody(required = true) @ApiParam(value = "assetCategoryModel") AssetCategoryModelRequest assetCategoryModel)
            throws Exception {
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
    // @PreAuthorize(value = "hasAuthority('asset:categorymodel:updateSingle')")
    public ActionResponse updateSingle(@RequestBody(required = false) @ApiParam(value = "assetCategoryModel") AssetCategoryModelRequest assetCategoryModel)
            throws Exception {
        return iAssetCategoryModelService.updateAssetCategoryModel(assetCategoryModel);
    }

    /**
     * 通过ID删除
     *
     * @param condition 主键,isConfirm 二次确认
     * @return
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:categorymodel:deleteById')")
    public ActionResponse deleteById(@RequestBody @ApiParam(value = "QueryCondition") QueryCondition condition)
            throws Exception {
        ParamterExceptionUtils.isNull(condition.getPrimaryKey(), "id不能为空");
        return iAssetCategoryModelService.delete(Integer.parseInt(condition.getPrimaryKey()));
    }

    /**
     * 品类树查询
     *
     * @return actionResponse
     */
    @ApiOperation(value = "查询品类树", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCategoryModelNodeResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/nodes", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:categorymodel:queryCategoryNode')")
    public ActionResponse queryCategoryNodeWhihoutNode() throws Exception {
//        iAssetCategoryModelService.queryCategoryNode(1);
//        iAssetCategoryModelService.queryCategoryNode(2);
//        List<AssetCategoryModelNodeResponse> assetCategoryModelNodeResponses = new ArrayList(){{add(iAssetCategoryModelService.queryCategoryNode(1));add(iAssetCategoryModelService.queryCategoryNode(2));}};
        return ActionResponse.success(iAssetCategoryModelService.queryCategoryNodeCount());
    }

}
