package com.antiy.asset.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.*;
import javax.annotation.Resource;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.ActionResponse;
import com.antiy.asset.service.IAssetPatchInstallTemplateService;
import com.antiy.asset.vo.request.AssetPatchInstallTemplateRequest;
import com.antiy.asset.vo.response.AssetPatchInstallTemplateResponse;
import com.antiy.asset.vo.query.AssetPatchInstallTemplateQuery;

/**
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetPatchInstallTemplate", description = "装机模板与补丁关系表")
@RestController
@RequestMapping("/api/v1/asset/assetpatchinstalltemplate")
public class AssetPatchInstallTemplateController {

    @Resource
    public IAssetPatchInstallTemplateService iAssetPatchInstallTemplateService;

    /**
     * 保存
     *
     * @param assetPatchInstallTemplateRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetPatchInstallTemplate") @RequestBody AssetPatchInstallTemplateRequest assetPatchInstallTemplateRequest) throws Exception {
        return ActionResponse
            .success(iAssetPatchInstallTemplateService.saveAssetPatchInstallTemplate(assetPatchInstallTemplateRequest));
    }

    /**
     * 修改
     *
     * @param assetPatchInstallTemplateRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetPatchInstallTemplate") AssetPatchInstallTemplateRequest assetPatchInstallTemplateRequest) throws Exception {
        return ActionResponse.success(
            iAssetPatchInstallTemplateService.updateAssetPatchInstallTemplate(assetPatchInstallTemplateRequest));
    }

    /**
     * 批量查询
     *
     * @param assetPatchInstallTemplateQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetPatchInstallTemplateResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.GET)
    public ActionResponse queryList(@ApiParam(value = "assetPatchInstallTemplate") AssetPatchInstallTemplateQuery assetPatchInstallTemplateQuery) throws Exception {
        return ActionResponse.success(
            iAssetPatchInstallTemplateService.queryPageAssetPatchInstallTemplate(assetPatchInstallTemplateQuery));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetPatchInstallTemplateResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse
            .success(iAssetPatchInstallTemplateService.queryAssetPatchInstallTemplateById(queryCondition));
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
            .success(iAssetPatchInstallTemplateService.deleteAssetPatchInstallTemplateById(baseRequest));
    }
}
