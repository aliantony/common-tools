package com.antiy.asset.controller;

import com.antiy.common.base.RespBasicCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import com.antiy.common.base.ActionResponse;

import javax.annotation.Resource;

import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;

import com.antiy.asset.service.IAssetUserService;
import com.antiy.asset.entity.vo.request.AssetUserRequest;
import com.antiy.asset.entity.vo.query.AssetUserQuery;


/**
 * @author zhangyajun
 * @since 2018-12-29
 */
@Api(value = "AssetUser", description = "资产用户信息")
@RestController
@RequestMapping("/v1/asset/user")
@Slf4j
public class AssetUserController {

    @Resource
    public IAssetUserService iAssetUserService;

    /**
     * 保存
     *
     * @param assetUser
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetUser") AssetUserRequest assetUser) throws Exception {
        Boolean success = iAssetUserService.saveAssetUser(assetUser) > 0;
        if (success) {
            return ActionResponse.success();
        }
        return ActionResponse.fail(RespBasicCode.ERROR, "删除数据失败");
    }

    /**
     * 修改
     *
     * @param assetUser
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.PUT)
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetUser") AssetUserRequest assetUser) throws Exception {
        Boolean success = iAssetUserService.updateAssetUser(assetUser) > 0;
        if (success) {
            return ActionResponse.success();
        }
        return ActionResponse.fail(RespBasicCode.ERROR, "删除数据失败");
    }

    /**
     * 批量查询
     *
     * @param assetUser
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@RequestBody @ApiParam(value = "assetUser") AssetUserQuery assetUser) throws Exception {
        return ActionResponse.success(iAssetUserService.findPageAssetUser(assetUser));
    }

    /**
     * 通过ID查询
     *
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ActionResponse queryById(@PathVariable("id") @RequestBody @ApiParam(value = "assetUser") Long id) throws Exception {
        return ActionResponse.success(iAssetUserService.getById(id));
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
    public ActionResponse deleteById(@PathVariable("id") @RequestBody @ApiParam(value = "id") Long id) throws Exception {
        Boolean success = iAssetUserService.deleteById(id) > 0;
        if (success) {
            return ActionResponse.success();
        }
        return ActionResponse.fail(RespBasicCode.ERROR, "删除数据失败");
    }
}

