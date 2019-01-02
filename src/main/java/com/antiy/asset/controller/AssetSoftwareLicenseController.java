package com.antiy.asset.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import com.antiy.common.base.ActionResponse;
import javax.annotation.Resource;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;

import com.antiy.asset.service.IAssetSoftwareLicenseService;
import com.antiy.asset.entity.AssetSoftwareLicense;
import com.antiy.asset.entity.vo.request.AssetSoftwareLicenseRequest;
import com.antiy.asset.entity.vo.response.AssetSoftwareLicenseResponse;
import com.antiy.asset.entity.vo.query.AssetSoftwareLicenseQuery;


/**
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Api(value = "AssetSoftwareLicense", description = "软件许可表")
@RestController
@RequestMapping("/v1/asset/assetsoftwarelicense")
@Slf4j
public class AssetSoftwareLicenseController {

    @Resource
    public IAssetSoftwareLicenseService iAssetSoftwareLicenseService;

    /**
     * 保存
     * @param assetSoftwareLicense
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@RequestBody @ApiParam(value = "assetSoftwareLicense")AssetSoftwareLicenseRequest assetSoftwareLicense)throws Exception{
        iAssetSoftwareLicenseService.saveAssetSoftwareLicense(assetSoftwareLicense);
        return ActionResponse.success();
    }

    /**
     * 修改
     * @param assetSoftwareLicense
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.PUT)
    public ActionResponse updateSingle(@RequestBody @ApiParam(value = "assetSoftwareLicense")AssetSoftwareLicenseRequest assetSoftwareLicense)throws Exception{
        iAssetSoftwareLicenseService.updateAssetSoftwareLicense(assetSoftwareLicense);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     * @param assetSoftwareLicense
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@RequestBody @ApiParam(value = "assetSoftwareLicense") AssetSoftwareLicenseQuery assetSoftwareLicense)throws Exception{
        return ActionResponse.success(iAssetSoftwareLicenseService.findPageAssetSoftwareLicense(assetSoftwareLicense));
    }

    /**
     * 通过ID查询
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@RequestBody @ApiParam(value = "assetSoftwareLicense") QueryCondition query)throws Exception{
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetSoftwareLicenseService.getById(query.getPrimaryKey()));
    }

    /**
     * 通过ID删除
     * @param query 主键封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/delete/id", method = RequestMethod.DELETE)
    public ActionResponse deleteById(@RequestBody @ApiParam(value = "query") QueryCondition query)throws Exception{
        ParamterExceptionUtils.isBlank(query.getPrimaryKey(), "ID不能为空");
        return ActionResponse.success(iAssetSoftwareLicenseService.deleteById(query.getPrimaryKey()));
    }
}

