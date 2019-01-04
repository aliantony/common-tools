package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetSoftwareLicenseService;
import com.antiy.asset.vo.query.AssetSoftwareLicenseQuery;
import com.antiy.asset.vo.request.AssetSoftwareLicenseRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 *
 * @author zhangyajun
 * @since 2019-01-04
 */
@Api(value = "AssetSoftwareLicense", description = "软件许可表")
@RestController
@RequestMapping("/v1/asset/assetsoftwarelicense")
public class AssetSoftwareLicenseController {
    private static final Logger logger = LogUtils.get();

    @Resource
    public IAssetSoftwareLicenseService iAssetSoftwareLicenseService;

    /**
     * 保存
     * @param assetSoftwareLicenseRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetSoftwareLicense") @RequestBody AssetSoftwareLicenseRequest assetSoftwareLicenseRequest)throws Exception{
        iAssetSoftwareLicenseService.saveAssetSoftwareLicense(assetSoftwareLicenseRequest);
        return ActionResponse.success();
    }

    /**
     * 修改
     * @param assetSoftwareLicenseRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetSoftwareLicense")AssetSoftwareLicenseRequest assetSoftwareLicenseRequest)throws Exception{
        iAssetSoftwareLicenseService.updateAssetSoftwareLicense(assetSoftwareLicenseRequest);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     * @param assetSoftwareLicenseQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetSoftwareLicense") AssetSoftwareLicenseQuery assetSoftwareLicenseQuery)throws Exception{
        return ActionResponse.success(iAssetSoftwareLicenseService.findPageAssetSoftwareLicense(assetSoftwareLicenseQuery));
    }

    /**
     * 通过ID查询
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "assetSoftwareLicense") @PathVariable("id") Integer id)throws Exception{
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetSoftwareLicenseService.getById(id));
    }

    /**
     * 通过ID删除
     * @param id
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"),
    })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "id") @PathVariable("id") Integer id)throws Exception{
        ParamterExceptionUtils.isNull(id, "ID不能为空");
        return ActionResponse.success(iAssetSoftwareLicenseService.deleteById(id));
    }
}

