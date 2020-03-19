package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.antiy.asset.service.IAssetCpeTreeService;
import com.antiy.asset.vo.query.AssetCpeTreeCondition;
import com.antiy.asset.vo.query.AssetCpeTreeQuery;
import com.antiy.asset.vo.request.AssetCpeTreeRequest;
import com.antiy.asset.vo.response.AssetCpeTreeResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;

import io.swagger.annotations.*;

/**
 *
 * @author zhangyajun
 * @since 2020-03-15
 */
@Api(value = "AssetCpeTree", description = "CPE分类树形接口")
@RestController
@RequestMapping("/api/v1/asset/cpetree")
public class AssetCpeTreeController {

    @Resource
    public IAssetCpeTreeService iAssetCpeTreeService;

    /**
     * 保存
     *
     * @param assetCpeTreeRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息", hidden = true)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetCpeTree") @RequestBody AssetCpeTreeRequest assetCpeTreeRequest) throws Exception {
        return ActionResponse.success(iAssetCpeTreeService.saveAssetCpeTree(assetCpeTreeRequest));
    }

    /**
     * 修改
     *
     * @param assetCpeTreeRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息", hidden = true)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetCpeTree") AssetCpeTreeRequest assetCpeTreeRequest) throws Exception {
        return ActionResponse.success(iAssetCpeTreeService.updateAssetCpeTree(assetCpeTreeRequest));
    }

    /**
     * 批量查询
     *
     * @param assetCpeTreeQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件", hidden = true)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCpeTreeResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetCpeTree") AssetCpeTreeQuery assetCpeTreeQuery) throws Exception {
        return ActionResponse.success(iAssetCpeTreeService.queryPageAssetCpeTree(assetCpeTreeQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象", hidden = true)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCpeTreeResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetCpeTreeService.queryAssetCpeTreeById(queryCondition));
    }

    /**
     * 通过ID删除
     *
     * @param baseRequest
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象", hidden = true)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "主键封装对象") BaseRequest baseRequest) throws Exception {
        return ActionResponse.success(iAssetCpeTreeService.deleteAssetCpeTreeById(baseRequest));
    }

    /**
     * 通过ID查询子节点数据
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询子节点数据", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCpeTreeResponse.class), })
    @RequestMapping(value = "/query/subtree/id", method = RequestMethod.POST)
    public ActionResponse querySubTreeById(@RequestBody @ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetCpeTreeService.querySubTreeById(queryCondition));
    }

    /**
     * 通过ID查询下级子节点数据,只显示二级节点数据
     *
     * @param cpeTreeCondition
     *
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询下级子节点数据", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCpeTreeResponse.class), })
    @RequestMapping(value = "/query/nextNode/id", method = RequestMethod.POST)
    public ActionResponse queryNextNodeById(@RequestBody @ApiParam(value = "主键封装对象") AssetCpeTreeCondition cpeTreeCondition) throws Exception {
        return ActionResponse.success(iAssetCpeTreeService.queryNextNodeById(cpeTreeCondition));
    }

    /**
     * 查询所有的树形分类数据
     *
     * @return actionResponse
     */
    @ApiOperation(value = "查询所有的树形分类数据", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetCpeTreeResponse.class), })
    @RequestMapping(value = "/query/tree", method = RequestMethod.POST)
    public ActionResponse queryTree() throws Exception {
        return ActionResponse.success(iAssetCpeTreeService.queryTree());
    }
}
