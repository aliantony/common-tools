package com.antiy.asset.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetSoftwareInstallTemplateService;
import com.antiy.asset.vo.request.AssetSoftwareInstallTemplateRequest;
import com.antiy.asset.vo.response.AssetSoftwareInstallTemplateResponse;
import com.antiy.asset.vo.query.AssetSoftwareInstallTemplateQuery;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetSoftwareInstallTemplate", description = "装机模板与软件关系表")
@RestController
@RequestMapping("/api/v1/asset/assetsoftwareinstalltemplate")
public class AssetSoftwareInstallTemplateController {

    @Resource
    public IAssetSoftwareInstallTemplateService iAssetSoftwareInstallTemplateService;

    /**
     * 保存
     *
     * @param assetSoftwareInstallTemplateRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetSoftwareInstallTemplate") @RequestBody AssetSoftwareInstallTemplateRequest assetSoftwareInstallTemplateRequest) throws Exception {
        return ActionResponse.success(
            iAssetSoftwareInstallTemplateService.saveAssetSoftwareInstallTemplate(assetSoftwareInstallTemplateRequest));
    }

    /**
     * 修改
     *
     * @param assetSoftwareInstallTemplateRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetSoftwareInstallTemplate") AssetSoftwareInstallTemplateRequest assetSoftwareInstallTemplateRequest) throws Exception {
        return ActionResponse.success(iAssetSoftwareInstallTemplateService
            .updateAssetSoftwareInstallTemplate(assetSoftwareInstallTemplateRequest));
    }

    /**
     * 批量查询
     *
     * @param assetSoftwareInstallTemplateQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftwareInstallTemplateResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetSoftwareInstallTemplate") AssetSoftwareInstallTemplateQuery assetSoftwareInstallTemplateQuery) throws Exception {
        return ActionResponse.success(iAssetSoftwareInstallTemplateService
            .queryPageAssetSoftwareInstallTemplate(assetSoftwareInstallTemplateQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetSoftwareInstallTemplateResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse
            .success(iAssetSoftwareInstallTemplateService.queryAssetSoftwareInstallTemplateById(queryCondition));
    }

    /**
     * 通过ID删除
     *
     * @param baseRequest
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "主键封装对象") BaseRequest baseRequest) throws Exception {
        return ActionResponse
            .success(iAssetSoftwareInstallTemplateService.deleteAssetSoftwareInstallTemplateById(baseRequest));
    }
}
