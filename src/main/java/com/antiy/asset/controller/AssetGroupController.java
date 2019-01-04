package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetGroupService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.query.AssetGroupQuery;
import com.antiy.asset.vo.request.AssetGroupRequest;
import com.antiy.asset.vo.request.AssetGroupUpdateRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetGroup", description = "资产组表")
@RestController
@RequestMapping("/v1/asset/assetgroup")
public class AssetGroupController {

    @Resource
    public IAssetGroupService iAssetGroupService;

    /**
     * 保存
     *
     * @param assetGroup
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetGroup") AssetGroupRequest assetGroup) throws Exception {
        return ActionResponse.success(iAssetGroupService.saveAssetGroup(assetGroup));
    }

    /**
     * 修改
     *
     * @param assetGroup
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetGroup") AssetGroupUpdateRequest assetGroup) throws Exception {
        return ActionResponse.success(iAssetGroupService.updateAssetGroup(assetGroup));
    }

    /**
     * 批量查询
     *
     * @param assetGroup
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetGroup") AssetGroupQuery assetGroup) throws Exception {
        return ActionResponse.success(iAssetGroupService.findPageAssetGroup(assetGroup));
    }

    /**
     * 通过ID查询
     *
     * @param id 主键
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ActionResponse queryById(@PathVariable @ApiParam(value = "assetGroup") Integer id) throws Exception {
        return ActionResponse.success(iAssetGroupService.getById(id));
    }

    /**
     * 通过ID删除
     *
     * @param id 主键
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ActionResponse deleteById(@PathVariable @RequestBody @ApiParam(value = "id") Integer id) throws Exception {
        return ActionResponse.success(iAssetGroupService.deleteById(id));
    }
}

