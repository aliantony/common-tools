package com.antiy.asset.controller;

import com.antiy.common.base.RespBasicCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import com.antiy.common.base.ActionResponse;

import javax.annotation.Resource;

import com.antiy.asset.service.IAssetGroupService;
import com.antiy.asset.entity.vo.request.AssetGroupRequest;
import com.antiy.asset.entity.vo.query.AssetGroupQuery;


/**
 * @author zhangyajun
 * @since 2018-12-29
 */
@Api(value = "AssetGroup", description = "资产组表")
@RestController
@RequestMapping("/v1/asset/group")
@Slf4j
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
        System.out.println(assetGroup);
        Boolean success = iAssetGroupService.saveAssetGroup(assetGroup) > 0;
        if (success) {
            return ActionResponse.success();
        }
        return ActionResponse.fail(RespBasicCode.ERROR, "保存数据失败");
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
    @RequestMapping(value = "/update/single", method = RequestMethod.PUT)
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetGroup") AssetGroupRequest assetGroup) throws Exception {
        Integer integer = iAssetGroupService.updateAssetGroup(assetGroup);
        Boolean success = integer > 0;
        if (success) {
            ActionResponse.success();
        } else {
            return ActionResponse.fail(RespBasicCode.ERROR, "更新数据失败");
        }
        return null;
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
    public ActionResponse queryList(@RequestBody @ApiParam(value = "assetGroup") AssetGroupQuery assetGroup) throws Exception {
        return ActionResponse.success(iAssetGroupService.findPageAssetGroup(assetGroup));
    }

    /**
     * 通过ID查询
     *
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "通过id查询接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ActionResponse queryById(@PathVariable("id") @RequestBody @ApiParam(value = "id") Integer id) throws Exception {
        return ActionResponse.success(iAssetGroupService.getById(id));
    }

    /**
     * 通过ID删除
     *
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ActionResponse deleteById(@PathVariable @RequestBody @ApiParam(value = "query") Integer id) throws Exception {
        Boolean success = iAssetGroupService.deleteById(id) > 0;
        if (success) {
            return ActionResponse.success(iAssetGroupService.deleteById(id));
        }
        return ActionResponse.fail(RespBasicCode.ERROR, "删除数据失败");
    }
}

