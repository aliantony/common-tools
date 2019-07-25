package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.util.Constants;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.asset.vo.request.CategoryNodeRequest;
import com.antiy.asset.vo.response.AssetCategoryModelResponse;
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
     * 批量查询
     *
     * @param assetCategoryModel
     * @return actionResponse
     */
    @ApiOperation(value = "(无效)批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCategoryModelResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:categorymodel:queryList')")
    public ActionResponse queryList(@RequestBody @ApiParam(value = "assetCategoryModel") AssetCategoryModelQuery assetCategoryModel)
                                                                                                                       throws Exception {
        return ActionResponse.success(iAssetCategoryModelService.findPageAssetCategoryModel(assetCategoryModel));
    }

    /**
     * 通过ID查询
     *
     * @param condition 主键
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCategoryModelResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/id", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:categorymodel:queryById')")
    public ActionResponse queryById(@RequestBody @ApiParam(value = "QueryCondition") QueryCondition condition) throws Exception {
        ParamterExceptionUtils.isNull(condition.getPrimaryKey(), "id不能为空");
        return ActionResponse.success(iAssetCategoryModelService.getById(Integer.parseInt(condition.getPrimaryKey())));
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
     *
     * @param condition 主键
     * @return
     */
    @ApiOperation(value = "(无效)通过ID查询品类型号及其子品类型号", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/get/id", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:categorymodel:getById')")
    public ActionResponse getById(@RequestBody @ApiParam(value = "QueryCondition") QueryCondition condition) throws Exception {
        ParamterExceptionUtils.isNull(condition.getPrimaryKey(), "id不能为空");
        return ActionResponse.success(iAssetCategoryModelService.findAssetCategoryModelById(Integer.parseInt(condition
            .getPrimaryKey())));
    }

    /**
     * 查询硬件第二级数据接口
     *
     * @return
     */
    @ApiOperation(value = "查询硬件第二级数据接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/second", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:categorymodel:getAssetCategoryByName')")
    public ActionResponse getAssetCategoryByName() throws Exception {
        return ActionResponse.success(
            iAssetCategoryModelService.getNextLevelCategoryByNameAes(Constants.FIRST_LEVEL_ASSET_CATEGORY_NAME));
    }

    /**
     * 查询软件第二级数据接口
     *
     * @return
     */
    @ApiOperation(value = "查询软件第二级数据接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/software", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:categorymodel:getSoftwareCategoryByName')")
    public ActionResponse getSoftwareCategoryByName() throws Exception {
        return ActionResponse.success(iAssetCategoryModelService.getNextLevelCategoryByName(Constants.FIRST_LEVEL_SOFTWARE_CATEGORY_NAME));
    }

    /**
     * 品类树查询
     *
     * @return actionResponse
     */
    @ApiOperation(value = "查询品类树", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCategoryModelResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/node", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:categorymodel:queryCategoryNode')")
    public ActionResponse queryCategoryNode() throws Exception {
        iAssetCategoryModelService.queryCategoryNode();
        return ActionResponse.success(iAssetCategoryModelService.queryCategoryNode());
    }
    /**
     * 品类树(去掉根节点)查询
     *
     * @return actionResponse
     */
    @ApiOperation(value = "查询品类树(去掉根节点)", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCategoryModelResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/withoutnode", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:categorymodel:queryCategoryNode')")
    public ActionResponse queryCategoryNodeWhihoutNode() throws Exception {
//        iAssetCategoryModelService.queryCategoryNode(1);
//        iAssetCategoryModelService.queryCategoryNode(2);
//        List<AssetCategoryModelNodeResponse> assetCategoryModelNodeResponses = new ArrayList(){{add(iAssetCategoryModelService.queryCategoryNode(1));add(iAssetCategoryModelService.queryCategoryNode(2));}};
        return ActionResponse.success(iAssetCategoryModelService.queryCategoryNodeCount());
    }

    /**
     * 品类树查询
     *
     * @return actionResponse
     */
    @ApiOperation(value = "通过类型查询品类树", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCategoryModelResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/typeNode", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:categorymodel:queryCategoryNodeByType')")
    public ActionResponse queryCategoryNodeByType(@RequestBody @ApiParam(value = "1--软件 2--硬件")CategoryNodeRequest categoryNodeRequest) throws Exception {
        return ActionResponse.success(iAssetCategoryModelService.queryCategoryNode(categoryNodeRequest.getType()));
    }

    /**
     * 品类树查询
     *
     * @return actionResponse
     */
    @ApiOperation(value = "通过计算设备和网络设备树", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCategoryModelResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/computeNetNode", method = RequestMethod.POST)
    // @PreAuthorize(value = "hasAuthority('asset:categorymodel:queryComputeAndNetCategoryNode')")
    public ActionResponse queryComputeAndNetCategoryNode(@RequestBody @ApiParam("是否是网络设备") CategoryNodeRequest categoryNodeRequest) throws Exception {
        return ActionResponse.success(iAssetCategoryModelService.queryComputeAndNetCategoryNode(categoryNodeRequest.getIsNet()));
    }
}
