package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetInstallTemplateCheckService;
import com.antiy.asset.service.IAssetInstallTemplateService;
import com.antiy.asset.vo.query.AssetInstallTemplateQuery;
import com.antiy.asset.vo.query.PrimaryKeyQuery;
import com.antiy.asset.vo.request.AssetInstallTemplateRequest;
import com.antiy.asset.vo.request.BatchQueryRequest;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangyajun
 * @since 2019-09-19
 */
@Api(value = "AssetInstallTemplate", description = "装机模板")
@RestController
@RequestMapping("/api/v1/asset/assetinstalltemplate")
public class AssetInstallTemplateController {

    @Resource
    private IAssetInstallTemplateService      iAssetInstallTemplateService;
    @Resource
    private IAssetInstallTemplateCheckService iAssetInstallTemplateCheckService;

    /**
     * 装机模板综合查询
     *
     * @return
     */
    @ApiOperation(value = "模板列表查询-模板状态")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/query/statusList", method = RequestMethod.POST)
    public ActionResponse queryStatusList() {
        return ActionResponse.success(iAssetInstallTemplateService.queryTemplateStatus());
    }

    /**
     * 装机模板综合查询
     *
     * @return
     */
    @ApiOperation(value = "模板列表查询-适用操作系统")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetInstallTemplateOsResponse.class), })
    @RequestMapping(value = "/query/osList", method = RequestMethod.POST)
    public ActionResponse queryOsList() {
        return ActionResponse.success(iAssetInstallTemplateService.queryTemplateOs());
    }

    /**
     * 模板编号查询： 查询是否存在
     *
     * @param query 模板
     * @return
     */
    @ApiOperation("模板创建/编辑-模板编号去重查询")
    @ApiResponse(code = 200, message = "ok", response = Integer.class)
    @RequestMapping(path = { "/query/numberCode" }, method = { RequestMethod.POST })
    public ActionResponse queryNumberCode(@RequestBody @ApiParam(value = "AssetInstallTemplateQuery", required = true) AssetInstallTemplateQuery query) {
        ParamterExceptionUtils.isBlank(query.getNumberCode(), "模板编号不能为空");
        return ActionResponse.success(iAssetInstallTemplateService.queryNumberCode(query.getNumberCode().trim()));
    }

    @ApiOperation("模板创建/编辑-操作系统查询")
    @ApiResponse(code = 200, message = "ok", response = String.class)
    @RequestMapping(value = "/query/os", method = RequestMethod.POST)
    public ActionResponse queryOs() {
        return ActionResponse.success(iAssetInstallTemplateService.queryOs());
    }

    /**
     * 保存
     *
     * @param assetInstallTemplateRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetInstallTemplate") @RequestBody AssetInstallTemplateRequest assetInstallTemplateRequest) throws Exception {
        return ActionResponse
            .success(iAssetInstallTemplateService.saveAssetInstallTemplate(assetInstallTemplateRequest));
    }

    /**
     * 修改
     *
     * @param assetInstallTemplateRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetInstallTemplate") AssetInstallTemplateRequest assetInstallTemplateRequest) throws Exception {
        return ActionResponse
            .success(iAssetInstallTemplateService.updateAssetInstallTemplate(assetInstallTemplateRequest));
    }

    /**
     * 批量查询
     *
     * @param assetInstallTemplateQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetInstallTemplateResponse.class, responseContainer = "List"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetInstallTemplate") @RequestBody AssetInstallTemplateQuery assetInstallTemplateQuery) throws Exception {
        return ActionResponse
            .success(iAssetInstallTemplateService.queryPageAssetInstallTemplate(assetInstallTemplateQuery));
    }

    /**
     * 启用/禁用装机模板
     *
     * @param request
     * @return actionResponse
     */
    @ApiOperation(value = "启用/禁用装机模板", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetInstallTemplateResponse.class), })
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") @RequestBody AssetInstallTemplateRequest request) throws Exception {
        ParamterExceptionUtils.isNull(request.getStringId(), "id不能为null");
        ParamterExceptionUtils.isNull(request.getEnable(), "是否启用不能为null");
        return ActionResponse.success(iAssetInstallTemplateService.enableInstallTemplate(request));
    }

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetInstallTemplateResponse.class), })
    @RequestMapping(value = "/query/id", method = RequestMethod.GET)
    public ActionResponse queryById(@ApiParam(value = "主键封装对象") QueryCondition queryCondition) throws Exception {
        return ActionResponse.success(iAssetInstallTemplateService.queryAssetInstallTemplateById(queryCondition));
    }

    /**
     * 通过ID删除
     *
     * @param batchQueryRequest
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class), })
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    public ActionResponse deleteById(@ApiParam(value = "主键封装对象") @RequestBody BatchQueryRequest batchQueryRequest) throws Exception {
        return ActionResponse.success(iAssetInstallTemplateService.deleteAssetInstallTemplateById(batchQueryRequest));
    }

    @ApiOperation(value = "资产关联的装机模板信息", notes = "传入模板ID")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetTemplateRelationResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/relationInfo", method = RequestMethod.POST)
    public AssetTemplateRelationResponse queryTemplateByAssetId(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "模板Id不能为空");
        AssetTemplateRelationResponse assetTemplateRelationResponse = iAssetInstallTemplateService
            .queryTemplateByAssetId(queryCondition);
        return assetTemplateRelationResponse;
    }

    @ApiOperation(value = "模板审核信息", notes = "传入模板ID")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetInstallTemplateCheckResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/auditInfo", method = RequestMethod.POST)
    public List<AssetInstallTemplateCheckResponse> queryTemplateCheckByTemplateId(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "装机模板Id不能为空");
        List<AssetInstallTemplateCheckResponse> templateCheckResponses = iAssetInstallTemplateCheckService
            .queryTemplateCheckByTemplateId(queryCondition);
        return templateCheckResponses;
    }

    @ApiOperation(value = "模板包含的软件列表", notes = "传入模板ID")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetInstallTemplateCheckResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/softList", method = RequestMethod.POST)
    public ActionResponse<PageResult<AssetHardSoftLibResponse>> querySoftList(@RequestBody PrimaryKeyQuery query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPid(), "装机模板Id不能为空");
        return ActionResponse.success(iAssetInstallTemplateService.querySoftPage(query));
    }

    @ApiOperation(value = "模板包含的补丁列表", notes = "传入模板ID")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AssetInstallTemplateCheckResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/patchList", method = RequestMethod.POST)
    public ActionResponse<PageResult<PatchInfoResponse>> queryPatchList(@RequestBody PrimaryKeyQuery query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getPid(), "装机模板Id不能为空");
        return ActionResponse.success(iAssetInstallTemplateService.queryPatchPage(query));
    }
}
