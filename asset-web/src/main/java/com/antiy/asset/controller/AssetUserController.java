package com.antiy.asset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;

import com.antiy.asset.service.IAssetUserService;
import com.antiy.asset.vo.query.AssetUserQuery;
import com.antiy.asset.vo.request.AssetUserRequest;
import com.antiy.common.base.ActionResponse;

import io.swagger.annotations.*;

/**
 * @author zhangyajun
 * @since 2019-01-02
 */
@Api(value = "AssetUser", description = "资产用户信息")
@RestController
@RequestMapping("/v1/asset/assetuser")
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
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetUser") AssetUserRequest assetUser) throws Exception {
        return ActionResponse.success(iAssetUserService.saveAssetUser(assetUser));
    }

    /**
     * 修改
     *
     * @param assetUser
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetUser") AssetUserRequest assetUser) throws Exception {
        return ActionResponse.success(iAssetUserService.updateAssetUser(assetUser));
    }

    /**
     * 批量查询
     *
     * @param assetUser
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetUser") AssetUserQuery assetUser) throws Exception {
        return ActionResponse.success(iAssetUserService.findPageAssetUser(assetUser));
    }

    /**
     * 通过ID查询
     *
     * @param id 主键
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ActionResponse queryById(@PathVariable @ApiParam(value = "id") Integer id) throws Exception {
        return ActionResponse.success(iAssetUserService.getById(id));
    }

    /**
     * 通过ID删除
     *
     * @param id 主键
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ActionResponse deleteById(@PathVariable @RequestBody @ApiParam(value = "id") Integer id) throws Exception {
        return ActionResponse.success(iAssetUserService.deleteById(id));
    }

    /**
     * 查询资产使用者信息
     *
     * @return 用户名集合
     */
    @ApiOperation(value = "查询资产使用者信息", notes = "无查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/userName", method = RequestMethod.GET)
    public ActionResponse queryUserName() throws Exception {
        return ActionResponse.success(iAssetUserService.findUserName());
    }
}
