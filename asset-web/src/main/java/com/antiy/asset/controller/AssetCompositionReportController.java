package com.antiy.asset.controller;

import com.antiy.asset.entity.AssetCompositionReport;
import com.antiy.asset.service.IAssetCompositionReportService;
import com.antiy.asset.vo.query.AssetCompositionReportReQuery;
import com.antiy.asset.vo.query.AssetCompositionReportTemplateQuery;
import com.antiy.asset.vo.request.AssetCompositionReportRequest;
import com.antiy.asset.vo.request.BaseId;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.JsonUtil;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author why
 * @since 2020-02-24
 */
@Api(value = "AssetCompositionReport", description = "")
@RestController
@RequestMapping("/api/v1/asset/assetcompositionreport")
public class AssetCompositionReportController {
    private static final Logger           logger = LogUtils.get();

    @Resource
    public IAssetCompositionReportService iAssetCompositionReportService;

    /**
     * 保存
     * @param assetCompositionReportRequest
     * @return actionResponse
     */
    @ApiOperation(value = "保存接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public ActionResponse saveSingle(@ApiParam(value = "assetCompositionReport") @RequestBody AssetCompositionReportRequest assetCompositionReportRequest) throws Exception {
        iAssetCompositionReportService.saveAssetCompositionReport(assetCompositionReportRequest);
        return ActionResponse.success();
    }

    /**
     * 修改
     * @param assetCompositionReportRequest
     * @return actionResponse
     */
    @ApiOperation(value = "修改接口", notes = "传入实体对象信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/update/single", method = RequestMethod.POST)
    public ActionResponse updateSingle(@ApiParam(value = "assetCompositionReport") @RequestBody AssetCompositionReportRequest assetCompositionReportRequest) throws Exception {
        iAssetCompositionReportService.updateAssetCompositionReport(assetCompositionReportRequest);
        return ActionResponse.success();
    }

    /**
     * 批量查询
     * @param assetCompositionReportQuery
     * @return actionResponse
     */
    @ApiOperation(value = "综合报表查询接口", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list", method = RequestMethod.POST)
    public ActionResponse queryList(@ApiParam(value = "assetCompositionReport") @RequestBody AssetCompositionReportReQuery assetCompositionReportQuery) throws Exception {
        return ActionResponse
            .success(iAssetCompositionReportService.findPageAssetCompositionReport(assetCompositionReportQuery));
    }

    /**
     * 批量查询
     * @param assetCompositionReportQuery
     * @return actionResponse
     */
    @ApiOperation(value = "批量查询模板", notes = "传入查询条件")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/list/templates", method = RequestMethod.POST)
    public ActionResponse templates(@ApiParam(value = "assetCompositionReportQuery") @RequestBody AssetCompositionReportTemplateQuery assetCompositionReportQuery) throws Exception {
        return ActionResponse.success(iAssetCompositionReportService.findReport(assetCompositionReportQuery));
    }

    /**
     * 通过ID查询
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID查询", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/query/id", method = RequestMethod.POST)
    public ActionResponse queryById(@RequestBody BaseId request) throws Exception {
        AssetCompositionReport byId = iAssetCompositionReportService.getById(request.getId());
        AssetCompositionReportReQuery assetCompositionReportQuery = JsonUtil.json2Object(byId.getQueryCondition(),
            AssetCompositionReportReQuery.class);
        AssetCompositionReportRequest assetCompositionReportRequest = new AssetCompositionReportRequest();
        assetCompositionReportRequest.setQuery(assetCompositionReportQuery);
        assetCompositionReportRequest.setName(byId.getName());
        assetCompositionReportRequest.setUserId(byId.getUserId());
        return ActionResponse.success(assetCompositionReportRequest);
    }

    /**
     * 通过ID删除
     * @return actionResponse
     */
    @ApiOperation(value = "通过ID删除接口", notes = "主键封装对象")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ActionResponse.class, responseContainer = "actionResponse"), })
    @RequestMapping(value = "/delete/id", method = RequestMethod.POST)
    public ActionResponse deleteById(@RequestBody BaseId request) throws Exception {
        ParamterExceptionUtils.isNull(request.getId(), "ID不能为空");
        return ActionResponse.success(iAssetCompositionReportService.deleteById(request.getId()));
    }

    /**
     * 导出资产信息
     *
     * @param assetQuery 封装对象
     * @return actionResponse
     */
    @ApiOperation(value = "根据条件导出综合报表信息", notes = "主键封装对象")
    @RequestMapping(value = "/export/file", method = RequestMethod.GET)
    // @PreAuthorize(value = "hasAuthority('asset:asset:export')")
    public void export(@ApiParam(value = "query") AssetCompositionReportReQuery assetQuery,
                       HttpServletResponse response,
                       HttpServletRequest request) throws Exception {
        iAssetCompositionReportService.exportData(assetQuery, response, request);

    }

}
